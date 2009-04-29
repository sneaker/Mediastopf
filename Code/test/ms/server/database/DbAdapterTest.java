package ms.server.database;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import ms.server.domain.Auftrag;
import ms.server.domain.ImportMedium;
import ms.server.domain.Mediensammlung;


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
	public void testGetAuftragList() {
		Auftrag myAuftrag = new Auftrag(randomint1);
		myAuftrag.save();		
		
		Auftrag DbAuftrag = DbAdapter.getAuftrag(myAuftrag.getID());
		assertEquals(myAuftrag, DbAuftrag);
		
		myAuftrag.delete();
		DbAuftrag = DbAdapter.getAuftrag(myAuftrag.getID());
		assertEquals(null, DbAuftrag);
	}
	
	@Test
	public void testGetMediensammlungList() {
		Auftrag myAuftrag = new Auftrag(randomint1);
		myAuftrag.save();
		Mediensammlung myMediensammlung = new Mediensammlung("Name"+randomint1, 1, myAuftrag.getID());
		myMediensammlung.save();
		List<Mediensammlung> MedienDbList = DbAdapter.getMediensammlungList(myAuftrag);
		
		assertEquals(myMediensammlung, MedienDbList.get(0));
		
		myMediensammlung.delete();
		myAuftrag.delete();
		Auftrag DbAuftrag = DbAdapter.getAuftrag(myAuftrag.getID());
		assertEquals(null, DbAuftrag);
		myMediensammlung = DbAdapter.getMediensammlung(myMediensammlung.getID());
		assertEquals(null, myMediensammlung);
	}
	
	
	
	
}
