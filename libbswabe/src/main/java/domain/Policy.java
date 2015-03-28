package domain;

import it.unisa.dia.gas.jpbc.Element;
import java.util.List;

/**
 * @see bswabe_policy_t
 */
public class Policy {
	/* serialized */
	private int k;				/* one if leaf, otherwise threshold */
	private String attr;		/* attribute string if leaf, otherwise null */
	private Element c;			/* G_1, only for leaves */
	private Element cp;			/* G_1, only for leaves */
	private List<Policy> children;		/* pointers to bswabe_policy_t's, len == 0 for leaves */

	/* only used during encryption */
	private Polynomial q;

	/* only used during decryption */
	private int satisfiable;
	private int minLeaves;
	private int attri;
	private List satl;

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public Element getC() {
		return c;
	}

	public void setC(Element c) {
		this.c = c;
	}

	public Element getCp() {
		return cp;
	}

	public void setCp(Element cp) {
		this.cp = cp;
	}

	public List<Policy> getChildren() {
		return children;
	}

	public void setChildren(List<Policy> children) {
		this.children = children;
	}

	public Polynomial getQ() {
		return q;
	}

	public void setQ(Polynomial q) {
		this.q = q;
	}

	public int getSatisfiable() {
		return satisfiable;
	}

	public void setSatisfiable(int satisfiable) {
		this.satisfiable = satisfiable;
	}

	public int getMinLeaves() {
		return minLeaves;
	}

	public void setMinLeaves(int minLeaves) {
		this.minLeaves = minLeaves;
	}

	public int getAttri() {
		return attri;
	}

	public void setAttri(int attri) {
		this.attri = attri;
	}

	public List getSatl() {
		return satl;
	}

	public void setSatl(List satl) {
		this.satl = satl;
	}
}
