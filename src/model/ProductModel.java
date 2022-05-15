package model;

import java.sql.SQLException;
import java.util.Collection;

public interface ProductModel {
	public void doSave(VinoBean product) throws SQLException;

	public boolean doDelete(String code) throws SQLException;

	public VinoBean doRetrieveByKey(String code) throws SQLException;

	public Collection<VinoBean> doRetrieveAll(String order) throws SQLException;

	// Overload su doRetrieveAll() per semplificare la chiamata al modello dati
	// quando
	// non è richiesto un ordinamento specifico
	public Collection<VinoBean> doRetrieveAll() throws SQLException;
}
