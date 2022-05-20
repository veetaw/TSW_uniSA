package it.unisa.ordiniProfiloBacco;

import java.sql.Date;

public class VisaMastercard implements MetodoPagamento
{
	String nomeCognome;
	Integer pan;
	Date scadenzaDate;
	Integer pin;
	
	public String getNomeCognome()
	{
		return nomeCognome;
	}
	public void setNomeCognome(String nomeCognome)
	{
		this.nomeCognome = nomeCognome;
	}
	
	public Integer getPan()
	{
		return pan;
	}
	public void setPan(Integer pan)
	{
		this.pan = pan;
	}
	
	public Date getScadenzaDate()
	{
		return scadenzaDate;
	}
	public void setScadenzaDate(Date scadenzaDate)
	{
		this.scadenzaDate = scadenzaDate;
	}
	
	public Integer getPin()
	{
		return pin;
	}
	public void setPin(Integer pin)
	{
		this.pin = pin;
	}
	
}