package domain;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

/**
 * A public key.
 * @see 'bswabe_pub_s'
 */
public class PubT {
    private String pairingDesc;
    private Pairing p;
    private Element g;           /* G_1 */
    private Element h;           /* G_1 */
    private Element gp;          /* G_2 */
    private Element gHatAlpha;   /* G_T */

    public String getPairingDesc() {
        return pairingDesc;
    }

    public void setPairingDesc(String pairingDesc) {
        this.pairingDesc = pairingDesc;
    }

    public Pairing getP() {
        return p;
    }

    public void setP(Pairing p) {
        this.p = p;
    }

    public Element getG() {
        return g;
    }

    public void setG(Element g) {
        this.g = g;
    }

    public Element getH() {
        return h;
    }

    public void setH(Element h) {
        this.h = h;
    }

    public Element getGp() {
        return gp;
    }

    public void setGp(Element gp) {
        this.gp = gp;
    }

    public Element getGHatAlpha() {
        return gHatAlpha;
    }

    public void setGHatAlpha(Element gHatAlpha) {
        this.gHatAlpha = gHatAlpha;
    }
}
