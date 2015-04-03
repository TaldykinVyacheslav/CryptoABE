package domain;

import it.unisa.dia.gas.jpbc.Element;

/**
  A ciphertext. Note that this library only handles encrypting a
  single group element, so if you want to encrypt something bigger,
  you will have to use that group element as a symmetric key for
  hybrid encryption (which you do yourself).

 @see 'bswabe_cph_s'
*/
public class CphT {
	private Element cs;		/* G_T */
	private Element c;		/* G_1 */
	private Policy p;

	public Element getCs() {
		return cs;
	}

	public void setCs(Element cs) {
		this.cs = cs;
	}

	public Element getC() {
		return c;
	}

	public void setC(Element c) {
		this.c = c;
	}

	public Policy getP() {
		return p;
	}

	public void setP(Policy p) {
		this.p = p;
	}
}
