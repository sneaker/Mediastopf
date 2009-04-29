package ms.server.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import ms.common.domain.Auftrag;
import ms.common.domain.ExportMedium;
import ms.common.domain.ImportMedium;
import ms.common.domain.MedienSammlung;
import ms.server.domain.ServerAuftrag;


import ms.server.domain.ServerExportMedium;
import ms.server.domain.ServerImportMedium;
import ms.server.domain.ServerMedienSammlung;



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
		ServerAuftrag myAuftrag = new ServerAuftrag(randomint1);
		myAuftrag.save();		
		String sql = "select * from Auftrag WHERE id = " + myAuftrag.getID();
		List<ServerAuftrag> myList = ActiveRecordManager.getObjectList(sql, ServerAuftrag.class);
		
		for (Auftrag item: myList) assertEquals(myAuftrag, item);
		
		myAuftrag.delete();
		assertTrue(ActiveRecordManager.getObjectList(sql, ServerAuftrag.class).isEmpty());
	}
	
	
	
	
	@Test
	public void testExportMediumDbTable() {
		ServerExportMedium myExportMedium = new ServerExportMedium("Name" + randomint1, randomint2, randomint3, randomint1);
		myExportMedium.save();		
		String sql = "select * from Exportmedium WHERE id = " + myExportMedium.getID();
		List<ServerExportMedium> myList = ActiveRecordManager.getObjectList(sql, ServerExportMedium.class);
		
		for (ExportMedium item: myList) assertEquals(myExportMedium, item);
		
		myExportMedium.delete();
		assertTrue(ActiveRecordManager.getObjectList(sql, ServerExportMedium.class).isEmpty());
	}
	
	@Test
	public void testImportMediumDbTable() {
		ServerImportMedium myImportMedium = new ServerImportMedium("Name" + randomint1, randomint2, randomint3);
		myImportMedium.save();		
		String sql = "select * from Importmedium WHERE id = " + myImportMedium.getID();
		List<ServerImportMedium> myList = ActiveRecordManager.getObjectList(sql, ServerImportMedium.class);
		
		for (ImportMedium item: myList) assertEquals(myImportMedium, item);
		
		myImportMedium.delete();
		assertTrue(ActiveRecordManager.getObjectList(sql, ServerImportMedium.class).isEmpty());
	}
	
	@Test
	public void testMediensammlungDbTable() {
		ServerMedienSammlung myImportMedium = new ServerMedienSammlung("Name" + randomint1, randomint2, randomint3);
		myImportMedium.save();		
		String sql = "select * from Mediensammlung WHERE id = " + myImportMedium.getID();
		List<ServerMedienSammlung> myList = ActiveRecordManager.getObjectList(sql, ServerMedienSammlung.class);
		
		for (MedienSammlung item: myList) assertEquals(myImportMedium, item);
		
		myImportMedium.delete();
		assertTrue(ActiveRecordManager.getObjectList(sql, ServerMedienSammlung.class).isEmpty());
	}
	
	
}
