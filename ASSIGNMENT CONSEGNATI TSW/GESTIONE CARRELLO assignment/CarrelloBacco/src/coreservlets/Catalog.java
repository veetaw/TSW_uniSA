package coreservlets;

public class Catalog
{
	private static CatalogItem[] items = 
		{ 
				new CatalogItem("vinoRosso001", 
									"<I>Aglianico del Vulture 'Synthesi' Paternoster 2017 </I>", 
										" L'Aglianico del Vulture \"Synthesi\" è un vino rosso pieno, "
										+ "intenso e avvolgente, maturato per almeno 6 mesi in botte grande e in barrique. "
										+ "All'assaggio è ricco, morbido e asciutto, "
										+ "contrassegnato da piacevoli sentori eterei "
										+ "e da profumi di frutta rossa ed erbe aromatiche",
											39.95),
				
				new CatalogItem("vinoRosso002", 
									"<I>Amarone Classico Tommasi 2017</I> ",
										"L’Amarone della Valpolicella Classico di Tommasi "
										+ "è un punto di riferimento della sua categoria, "
										+ "un vino rosso profondo e potente "
										+ "maturato per 30 mesi in grandi botti di rovere. "
										+ "Un intenso spettro olfattivo contrassegnato di frutta sotto spirito, "
										+ "cioccolato, grafite ed erbe balsamiche emerge da una trama gustativa materica "
										+ "e complessa, saporita e persistente",
											49.99),
				
				new CatalogItem("vinoBianco001", 
									"<I>Chardonnay Feudi di Romans 2021",
										"Il Chardonnay di Feudi di Romans è un vino bianco delicato "
										+ "e fresco che nasce in prossimità del fiume Isonzo. "
										+ "La vinfificazione e affinamento in acciaio "
										+ "permettono di preservare tutta l'integrità e freschezza del frutto. "
										+ "Note principalmente fruttate di agrumi, mela e pera "
										+ "con leggeri timbri floreali e minerali animano un corpo equilibrato "
										+ "e pulito, dotato di mineralità e chiusura piacevolmente sapida",
											19.95),
				
				new CatalogItem("vinoBianco002", 
									"<I>Pinot Bianco 'Schulthauser' San Michele Appiano 2021",
										"Il Pinot Bianco \"Schulthauser\" di San Michele Appiano "
										+ "è un vino bianco leggero, giovane e fresco. "
										+ "Al naso è di espressione fruttata, di agrumi e biancospino. "
										+ "Al palato rivela una piacevole eleganza, è minerale "
										+ "e di bella corrispondenza con la sensazione olfattiva",
											29.75),
				
				new CatalogItem("vinoBianco003", 
									"<I>Trebbiano 'Bianco di Ciccio' Zaccagnini 2021",
										"Il “Tralcetto” Trebbiano d'Abruzzo \"Bianco di Ciccio\" "
										+ "è un vino bianco abruzzese dal carattere leggero, fresco, semplice "
										+ "e immediato, vinificato solo in acciaio. "
										+ "Ha un profilo aromatico delicato e fragrante, "
										+ "contrassegnato da note di fiori di campo e frutta fresca. "
										+ "Il sorso è agile, beverino, armonico ed equilibrato",
											59.90) };

	public static CatalogItem getItem(String itemID)
	{
		CatalogItem item;
		
		if (itemID == null)
		{
			return (null);
		}
		
		for (int i = 0; i < items.length; i++)
		{
			item = items[i];
			
			if (itemID.equals(item.getItemID()))
			{
				return (item);
			}
		}
		return (null);
	}
	
}