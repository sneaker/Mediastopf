package ms.utils.server.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import ms.domain.Auftrag;
import ms.domain.server.ServerExportMedium;
import ms.utils.server.database.ActiveRecordManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateDeleteDomainObjectsTest {
	
	private static int randomint;

	@Before
	public void setUp() throws Exception {
		Random generator = new Random();
		randomint = generator.nextInt();
				
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testAuftragDbTable() {
		Auftrag myAuftrag = new Auftrag(randomint);
		
		DbAdapter.saveAuftrag(myAuftrag);
		
		
		String sql = "select * from Auftrag WHERE id = " + myAuftrag.getID();
		List<Auftrag> myList = ActiveRecordManager.getObjectList(sql, Auftrag.class);
		
		for (Auftrag item: myList) assertEquals(myAuftrag, item);
		
		DbAdapter.deleteAuftrag(myAuftrag);
		assertTrue(ActiveRecordManager.getObjectList(sql, Auftrag.class).isEmpty());
	}
}
