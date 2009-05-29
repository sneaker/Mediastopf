package ms.utils.server.database;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import ms.domain.Auftrag;
import ms.utils.server.database.SqlDbAdapter;

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

	
	//TODO: Fix this test
	@Test
	public void testSaveGetDeleteAuftragList() {
		Auftrag myAuftrag = new Auftrag(randomint1);
		
		SqlDbAdapter sqldbadapter = new SqlDbAdapter();
		
		int asserted_id = sqldbadapter.saveAuftrag(myAuftrag);
		
		Auftrag DbAuftrag = sqldbadapter.getAuftrag(myAuftrag.getID());
		assertEquals(asserted_id, DbAuftrag.getID());
				
		assertEquals(true, sqldbadapter.deleteAuftrag(myAuftrag));
		assertEquals(false, sqldbadapter.deleteAuftrag(myAuftrag));
		
		DbAuftrag = sqldbadapter.getAuftrag(myAuftrag.getID());
		assertEquals(null, DbAuftrag);
	}
}
