package it.unisa.ordiniProfiloBacco;

import java.sql.Date;
import java.util.List;

public class Ordine
{
	String idOrdine;
	enum stato
	{
		inElaborazione, 
		elaborato,
		spedito,
		consegnato
	}
	Date data;
	String usernameUtente;
	List<Prodotto> prodotti;
	
	public String getIdOrdine()
	{
		return idOrdine;
	}
	public void setIdOrdine(String idOrdine)
	{
		this.idOrdine = idOrdine;
	}
	
	public Date getDate()
	{
		return data;
	}
	public void setDate(Date date)
	{
		this.data = date;
	}
	
	public String getUsernameUtente()
	{
		return usernameUtente;
	}
	public void setUsernameUtente(String usernameUtente)
	{
		this.usernameUtente = usernameUtente;
	}
	
	public List<Prodotto> getProdotti()
	{
		return prodotti;
	}
	public void setProdotti(Prodotto prodotto)
	{
		prodotti.add(prodotto);
	}
	
}