package ms.server.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import ms.server.domain.Auftrag;
import ms.server.domain.BildItem;
import ms.server.domain.Container;
import ms.server.domain.Einlesegeraet;
import ms.server.domain.Einlesestation;
import ms.server.domain.ExportMedium;
import ms.server.domain.ImportMedium;
import ms.server.domain.Mediensammlung;
import ms.server.domain.MusikItem;
import ms.server.domain.Sammelstation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateDeleteDomainObjectsTest {
	
	private static int randomint1, randomint2, randomint3;

	@Before
	public void setUp() throws Exception {
		Random generator = new Random();
		randomint1 = generator.nextInt();
		randomint2 = generator.nextInt();
		randomint3 = generator.nextInt();
				
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testAuftragDbTable() {
		Auftrag myAuftrag = new Auftrag(randomint1);
		myAuftrag.save();		
		String sql = "select * from Auftrag WHERE id = " + myAuftrag.getID();
		List<Auftrag> myList = ActiveRecordManager.getObjectList(sql, Auftrag.class);
		
		for (Auftrag item: myList) assertEquals(myAuftrag, item);
		
		myAuftrag.delete();
		assertTrue(ActiveRecordManager.getObjectList(sql, Auftrag.class).isEmpty());
	}
	
	@Test
	public void testBildItemDbTable() {
		BildItem myBildItem = new BildItem("Name" + randomint1, "Importdate" + randomint2, "Speicherort" + randomint3, randomint1, "ort" + randomint2, randomint3, randomint1, randomint2, randomint3);
		myBildItem.save();
		String sql = "select Name, Importdatum, Speicherort, BildItem.id as id, Aufloesung, Aufnahmeort, fk_ImportMedium, fk_Container, Breite, Hoehe  from BildItem, Item WHERE BildItem.fk_Item = Item.id and BildItem.id = " + myBildItem.getID();
		List<BildItem> myList = ActiveRecordManager.getObjectList(sql, BildItem.class);
				
		for (BildItem item: myList) assertEquals(myBildItem, item);
		
		myBildItem.delete();
		assertTrue(ActiveRecordManager.getObjectList(sql, BildItem.class).isEmpty());
	}
	
	@Test
	public void testMusikItemDbTable() {
		MusikItem myMusikItem = new MusikItem("Name" + randomint1, "Importdate" + randomint2, "Speicherort" + randomint3, randomint1, "ort" + randomint2, randomint3, randomint1);
		myMusikItem.save();
		String sql = "select MusikItem.id as id, Dauer, Interpret, fk_ImportMedium, fk_Container, Name, Importdatum, Speicherort from MusikItem, Item WHERE MusikItem.fk_Item = Item.id and MusikItem.id =" + myMusikItem.getID();
		List<MusikItem> myList = ActiveRecordManager.getObjectList(sql, MusikItem.class);
				
		for (MusikItem item: myList) assertEquals(myMusikItem, item);
		
		myMusikItem.delete();
		assertTrue(ActiveRecordManager.getObjectList(sql, MusikItem.class).isEmpty());
	}
	
	@Test
	public void testContainerDbTable() {
		Container myContainer = new Container("Name" + randomint1, randomint2);
		myContainer.save();		
		String sql = "select * from Container WHERE id = " + myContainer.getID();
		List<Container> myList = ActiveRecordManager.getObjectList(sql, Container.class);
		
		for (Container item: myList) assertEquals(myContainer, item);
		
		myContainer.delete();
		assertTrue(ActiveRecordManager.getObjectList(sql, Container.class).isEmpty());
	}
	
	@Test
	public void testEinlesegeraetDbTable() {
		Einlesegeraet myEinlesegeraet = new Einlesegeraet("Name" + randomint1, randomint2);
		myEinlesegeraet.save();		
		String sql = "select * from Einlesegeraet WHERE id = " + myEinlesegeraet.getID();
		List<Einlesegeraet> myList = ActiveRecordManager.getObjectList(sql, Einlesegeraet.class);
		
		for (Einlesegeraet item: myList) assertEquals(myEinlesegeraet, item);
		
		myEinlesegeraet.delete();
		assertTrue(ActiveRecordManager.getObjectList(sql, Einlesegeraet.class).isEmpty());
	}
	
	@Test
	public void testEinlesestationDbTable() {
		Einlesestation myEinlesestation = new Einlesestation("Name" + randomint1, randomint2, "Netzwerkadresse" + randomint3);
		myEinlesestation.save();		
		String sql = "select * from Einlesestation WHERE id = " + myEinlesestation.getID();
		List<Einlesestation> myList = ActiveRecordManager.getObjectList(sql, Einlesestation.class);
		
		for (Einlesestation item: myList) assertEquals(myEinlesestation, item);
		
		myEinlesestation.delete();
		assertTrue(ActiveRecordManager.getObjectList(sql, Einlesestation.class).isEmpty());
	}
	
	@Test
	public void testExportMediumDbTable() {
		ExportMedium myExportMedium = new ExportMedium("Name" + randomint1, randomint2, randomint3, randomint1);
		myExportMedium.save();		
		String sql = "select * from Exportmedium WHERE id = " + myExportMedium.getID();
		List<ExportMedium> myList = ActiveRecordManager.getObjectList(sql, ExportMedium.class);
		
		for (ExportMedium item: myList) assertEquals(myExportMedium, item);
		
		myExportMedium.delete();
		assertTrue(ActiveRecordManager.getObjectList(sql, ExportMedium.class).isEmpty());
	}
	
	@Test
	public void testImportMediumDbTable() {
		ImportMedium myImportMedium = new ImportMedium("Name" + randomint1, randomint2, randomint3);
		myImportMedium.save();		
		String sql = "select * from Importmedium WHERE id = " + myImportMedium.getID();
		List<ImportMedium> myList = ActiveRecordManager.getObjectList(sql, ImportMedium.class);
		
		for (ImportMedium item: myList) assertEquals(myImportMedium, item);
		
		myImportMedium.delete();
		assertTrue(ActiveRecordManager.getObjectList(sql, ImportMedium.class).isEmpty());
	}
	
	@Test
	public void testMediensammlungDbTable() {
		Mediensammlung myImportMedium = new Mediensammlung("Name" + randomint1, randomint2, randomint3);
		myImportMedium.save();		
		String sql = "select * from Mediensammlung WHERE id = " + myImportMedium.getID();
		List<Mediensammlung> myList = ActiveRecordManager.getObjectList(sql, Mediensammlung.class);
		
		for (Mediensammlung item: myList) assertEquals(myImportMedium, item);
		
		myImportMedium.delete();
		assertTrue(ActiveRecordManager.getObjectList(sql, Mediensammlung.class).isEmpty());
	}
	
	@Test
	public void testSammelstationDbTable() {
		Sammelstation mySammelstation = new Sammelstation("Name" + randomint1, "Netzwerkadresse" + randomint2);
		mySammelstation.save();		
		String sql = "select * from Sammelstation WHERE id = " + mySammelstation.getID();
		List<Sammelstation> myList = ActiveRecordManager.getObjectList(sql, Sammelstation.class);
		
		for (Sammelstation item: myList) assertEquals(mySammelstation, item);
		
		mySammelstation.delete();
		assertTrue(ActiveRecordManager.getObjectList(sql, Sammelstation.class).isEmpty());
	}
}
