package domain;


import it.unisa.dia.gas.jpbc.Element;

/**
 * @see 'bswabe_polynomial_t'
 */
public class Polynomial {
	private int deg;

	/* coefficients from [0] x^0 to [deg] x^deg */
	private Element[] coef;		/* G_T (of length deg + 1) */

	public int getDeg() {
		return deg;
	}

	public void setDeg(int deg) {
		this.deg = deg;
	}

	public Element[] getCoef() {
		return coef;
	}

	public void setCoef(Element[] coef) {
		this.coef = coef;
	}
}
