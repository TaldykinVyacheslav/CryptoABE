package domain;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import java.util.List;

/**
  A private key.

 @see 'bswabe_prv_s'
*/
public class PrvT {
	private Element d;		/* G_2 */
    List comps;			/* bswabe_prv_comp_t's */

	public Element getD() {
		return d;
	}

	public void setD(Element d) {
		this.d = d;
	}

	public List getComps() {
		return comps;
	}

	public void setComps(List comps) {
		this.comps = comps;
	}
}
