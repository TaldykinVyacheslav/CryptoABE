package domain;

import it.unisa.dia.gas.jpbc.Pairing;

/**
 * @see bswabe_prv_comp_t
 */
public class PrvComp {
    private String attr;
    private Pairing d;		/* G_2 */
    private Pairing dp;		/* G_2 */

    private int used;
    private Pairing z;		/* G_1 */
    private Pairing zp;		/* G_1 */

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public Pairing getD() {
        return d;
    }

    public void setD(Pairing d) {
        this.d = d;
    }

    public Pairing getDp() {
        return dp;
    }

    public void setDp(Pairing dp) {
        this.dp = dp;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public Pairing getZ() {
        return z;
    }

    public void setZ(Pairing z) {
        this.z = z;
    }

    public Pairing getZp() {
        return zp;
    }

    public void setZp(Pairing zp) {
        this.zp = zp;
    }
}
