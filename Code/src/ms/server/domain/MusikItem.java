package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MusikItem extends Item {
	
	private int Dauer; //in seconds
	private String Interpret;
	private int id = NOTINDB;

	public boolean isInDB() {
		return id > NOTINDB;
	}
	
	public MusikItem(String name, String Importdatum, String Speicherort, int Dauer, String Interpret, int fk_ImportMedium, int fk_Container) {
		super(name, Importdatum, Speicherort, fk_ImportMedium, fk_Container);
		this.setDauer(Dauer);
		this.setInterpret(Interpret);
	}
	
	public MusikItem(ResultSet row) throws SQLException {
		this(row.getString("Name"), row.getString("Importdatum"), row.getString("Speicherort"), row.getInt("Dauer"), row.getString("Interpret"), row.getInt("fk_ImportMedium"), row.getInt("fk_Container"));
		this.id = row.getInt("id");
	}

	public void setDauer(int dauer) {
		Dauer = dauer;
	}

	public int getDauer() {
		return Dauer;
	}

	public void setInterpret(String interpret) {
		Interpret = interpret;
	}

	public String getInterpret() {
		return Interpret;
	}

}
