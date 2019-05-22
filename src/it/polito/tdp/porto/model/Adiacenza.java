package it.polito.tdp.porto.model;

public class Adiacenza {
	
	private Author autore1;
	private Author autore2;
	private Paper pubblicazione;
	
	public Adiacenza(Author autore1, Author autore2, Paper pubblicazione) {
		super();
		this.autore1 = autore1;
		this.autore2 = autore2;
		this.pubblicazione = pubblicazione;
	}

	public Author getAutore1() {
		return autore1;
	}

	public void setAutore1(Author autore1) {
		this.autore1 = autore1;
	}

	public Author getAutore2() {
		return autore2;
	}

	public void setAutore2(Author autore2) {
		this.autore2 = autore2;
	}

	public Paper getPubblicazione() {
		return pubblicazione;
	}

	public void setPubblicazione(Paper pubblicazione) {
		this.pubblicazione = pubblicazione;
	}

	@Override
	public String toString() {
		return "Adiacenza [autore1=" + autore1 + ", autore2=" + autore2 + ", pubblicazione=" + pubblicazione + "]";
	}
	
	

}
