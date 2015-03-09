package domain;

import it.unisa.dia.gas.jpbc.Element;

/**
  A master secret key.
 @see bswabe_msk_s
*/
public class MskT {
    private Element beta;        /* Z_r */
    private Element gAlpha;      /* G_2 */

    public Element getBeta() {
        return beta;
    }

    public void setBeta(Element beta) {
        this.beta = beta;
    }

    public Element getgAlpha() {
        return gAlpha;
    }

    public void setgAlpha(Element gAlpha) {
        this.gAlpha = gAlpha;
    }
}
