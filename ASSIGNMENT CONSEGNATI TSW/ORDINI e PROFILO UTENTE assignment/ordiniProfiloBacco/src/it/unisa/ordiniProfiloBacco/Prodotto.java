package it.unisa.ordiniProfiloBacco;

public class Prodotto
{
	String idProdotto;
	String nome;
	String descrizione;
	Float gradazione;
	Float prezzo;
	String regione;
	String fotoUrl;
	Boolean eliminato;
	enum tipo
	{
		rosso,
		bianco
	}
    enum sapore
    {
    	secco,
    	dolce
    }
    
}