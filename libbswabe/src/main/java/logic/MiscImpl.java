package logic;

import domain.*;
import it.unisa.dia.gas.jpbc.Element;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
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

    int[] unserialize_uint32( List<Byte> b, int offset ) {
        int i;
        int r;

        r = 0;
        for( i = 3; i >= 0; i-- )
            r |= ((int)b.get(offset++))<<(i*8);

        return new int[]{r, offset};
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
        int[] unserialize_uint32_result;

        unserialize_uint32_result = unserialize_uint32(b, offset);
        len = unserialize_uint32_result[0];
        offset = unserialize_uint32_result[1];

        buf = new byte[len];
        b.subList(offset, len).toArray(ArrayUtils.toObject(buf));
        offset += len;

        e.setFromBytes(buf);
        return offset;
    }

    void serialize_string( List<Byte> b, String s ) {
        b.addAll(Arrays.asList(ArrayUtils.toObject(s.getBytes())));
    }

    Object[] unserialize_string( List<Byte> b, int offset ) {
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

        return new Object[] { s, offset };
    }

    @Override
    public List<Byte> pub_serialize(PubT pub) {
        List<Byte> b;

        b = new ArrayList<>();
        serialize_string(b,  pub.getPairingDesc());
        serialize_element(b, pub.getG());
        serialize_element(b, pub.getH());
        serialize_element(b, pub.getGp());
        serialize_element(b, pub.getGHatAlpha());

        return b;
    }

    @Override
    public PubT pub_unserialize(List<Byte> b, int free) {
        PubT pub;
        int offset;
        Object[] unserialize_string_result;

        pub = new PubT();
        offset = 0;

        unserialize_string_result = unserialize_string(b, offset);
        pub.setPairingDesc((String)unserialize_string_result[0]);
        offset = (int)unserialize_string_result[1];
        pub.getP().getPairingPreProcessingFromBytes(pub.getPairingDesc().getBytes());

        pub.setG(pub.getP().getG1().newRandomElement());
        pub.setH(pub.getP().getG1().newRandomElement());
        pub.setGp(pub.getP().getG2().newRandomElement());
        pub.setGHatAlpha(pub.getP().getGT().newRandomElement());

        offset = unserialize_element(b, offset, pub.getG());
        offset = unserialize_element(b, offset, pub.getH());
        offset = unserialize_element(b, offset, pub.getGp());
        unserialize_element(b, offset, pub.getGHatAlpha());

        if( free != 1 )
            b.clear();

        return pub;
    }

    @Override
    public List<Byte> msk_serialize(MskT msk) {
        List<Byte> b;

        b = new ArrayList<>();
        serialize_element(b, msk.getBeta());
        serialize_element(b, msk.getGAlpha());

        return b;
    }

    @Override
    public MskT msk_unserialize(PubT pub, List<Byte> b, int free) {
        MskT msk;
        int offset;

        msk = new MskT();
        offset = 0;

        msk.setBeta(pub.getP().getZr().newRandomElement());
        msk.setGAlpha(pub.getP().getG2().newRandomElement());

        offset = unserialize_element(b, offset, msk.getBeta());
        unserialize_element(b, offset, msk.getGAlpha());

        if( free != 0 )
            b.clear();

        return msk;
    }

    @Override
    public List<Byte> prv_serialize(PrvT prv) {
        List<Byte> b;
        int i;

        b = new ArrayList<>();

        serialize_element(b, prv.getD());
        serialize_uint32(b, prv.getComps().size());

        for( i = 0; i < prv.getComps().size(); i++ ) {
            serialize_string( b, ((PrvComp)(prv.getComps().get(i))).getAttr());
            serialize_element(b, ((PrvComp)(prv.getComps().get(i))).getD());
            serialize_element(b, ((PrvComp)(prv.getComps().get(i))).getDp());
        }

        return b;
    }

    @Override
    public PrvT prv_unserialize(PubT pub, List<Byte> b, int free) {
        PrvT prv;
        int i;
        int len;
        int offset;
        int[] unserialize_uint32_result;
        Object[] unserialize_string_result;

        prv = new PrvT();
        offset = 0;

        prv.setD(pub.getP().getG2().newRandomElement());
        offset = unserialize_element(b, offset, prv.getD());

        prv.setComps(new ArrayList<>());
        unserialize_uint32_result = unserialize_uint32(b, offset);
        len = unserialize_uint32_result[0];
        offset = unserialize_uint32_result[1];

        for( i = 0; i < len; i++ ) {
            PrvComp c = new PrvComp();

            unserialize_string_result = unserialize_string(b, offset);
            c.setAttr((String)unserialize_string_result[0]);
            offset = (int)unserialize_string_result[1];

            c.setD(pub.getP().getG2().newRandomElement());
            c.setDp(pub.getP().getG2().newRandomElement());

            offset = unserialize_element(b, offset, c.getD());
            offset = unserialize_element(b, offset, c.getDp());

            prv.getComps().add(c);
        }

        if( free != 0 )
            b.clear();

        return prv;
    }

    void  serialize_policy( List<Byte> b, Policy p ) {
        int i;

        serialize_uint32(b, p.getK());

        serialize_uint32(b, p.getChildren().size());
        if( p.getChildren().size() == 0 ) {
            serialize_string( b, p.getAttr());
            serialize_element(b, p.getC());
            serialize_element(b, p.getCp());
        }
        else
            for( i = 0; i < p.getChildren().size(); i++ )
                serialize_policy(b, p.getChildren().get(i));
    }

    Object[] unserialize_policy( PubT pub, List<Byte> b, int offset ) {
        int i;
        int n;
        Policy p;
        int[] unserialize_uint32_result;
        Object[] unserialize_string_result;

        p = new Policy();

        unserialize_uint32_result = unserialize_uint32(b, offset);

        p.setK(unserialize_uint32_result[0]);
        offset = unserialize_uint32_result[1];
        p.setAttr(null);
        p.setChildren(new ArrayList<>());

        unserialize_uint32_result = unserialize_uint32(b, offset);
        n = unserialize_uint32_result[0];
        offset = unserialize_uint32_result[1];
        if( n == 0 ) {
            unserialize_string_result = unserialize_string(b, offset);
            p.setAttr((String)unserialize_string_result[0]);
            offset = (int)unserialize_string_result[1];
            p.setC(pub.getP().getG1().newElement());
            p.setCp(pub.getP().getG1().newElement());
            offset = unserialize_element(b, offset, p.getC());
            offset = unserialize_element(b, offset, p.getCp());
        }
        else
            for( i = 0; i < n; i++ ) {
                Object[] unserialize_policy_result;

                unserialize_policy_result = unserialize_policy(pub, b, offset);
                p.getChildren().add((Policy)unserialize_policy_result[0]);
                offset = (int)unserialize_policy_result[1];
            }

        return new Object[] {p, offset};
    }

    @Override
    public List<Byte> cph_serialize(CphT cph) {
        List<Byte> b;

        b = new ArrayList<>();
        serialize_element(b, cph.getCs());
        serialize_element(b, cph.getC());
        serialize_policy(b, cph.getP());

        return b;
    }

    @Override
    public CphT cph_unserialize(PubT pub, List<Byte> b, int free) {
        CphT cph;
        int offset;
        Object[] unserialize_policy_result;

        cph = new CphT();
        offset = 0;

        cph.setCs(pub.getP().getGT().newElement());
        cph.setC(pub.getP().getG1().newElement());
        offset = unserialize_element(b, offset, cph.getCs());
        offset = unserialize_element(b, offset, cph.getC());
        unserialize_policy_result = unserialize_policy(pub, b, offset);
        cph.setP((Policy)unserialize_policy_result[0]);
        offset = (int)unserialize_policy_result[1];

        if( free != 0 )
            b.clear();

        return cph;
    }
}
