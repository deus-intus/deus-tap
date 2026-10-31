package com.deusintus.tap;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public final class TagProtocolTest {
    private static final byte[] SELECT_APP = new byte[]{
        0x00, (byte)0xA4, 0x04, 0x00, 0x07, (byte)0xD2, 0x76, 0x00, 0x00, (byte)0x85, 0x01, 0x01, 0x00
    };
    private static final byte[] SELECT_CC = new byte[]{0, (byte)0xA4,0,0x0C,2,(byte)0xE1,3};
    private static final byte[] SELECT_NDEF = new byte[]{0,(byte)0xA4,0,0x0C,2,(byte)0xE1,4};
    private static final byte[] SUCCESS = new byte[]{(byte)0x90,0};
    private static byte[] read(int offset,int length) {
        return new byte[]{0,(byte)0xB0,(byte)(offset>>8),(byte)offset,(byte)length};
    }
    @Test public void onlyNdefAidIsAccepted() {
        TagProtocol p=new TagProtocol();
        assertArrayEquals(SUCCESS,p.exchange(SELECT_APP));
        p.reset();
        byte[] wrong=SELECT_APP.clone();
        wrong[10]=0;
        assertArrayEquals(new byte[]{0x69,(byte)0x85},p.exchange(wrong));
    }
    @Test public void capabilityContainerCanBeReadInChunks() {
        TagProtocol p=new TagProtocol();
        assertArrayEquals(new byte[]{0x69,(byte)0x85},p.exchange(read(0,2)));
        p.exchange(SELECT_APP);p.exchange(SELECT_CC);
        assertArrayEquals(new byte[]{0,0x0F,(byte)0x90,0},p.exchange(read(0,2)));
        byte[] full=p.exchange(read(0,15));
        assertEquals(17,full.length);
        assertEquals((byte)0xE1,full[9]);
        assertEquals((byte)0x04,full[10]);
        assertEquals((byte)0x90,full[15]);
    }
    @Test public void ndefRecordRepresentsOriginalHttpsContactUrl() {
        TagProtocol p=new TagProtocol();
        p.exchange(SELECT_APP);
        assertArrayEquals(SUCCESS,p.exchange(SELECT_NDEF));
        byte[] lenResponse=p.exchange(read(0,2));
        int ndefLength=(lenResponse[0]&0xff)*256+(lenResponse[1]&0xff);
        byte[] bytes=p.exchange(read(2,ndefLength));
        assertEquals((byte)0xD1,bytes[0]);
        assertEquals(1,bytes[1]);
        assertEquals((byte)'U',bytes[3]);
        assertEquals(4,bytes[4]); // standard "https://" URI prefix
        String suffix=new String(bytes,5,ndefLength-5,StandardCharsets.UTF_8);
        assertEquals(TagProtocol.PROFILE_URL,"https://"+suffix);
        assertEquals((byte)0x90,bytes[ndefLength]);
        assertEquals(0,bytes[ndefLength+1]);
    }
    @Test public void rejectsUnselectedInvalidAndOutOfBoundsApdus() {
        TagProtocol p=new TagProtocol();
        assertArrayEquals(new byte[]{0x67,0},p.exchange(new byte[]{0}));
        assertArrayEquals(new byte[]{0x69,(byte)0x85},p.exchange(read(0,2)));
        p.exchange(SELECT_APP);p.exchange(SELECT_NDEF);
        assertArrayEquals(new byte[]{0x67,0},p.exchange(read(250,20)));
        p.reset();
        assertArrayEquals(new byte[]{0x69,(byte)0x85},p.exchange(read(0,2)));
    }
}
