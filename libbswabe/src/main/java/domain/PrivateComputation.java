package domain;

import it.unisa.dia.gas.jpbc.Element;

/**
 * Private computation
 * @see 'bswabe_prv_comp_t'
 */
public class PrivateComputation {
	/* these actually get serialized */
	String attr;
	Element d;		/* G_2 */
    Element dp;		/* G_2 */

	/* only used during dec (only by dec_merge) */
	int used;
    Element z;		/* G_1 */
    Element zp;		/* G_1 */

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public Element getD() {
		return d;
	}

	public void setD(Element d) {
		this.d = d;
	}

	public Element getDp() {
		return dp;
	}

	public void setDp(Element dp) {
		this.dp = dp;
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
	}

	public Element getZ() {
		return z;
	}

	public void setZ(Element z) {
		this.z = z;
	}

	public Element getZp() {
		return zp;
	}

	public void setZp(Element zp) {
		this.zp = zp;
	}
}
