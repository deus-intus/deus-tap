package com.deusintus.tap;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * NFC Forum Type 4 Tag APDU handling for a read-only NDEF URL.
 * Adapted from the Apache-2.0 NdefHostApduService implementation by
 * MichaelsPlayground/TechBooster (see UPSTREAM-LICENSE.txt).
 * This class has no Android dependencies, so protocol tests run on the JVM.
 */
public final class TagProtocol {
    public static final String PROFILE_URL = "https://deus-intus.github.io/deus-tap/";
    private static final byte[] OK = new byte[]{(byte) 0x90, 0x00};
    private static final byte[] NOT_FOUND = new byte[]{(byte) 0x6A, (byte) 0x82};
    private static final byte[] BAD_LENGTH = new byte[]{(byte) 0x67, 0x00};
    private static final byte[] NOT_SELECTED = new byte[]{(byte) 0x69, (byte) 0x85};
    private static final byte[] APP_AID = new byte[]{
            (byte) 0xD2, 0x76, 0x00, 0x00, (byte) 0x85, 0x01, 0x01
    };
    private static final byte[] CAPABILITY_CONTAINER = new byte[]{
            0x00, 0x0F, // Capability Container length
            0x20,       // NDEF Mapping version 2.0
            0x00, 0x3B, // Max R-APDU
            0x00, 0x34, // Max C-APDU
            0x04, 0x06, // NDEF File Control TLV
            (byte) 0xE1, 0x04, // NDEF file id
            0x00, (byte) 0xFF, // advertised max file size
            0x00, (byte) 0xFF // readable, not writable
    };
    private final byte[] ndefFile;
    private boolean appSelected;
    private int selectedFile; // 0 none, 1 Capability Container, 2 NDEF

    public TagProtocol() {
        byte[] ndef = encodeUriNdef(PROFILE_URL);
        ndefFile = new byte[ndef.length + 2];
        ndefFile[0] = (byte) (ndef.length >>> 8);
        ndefFile[1] = (byte) ndef.length;
        System.arraycopy(ndef, 0, ndefFile, 2, ndef.length);
    }

    static byte[] encodeUriNdef(String uri) {
        if (!uri.startsWith("https://")) throw new IllegalArgumentException("HTTPS URI required");
        byte[] suffix = uri.substring("https://".length()).getBytes(StandardCharsets.UTF_8);
        int len = suffix.length + 1; // URI prefix code + suffix
        if (len > 255) throw new IllegalArgumentException("URI too long");
        byte[] message = new byte[5 + suffix.length];
        message[0] = (byte) 0xD1; // MB + ME + SR + TNF_WELL_KNOWN
        message[1] = 0x01; // one-byte type
        message[2] = (byte) len;
        message[3] = 'U'; // NFC RTD URI
        message[4] = 0x04; // "https://" URI prefix compression
        System.arraycopy(suffix, 0, message, 5, suffix.length);
        return message;
    }

    private static boolean isSelectAid(byte[] cmd) {
        if (cmd.length != 12 && cmd.length != 13) return false;
        if (cmd[0] != 0 || (cmd[1] & 0xff) != 0xA4 ||
                cmd[2] != 0x04 || cmd[3] != 0 || cmd[4] != 0x07) return false;
        if (cmd.length == 13 && cmd[12] != 0) return false;
        for (int i = 0; i < APP_AID.length; i++)
            if (cmd[5+i] != APP_AID[i]) return false;
        return true;
    }

    public synchronized byte[] exchange(byte[] cmd) {
        if (cmd == null || cmd.length < 4) return BAD_LENGTH.clone();
        if (isSelectAid(cmd)) {
            appSelected = true;
            selectedFile = 0;
            return OK.clone();
        }
        if (!appSelected) return NOT_SELECTED.clone();
        // SELECT FILE by two-byte file identifier
        if (cmd.length == 7 && cmd[0] == 0 && (cmd[1]&0xff) == 0xA4 &&
                cmd[2] == 0 && (cmd[3]&0xff) == 0x0C && cmd[4] == 2 &&
                (cmd[5]&0xff) == 0xE1) {
            int number = cmd[6]&0xff;
            if (number == 3 || number == 4) {
                selectedFile = number == 3 ? 1 : 2;
                return OK.clone();
            }
            return NOT_FOUND.clone();
        }
        // READ BINARY: CLA INS P1 P2 Le
        if (cmd.length == 5 && cmd[0] == 0 && (cmd[1]&0xff) == 0xB0) {
            if (selectedFile == 0) return NOT_SELECTED.clone();
            int offset = ((cmd[2]&0xff) << 8) | (cmd[3]&0xff);
            int length = cmd[4]&0xff;
            if (length == 0) length = 256;
            byte[] contents = selectedFile == 1 ? CAPABILITY_CONTAINER : ndefFile;
            if (offset >= contents.length || length > contents.length-offset) return BAD_LENGTH.clone();
            byte[] response = Arrays.copyOfRange(contents, offset, offset+length+2);
            // Arrays.copyOfRange above padded the status bytes with zeroes, replace them.
            response[length] = (byte) 0x90;
            response[length+1] = 0;
            return response;
        }
        return NOT_FOUND.clone();
    }

    public synchronized void reset() {
        appSelected = false;
        selectedFile = 0;
    }
}
