package logic;

import domain.*;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.wrapper.jna.PBCElementType;

import java.lang.reflect.GenericArrayType;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        pub.setPairingDesc(new String(TYPE_A_PARAMS));
        pub.setP(PairingFactory.getPairing(pub.getPairingDesc()));

        pub.setG(pub.getP().getG1().newRandomElement());
        pub.setH(pub.getP().getG1().newRandomElement());
        pub.setGp(pub.getP().getG2().newRandomElement());
        pub.setGHatAlpha(pub.getP().getGT().newRandomElement());
        alpha = pub.getP().getGT().newRandomElement();
        msk.setBeta(pub.getP().getZr().newRandomElement());
        msk.setGAlpha(pub.getP().getG2().newRandomElement());

        /* compute */

        msk.setGAlpha(pub.getGp().powZn(alpha));
        pub.setH(pub.getG().powZn(msk.getBeta()));
        pub.setGHatAlpha(pub.getP().pairing(pub.getG(), msk.getGAlpha()));
    }

    @Override
    public PrvT bswabe_keygen(PubT pub, MskT msk, String[] attributes) {
        PrvT prv;
        Element gR;
        Element r;
        Element betaInv;

        /* initialize */

        prv = new PrvT();

        prv.setD(pub.getP().getZr().newRandomElement());
        gR = pub.getP().getG2().newRandomElement();
        r = pub.getP().getZr().newRandomElement();
        betaInv = pub.getP().getZr().newRandomElement();

        prv.setComps(new ArrayList(1));

        /* compute */

        gR = pub.getGp().powZn(r);
        prv.setD(msk.getGAlpha().mul(r));
        betaInv = msk.getBeta().invert();
        prv.setD(prv.getD().powZn(betaInv));

        while(attributes.length > 0) {
            PrvComp c;
            Element hRp;
            Element rp;

            c = new PrvComp();
            c.setAttr(attributes[0]);
            attributes = Arrays.copyOfRange(attributes, 1, attributes.length);

            c.setD(pub.getP().getG2().newRandomElement());
            c.setDp(pub.getP().getG1().newElement());
            hRp = pub.getP().getG2().newRandomElement();
            rp = pub.getP().getZr().newRandomElement();

            hRp.setFromBytes(c.getAttr().getBytes());

            hRp = hRp.powZn(rp);

            c.setD(gR.mul(hRp));
            c.setDp(pub.getG().mul(rp));

            prv.getComps().add(c);
        }

        return prv;
    }

    Policy base_node( int k, String s )
    {
        Policy p = new Policy();

        p.setK(k);
        p.setAttr(s != null || s.length() != 0 ? new String(s) : "");
        p.setChildren(new ArrayList<Policy>());
        p.setQ(null);

        return p;
    }

    Policy parse_policy_postfix(String s) {
        String[] toks;
        String[] curToks;
        String tok;
        List<Policy> stack;
        Policy root;

        toks = s.split(" ");
        curToks = toks;
        stack = new ArrayList<Policy>();

        while(curToks.length != 0) {
            int i, k, n;

            tok = toks[0];
            toks = Arrays.copyOfRange(toks, 1, toks.length);

            if(tok == null || tok.length() == 0) {
                continue;
            }


            Pattern p = Pattern.compile("(\\d+)of(\\d+)");
            Matcher m = p.matcher(tok);

            if(m.groupCount() != 2) {
                stack.add(base_node(1, tok));
            } else {
                k = Integer.parseInt(m.group(1));
                n= Integer.parseInt(m.group(2));
                if( k < 1 ) {
                    raiseError(String.format("error parsing \"%s\": trivially satisfied operator \"%s\"\n", s, tok));
                    return null;
                } else if(k > n) {
                    raiseError("error parsing \"%s\": unsatisfiable operator \"%s\"\n", s, tok);
                    return null;
                } else if( n == 1 ) {
                    raiseError("error parsing \"%s\": identity operator \"%s\"\n", s, tok);
                    return null;
                } else if( n > stack.size() ) {
                    raiseError("error parsing \"%s\": stack underflow at \"%s\"\n", s, tok);
                    return null;
                }

                /* pop n things and fill in children */
                Policy node = base_node(k, null);
                node.setChildren(new ArrayList<Policy>(n));
                for( i = n - 1; i >= 0; i-- ) {
                    node.getChildren().set(i, stack.get(stack.size() - 1));
                    stack.remove(stack.size() - 1);
                }


            /* push result */
                stack.add(node);
            }
        }

        if(stack.size() > 1) {
            raiseError("error parsing \"%s\": extra tokens left on stack\n", s);
            return null;
        } else if(stack.size() < 1) {
            raiseError("error parsing \"%s\": empty policy\n", s);
            return null;
        }

        root = stack.get(0);

        return root;
    }

    Polynomial rand_poly( int deg, Element zero_val )
    {
        int i;
        Polynomial q;

        q = new Polynomial();
        q.setDeg(deg);

        for( i = 0; i < q.getDeg() + 1; i++ ) {
            (q.getCoef()[i]).set(zero_val);
        }

        q.getCoef()[0] = zero_val;

        for( i = 1; i < q.getDeg() + 1; i++ ){
            q.getCoef()[i].setToRandom();
        }

        return q;
    }


    // q is return value of the function
    Polynomial eval_poly( Element r, Polynomial q, Element x ) {
        int i;
        Element s, t;

        s = r.duplicate();
        t = r.duplicate();

        r.setToZero();
        t.setToOne();

        for( i = 0; i < q.getDeg() + 1; i++ ) {
		    /* r += q->coef[i] * t */
            s = q.getCoef()[i].mul(t);
            r = r.add(s);

		    /* t *= x */
            t = t.mul(x);
        }

        return q;
    }

    void fill_policy( Policy p, PubT pub, Element e ) {
        int i;
        Element r;
        Element t;
        Element h;

        r = pub.getP().getZr().newRandomElement();
        t = pub.getP().getZr().newRandomElement();
        h = pub.getP().getG2().newRandomElement();

        p.setQ(rand_poly(p.getK() - 1, e));

        if( p.getChildren().size() == 0 ) {
            p.setC(pub.getP().getG1().newRandomElement());
            p.setCp(pub.getP().getG2().newRandomElement());

            element_from_string(h, p.getAttr());
            p.setC(pub.getG().powZn(p.getQ().getCoef()[0]));
            p.setCp(pub.getH().powZn(p.getQ().getCoef()[0]));
        } else {
            for( i = 0; i < p.getChildren().size(); i++ ) {
                r.set(i + 1);
                p.setQ(eval_poly(t, p.getQ(), r));
                fill_policy(p.getChildren().get(i), pub, t);
            }
        }
    }

    @Override
    public CphT bswabe_enc(PubT pub, Element m, String policy) {
        CphT cph;
        Element s;

        /* initialize */

        cph = new CphT();

        s = pub.getP().getZr().newRandomElement();
        m = pub.getP().getGT().newRandomElement();
        cph.setCs(pub.getP().getGT().newRandomElement());
        cph.setC(pub.getP().getG1().newRandomElement());
        cph.setP(parse_policy_postfix(policy));

        /* compute */

        cph.setCs(pub.getGHatAlpha().powZn(s));
        cph.setCs(cph.getCs().mul(m));

        cph.setC(pub.getH().powZn(s));

        fill_policy(cph.getP(), pub, s);

        return cph;
    }

    void check_sat( Policy p, PrvT prv )
    {
        int i, l;

        p.setSatisfiable(0);
        if( p.getChildren().size() == 0 ) {
            for( i = 0; i < prv.getComps().size(); i++ ) {
                if (((PrvComp) prv.getComps().get(i)).getAttr().compareTo(p.getAttr()) != 0) {
                    p.setSatisfiable(1);
                    p.setAttri(i);
                    break;
                }
            }
        } else {
            for( i = 0; i < p.getChildren().size(); i++ ) {
                check_sat(p.getChildren().get(i), prv);
            }

            l = 0;
            for( i = 0; i < p.getChildren().size(); i++ )
                if(((Policy)(p.getChildren().get(i))).getSatisfiable() != 0 ) {
                    l++;
                }

            if( l >= p.getK() ) {
                p.setSatisfiable(1);
            }
        }
    }

    void pick_sat_naive( Policy p, PrvT prv )
    {
        int i, k, l;

        assert(p.getSatisfiable() == 1);

        if( p.getChildren().size() == 0 ) {
            return;
        }

        p.setSatl(new ArrayList());

        l = 0;
        for( i = 0; i < p.getChildren().size() && l < p.getK(); i++ ) {
            if (p.getChildren().get(i).getSatisfiable() != 0) {
                pick_sat_naive(p.getChildren().get(i), prv);
                l++;
                k = i + 1;
                p.getSatl().add(k);
            }
        }
    }

    private Policy curCompPol;
    int cmp_int( int a, int b )
    {
        int k, l;

        k = ((Policy) curCompPol.getChildren().get(a)).getMinLeaves();
        l = ((Policy) curCompPol.getChildren().get(b)).getMinLeaves();

        return
                k <  l ? -1 :
                        k == l ?  0 : 1;
    }


    void pick_sat_min_leaves( Policy p, PrvT prv )
    {
        int i, k, l;
        Integer[] c;

        assert(p.getSatisfiable() == 1);

        if( p.getChildren().size() == 0 ) {
            p.setMinLeaves(1);
        } else {
            for( i = 0; i < p.getChildren().size(); i++ ) {
                if ((p.getChildren().get(i)).getSatisfiable() != 0) {
                    pick_sat_min_leaves(p.getChildren().get(i), prv);
                }
            }

            c = new Integer[p.getChildren().size()];
            for( i = 0; i < p.getChildren().size(); i++ ) {
                c[i] = i;
            }

            curCompPol = p;
            Arrays.sort(c, 0, c.length, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return cmp_int(o1, o2);
                }
            });

            p.setSatl(new ArrayList());
            p.setMinLeaves(0);
            l = 0;

            for( i = 0; i < p.getChildren().size() && l < p.getK(); i++ )
                if( ((Policy) p.getChildren().get(c[i])).getSatisfiable() != 0 )
            {
                l++;
                p.setMinLeaves(p.getMinLeaves() + ((Policy) p.getChildren().get(c[i])).getSatisfiable());
                k = c[i] + 1;
                p.getSatl().add(k);
            }
            assert(l == p.getK());
        }
    }

    Element lagrange_coef( Element r, List s, int i ) {
        int j, k;
        Element t;

        t = r.duplicate();

        r = r.setToOne();
        for( k = 0; k < s.size(); k++ )
        {
            j = (int)(s.get(k));
            if( j == i )
                continue;
            t.set(j);
            r = r.mul(t); /* num_muls++; */
            t.set(i - j);
            t.invert();
            r = r.mul(t); /* num_muls++; */
        }

        return r;
    }

    Element dec_leaf_naive( Element r, Policy p, PrvT prv, PubT pub ) {
        PrvComp c;
        Element s;

        c = (PrvComp)prv.getComps().get(p.getAttri());

        s = pub.getP().getGT().newRandomElement();

        r = pub.getP().pairing(p.getC(), c.getD());
        s = pub.getP().pairing(p.getCp(), c.getDp());

        s.invert();
        r = r.mul(s);
        return r;
    }

    Element dec_node_naive(Element r, Policy p, PrvT prv, PubT pub) {
        assert p.getSatisfiable() != 0;
        if( p.getChildren().size() == 0 )
            r = dec_leaf_naive(r, p, prv, pub);
        else
            r = dec_internal_naive(r, p, prv, pub);
        return r;
    }

    Element dec_internal_naive( Element r, Policy p, PrvT prv, PubT pub ) {
        int i;
        Element s;
        Element t;

        s = pub.getP().getGT().newRandomElement();
        t = pub.getP().getZr().newRandomElement();

        r = r.setToOne();
        for( i = 0; i < p.getSatl().size(); i++ ) {
            s = dec_node_naive(s, (p.getChildren().get((int)p.getSatl().get(i) - 1)), prv, pub);
            t = lagrange_coef(t, p.getSatl(), (int)p.getSatl().get(i));
            s = s.powZn(t); /* num_exps++; */
            r = r.mul(s); /* num_muls++; */
        }

        return r;
    }

    Element dec_naive( Element r, Policy p, PrvT prv, PubT pub ) {
        return dec_node_naive(r, p, prv, pub);
    }


    void dec_leaf_merge( Element exp, Policy p, PrvT prv, PubT pub ) {
        PrvComp c;
        Element s;

        c =(PrvComp)prv.getComps().get(p.getAttri());

        if( c.getUsed() != 0 ) {
            c.setUsed(1);
            c.setZ(pub.getP().getG1().newRandomElement());
            c.setZp(pub.getP().getG2().newRandomElement());
            c.getZ().setToOne();
            e;e
        }

        element_init_G1(s, pub->p);

        element_pow_zn(s, p->c, exp); /* num_exps++; */
        element_mul(c->z, c->z, s); /* num_muls++; */

        element_pow_zn(s, p->cp, exp); /* num_exps++; */
        element_mul(c->zp, c->zp, s); /* num_muls++; */

        element_clear(s);
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
