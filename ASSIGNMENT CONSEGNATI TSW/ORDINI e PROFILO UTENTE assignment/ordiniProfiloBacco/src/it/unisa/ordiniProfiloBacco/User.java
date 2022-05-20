package it.unisa.ordiniProfiloBacco;

import java.util.List;

public class User
{
	private String username;
	private String password;
	private String nome;
	private String cognome;
	private String email;
    enum tipo
    {
    	registrato,
    	amministratore
    }
    List<MetodoPagamento> metodiPagamento;
    List<Indirizzo> indirizzi;

	public User(String username, String password, String nome, String cognome, String email)
	{
		this.username = username;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
	}
	
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	
	public String getCognome()
	{
		return cognome;
	}
	public void setCognome(String cognome)
	{
		this.cognome = cognome;
	}
	
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public List<MetodoPagamento> getMetodiPagamento()
	{
		return metodiPagamento;
	}
	public void setMetodoPagamento(MetodoPagamento metodoPagamento)
	{
		metodiPagamento.add(metodoPagamento);
	}
	public void removeMetodoPagamento(MetodoPagamento metodoPagamento)
	{
		metodiPagamento.remove(metodoPagamento);
	}
	
	public List<Indirizzo> getIndirizzi()
	{
		return indirizzi;
	}
	public void setIndirizzo(Indirizzo indirizzo)
	{
		indirizzi.add(indirizzo);
	}
	public void removeIndirizzo(Indirizzo indirizzo)
	{
		indirizzi.remove(indirizzo);
	}
	
}