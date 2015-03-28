package logic;

import domain.CphT;
import domain.MskT;
import domain.PrvT;
import domain.PubT;
import it.unisa.dia.gas.jpbc.Element;
import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.GenericArrayType;
import java.util.Arrays;
import java.util.List;

public class MiscImpl implements Misc {

    void serialize_uint32( List<Byte> b, int k ) {
        int i;
        byte _byte;

        for( i = 3; i >= 0; i-- ) {
            _byte = (byte)((k & 0xff<<(i*8))>>(i*8));
            b.add(_byte);
        }
    }

    int unserialize_uint32( List<Byte> b, int offset ) {
        int i;
        int r;

        r = 0;
        for( i = 3; i >= 0; i-- )
            r |= ((int)b.get(offset++))<<(i*8);

        return r;
    }

    void serialize_element( List<Byte> b, Element e ) {
        int len;
        byte[] buf;

        len = e.getLengthInBytes();
        serialize_uint32(b, len);

        buf = e.toBytes();
        b.addAll(Arrays.asList(ArrayUtils.toObject(buf)));
    }


    int unserialize_element( List<Byte> b, int offset, Element e ) {
        int len;
        byte[] buf;

        len = unserialize_uint32(b, offset);

        buf = new byte[len];
        b.subList(offset, len).toArray(ArrayUtils.toObject(buf));
        offset += len;

        e.setFromBytes(buf);
        return offset;
    }

    void serialize_string( List<Byte> b, String s ) {
        b.addAll(Arrays.asList(ArrayUtils.toObject(s.getBytes())));
    }

    String unserialize_string( List<Byte> b, int offset )
    {
        String s;
        char[] r;
        char c;

        s = new String();
        while( true ) {
            c = (char)(b.get(offset++) & 0xFF);
            if( c != 0 && offset < b.size() )
                s = s + Character.toString(c);
            else
                break;
        }

        return s;
    }

    @Override
    public GenericArrayType bswabe_pub_serialize(PubT pub) {
        GByteArray* b;

        b = g_byte_array_new();
        serialize_string(b,  pub->pairing_desc);
        serialize_element(b, pub->g);
        serialize_element(b, pub->h);
        serialize_element(b, pub->gp);
        serialize_element(b, pub->g_hat_alpha);

        return b
    }

    @Override
    public GenericArrayType bswabe_msk_serialize(MskT msk) {
        return null;
    }

    @Override
    public GenericArrayType bswabe_prv_serialize(PrvT prv) {
        return null;
    }

    @Override
    public GenericArrayType bswabe_cph_serialize(CphT cph) {
        return null;
    }

    @Override
    public PubT bswabe_pub_unserialize(GenericArrayType b, int free) {
        return null;
    }

    @Override
    public MskT bswabe_msk_unserialize(PubT pub, GenericArrayType b, int free) {
        return null;
    }

    @Override
    public PrvT bswabe_prv_unserialize(PubT pub, GenericArrayType b, int free) {
        return null;
    }

    @Override
    public CphT bswabe_cph_unserialize(PubT pub, GenericArrayType b, int free) {
        return null;
    }

    @Override
    public void bswabe_pub_free(PubT pub) {

    }

    @Override
    public void bswabe_msk_free(MskT msk) {

    }

    @Override
    public void bswabe_prv_free(PrvT prv) {

    }

    @Override
    public void bswabe_cph_free(CphT cph) {

    }
}
