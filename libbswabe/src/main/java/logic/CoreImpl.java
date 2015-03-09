package logic;

import domain.CphT;
import domain.MskT;
import domain.PrvT;
import domain.PubT;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jpbc.wrapper.jna.PBCElementType;

import java.lang.reflect.GenericArrayType;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class CoreImpl implements Core {
    private final static String TYPE_A_PARAMS = "type a\n" +
            "q 87807107996633125224377819847540498158068831994142082" +
            "1102865339926647563088022295707862517942266222142315585" +
            "8769582317459277713367317481324925129998224791\n" +
            "h 12016012264891146079388821366740534204802954401251311" +
            "822919615131047207289359704531102844802183906537786776\n" +
            "r 730750818665451621361119245571504901405976559617\n" +
            "exp2 159\n" +
            "exp1 107\n" +
            "sign1 1\n" +
            "sign0 1\n";
    private char[] lastError = new char[256];

    @Override
    public char[] bswabe_error() {
        return lastError;
    }

    private void raiseError(String format, String... args) {
        System.out.printf("raiseError exception");
    }

    private Element element_from_string(Element h, String s) {
        byte[] r = toSHA1(s.getBytes());
        return h.setFromHash(r, 0, r.length);
    }

    private byte[] toSHA1(byte[] convertme) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md.digest(convertme);
    }
    
    @Override
    public void bswabe_setup(PubT pub, MskT msk) {
        Element alpha;

        /* initialize*/
        pub.setPairingDesc(TYPE_A_PARAMS);
        pub.setPairingDesc();
    }

    @Override
    public PrvT[] bswabe_keygen(PubT pub) {
        return new PrvT[0];
    }

    @Override
    public CphT[] bswabe_enc(PubT pub, PBCElementType m, String policy) {
        return new CphT[0];
    }

    @Override
    public int bswabe_dec(PubT pub, PrvT prvT, CphT cph, PBCElementType m) {
        return 0;
    }

    @Override
    public GenericArrayType bswabe_pub_serialize(PubT pub) {
        return null;
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
