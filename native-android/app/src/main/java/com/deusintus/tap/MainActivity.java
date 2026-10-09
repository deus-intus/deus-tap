package com.deusintus.tap;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.cardemulation.CardEmulation;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public final class MainActivity extends Activity {
    private final int bg = Color.rgb(16, 21, 23);
    private final int gold = Color.rgb(214, 189, 135);
    private final int white = Color.rgb(244, 244, 240);
    private final int muted = Color.rgb(180, 190, 184);
    private TextView status;
    private CardEmulation cardEmulation;
    private ComponentName hceComponent;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(bg);
        LinearLayout body = new LinearLayout(this);
        body.setOrientation(LinearLayout.VERTICAL);
        body.setPadding(dp(28), dp(46), dp(28), dp(36));
        scroll.addView(body);
        text(body, "DEUS   INTUS", 17, gold, true);
        text(body, "TAP  ·  NFC EXPERIMENT", 13, muted, false);
        text(body, "Anthon Linton", 34, white, true);
        text(body, "Founder · Deus Intus", 19, gold, false);
        text(body, "Phone-to-phone NFC sharing", 22, white, true);
        text(body, "This Android app makes your phone behave like a read-only NFC Type 4 contact tag. Keep this screen open and your phone unlocked, then touch NFC antenna areas with the other device. Detection varies by phone.", 15, muted, false);
        status = text(body, "Checking NFC…", 15, white, true);
        addButton(body, "OPEN DIGITAL BUSINESS CARD", () -> openWebsite(TagProtocol.PROFILE_URL));
        addButton(body, "COPY CARD LINK", () -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(ClipData.newPlainText("Deus Tap", TagProtocol.PROFILE_URL));
            status.setText("Card URL copied to clipboard.");
        });
        addButton(body, "NFC SETTINGS", () -> startActivity(new Intent(Settings.ACTION_NFC_SETTINGS)));
        text(body, "No payment integration, no subscription and no contact storage in this app. The contact exchange remains on your existing Deus Tap website. If the receiving phone does not detect this experimental tag, open the website and use its QR code.", 13, muted, false);
        setContentView(scroll);
        hceComponent = new ComponentName(this, DeusTapApduService.class);
        updateStatus();
    }

    private int dp(int dp) { return Math.round(dp * getResources().getDisplayMetrics().density); }
    private TextView text(LinearLayout parent, String message, int sp, int colour, boolean bold) {
        TextView view = new TextView(this);
        view.setText(message);
        view.setTextColor(colour);
        view.setTextSize(sp);
        if (bold) view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        view.setPadding(0, dp(8), 0, dp(12));
        view.setGravity(Gravity.START);
        parent.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }
    private void addButton(LinearLayout parent, String label, Runnable click) {
        Button btn = new Button(this);
        btn.setText(label);
        btn.setTextColor(bg);
        btn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(gold));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(58));
        lp.setMargins(0, dp(9), 0, dp(8));
        parent.addView(btn, lp);
        btn.setOnClickListener(v -> click.run());
    }
    private void openWebsite(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
    private void updateStatus() {
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
        if (adapter == null || !getPackageManager().hasSystemFeature("android.hardware.nfc.hce")) {
            status.setText("This phone does not support Android NFC card emulation.");
        } else if (!adapter.isEnabled()) {
            status.setText("NFC is off. Enable NFC in Settings.");
        } else {
            status.setText("NFC enabled. Emulation is registered; real phone-to-phone reception must still be tested.");
        }
    }
    @Override protected void onResume() {
        super.onResume();
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
        if (adapter != null && adapter.isEnabled() && getPackageManager().hasSystemFeature("android.hardware.nfc.hce")) {
            try {
                cardEmulation = CardEmulation.getInstance(adapter);
                cardEmulation.setPreferredService(this, hceComponent);
            } catch (Exception e) {
                if (status != null) status.setText("NFC preference unavailable: " + e.getClass().getSimpleName());
            }
        }
        updateStatus();
    }
    @Override protected void onPause() {
        if (cardEmulation != null) {
            try { cardEmulation.unsetPreferredService(this); } catch (Exception ignored) {}
        }
        super.onPause();
    }
}
