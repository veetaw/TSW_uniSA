package model;

import java.io.Serializable;

public class ProductBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String idProdotto;
	private String nome;
	private String descrizione;
	private Float gradazione;
	private Float prezzo;
	private String regione;
	private String url;
	// private Boolean eliminato;
	private String tipo;
	private String sapore;

	public ProductBean() {

	}

	public ProductBean(String idProdotto, String nome, String descrizione, Float gradazione, Float prezzo, String regione, String url, String tipo, String sapore) {
		this.idProdotto = idProdotto;
		this.nome = nome;
		this.descrizione = descrizione;
		this.gradazione = gradazione;
		this.prezzo = prezzo;
		this.regione = regione;
		this.url = url;
		this.tipo = tipo;
		this.sapore = sapore;
	}

	public String getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(String idProdotto) {
		this.idProdotto = idProdotto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Float getGradazione() {
		return gradazione;
	}

	public void setGradazione(Float gradazione) {
		this.gradazione = gradazione;
	}

	public Float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Float prezzo) {
		this.prezzo = prezzo;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSapore() {
		return sapore;
	}

	public void setSapore(String sapore) {
		this.sapore = sapore;
	}

	@Override
	public java.lang.String toString() {
		return "VinoBean{" +
				"idProdotto='" + idProdotto + '\'' +
				", nome='" + nome + '\'' +
				", descrizione='" + descrizione + '\'' +
				", gradazione=" + gradazione +
				", prezzo=" + prezzo +
				", regione='" + regione + '\'' +
				", url='" + url + '\'' +
				", tipo='" + tipo + '\'' +
				", sapore='" + sapore + '\'' +
				'}';
	}

}
