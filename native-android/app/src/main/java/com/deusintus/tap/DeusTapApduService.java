package com.deusintus.tap;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;

public final class DeusTapApduService extends HostApduService {
    private final TagProtocol protocol = new TagProtocol();

    @Override public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        return protocol.exchange(commandApdu);
    }

    @Override public void onDeactivated(int reason) {
        protocol.reset();
    }
}
