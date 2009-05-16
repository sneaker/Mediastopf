package ms.utils.server.database;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import ms.domain.Auftrag;
import ms.utils.server.database.DbAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DbAdapterTest {
	
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
	public void testSaveGetDeleteAuftragList() {
		Auftrag myAuftrag = new Auftrag(randomint1);
				
		DbAdapter.saveAuftrag(myAuftrag);
		
		Auftrag DbAuftrag = DbAdapter.getAuftrag(myAuftrag.getID());
		assertEquals(randomint1, DbAuftrag.getStatus());
				
		assertEquals(true, DbAdapter.deleteAuftrag(myAuftrag));
		assertEquals(false, DbAdapter.deleteAuftrag(myAuftrag));
		
		DbAuftrag = DbAdapter.getAuftrag(myAuftrag.getID());
		assertEquals(null, DbAuftrag);
	}
}
