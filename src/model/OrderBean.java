package model;

import java.sql.Date;

/*
 *   id_ordine varchar(32) primary key,
    stato enum("in elaborazione", "elaborato","spedito","consegnato") not null,
    data date not null,
    username_utente varchar(32),
    foreign key (username_utente) references utente(username) 
 */
public class OrderBean {
	String idOrdine;
	String stato;
	Date date;
	String usernameUtente;
	/**
	 * @return the idOrdine
	 */
	public String getIdOrdine() {
		return idOrdine;
	}
	/**
	 * @param idOrdine the idOrdine to set
	 */
	public void setIdOrdine(String idOrdine) {
		this.idOrdine = idOrdine;
	}
	/**
	 * @return the stato
	 */
	public String getStato() {
		return stato;
	}
	/**
	 * @param stato the stato to set
	 */
	public void setStato(String stato) {
		this.stato = stato;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the usernameUtente
	 */
	public String getUsernameUtente() {
		return usernameUtente;
	}
	/**
	 * @param usernameUtente the usernameUtente to set
	 */
	public void setUsernameUtente(String usernameUtente) {
		this.usernameUtente = usernameUtente;
	}
	
	public OrderBean(String idOrdine, String stato, Date date, String usernameUtente) {
		this.idOrdine = idOrdine;
		this.stato = stato;
		this.date = date;
		this.usernameUtente = usernameUtente;
	}
	public OrderBean(String stato, Date date, String usernameUtente) {
		this.stato = stato;
		this.date = date;
		this.usernameUtente = usernameUtente;
	}
}
