package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BildItem extends Item {
	
	private int Aufloesung, Breite, Hoehe; //in seconds
	private String Aufnahmeort;
	private int id = NOTINDB;

	public boolean isInDB() {
		return id > NOTINDB;
	}
	
	public BildItem(String name, String Importdatum, String Speicherort, int Aufloesung, String Aufnahmeort, int fk_ImportMedium, int fk_Container, int Breite, int Hoehe) {
		super(name, Importdatum, Speicherort, fk_ImportMedium, fk_Container);
		this.setAufloesung(Aufloesung);
		this.setAufnahmeort(Aufnahmeort);
		this.setBreite(Breite);
		this.setHoehe(Hoehe);
	}
	
	public BildItem(ResultSet row) throws SQLException {
		this(row.getString("Name"), row.getString("Importdatum"), row.getString("Speicherort"), row.getInt("Aufloesung"), row.getString("Aufnahmeort"), row.getInt("fk_ImportMedium"), row.getInt("fk_Container"), row.getInt("Breite"), row.getInt("Hoehe"));
		this.id = row.getInt("id");
	}

	public void setAufloesung(int Aufloesung) {
		this.Aufloesung = Aufloesung;
	}

	public int getAufloesung() {
		return Aufloesung;
	}

	public void setAufnahmeort(String Aufnahmeort) {
		this.Aufnahmeort = Aufnahmeort;
	}

	public String getAufnahmeort() {
		return Aufnahmeort;
	}

	public void setBreite(int breite) {
		Breite = breite;
	}

	public int getBreite() {
		return Breite;
	}

	public void setHoehe(int hoehe) {
		Hoehe = hoehe;
	}

	public int getHoehe() {
		return Hoehe;
	}
	
	@Override
	public String toString() {
		return "Bilditem-ID: " + id + " Name: " + Name + " Importdatum: " + Importdatum + " Speicherort: " + Speicherort;
	}

}
