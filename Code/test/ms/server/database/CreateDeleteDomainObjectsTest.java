package ms.server.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import ms.server.domain.Auftrag;


import ms.server.domain.ExportMedium;
import ms.server.domain.ImportMedium;
import ms.server.domain.Mediensammlung;



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
	
	
}
