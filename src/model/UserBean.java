package model;

/*
 */
public class UserBean {
	private String username;
	private String nome;
	private String cognome;
	private String mail;
	private String tipo;
	private String via;
	private Integer numeroCivico;
	private String citta;
	private String nazione;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @return the via
	 */
	public String getVia() {
		return via;
	}

	/**
	 * @return the numeroCivico
	 */
	public Integer getNumeroCivico() {
		return numeroCivico;
	}

	/**
	 * @return the citta
	 */
	public String getCitta() {
		return citta;
	}

	/**
	 * @return the nazione
	 */
	public String getNazione() {
		return nazione;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @param via the via to set
	 */
	public void setVia(String via) {
		this.via = via;
	}

	/**
	 * @param numeroCivico the numeroCivico to set
	 */
	public void setNumeroCivico(Integer numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

	/**
	 * @param citta the citta to set
	 */
	public void setCitta(String citta) {
		this.citta = citta;
	}

	/**
	 * @param nazione the nazione to set
	 */
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public UserBean(String username, String nome, String cognome, String mail, String tipo, String via,
			Integer numeroCivico, String citta, String nazione) {
		super();
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.mail = mail;
		this.tipo = tipo;
		this.via = via;
		this.numeroCivico = numeroCivico;
		this.citta = citta;
		this.nazione = nazione;
	}

	public UserBean() {

	}

	@Override
	public String toString() {
		return "UserBean [username=" + username + ", nome=" + nome + ", cognome=" + cognome + ", mail=" + mail
				+ ", tipo=" + tipo + ", via=" + via + ", numeroCivico=" + numeroCivico + ", citta=" + citta
				+ ", nazione=" + nazione + "]";
	}

}
