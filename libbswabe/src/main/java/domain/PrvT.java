package domain;

import it.unisa.dia.gas.jpbc.Pairing;
import java.util.List;

/**
  A private key.

 @see bswabe_prv_s
*/
public class PrvT {
	private Pairing d;		/* G_2 */
    List comps;			/* bswabe_prv_comp_t's */

	public Pairing getD() {
		return d;
	}

	public void setD(Pairing d) {
		this.d = d;
	}

	public List getComps() {
		return comps;
	}

	public void setComps(List comps) {
		this.comps = comps;
	}
}
