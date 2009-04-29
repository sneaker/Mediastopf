package ms.server.database;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import ms.common.domain.Auftrag;
import ms.server.domain.ServerAuftrag;
import ms.server.domain.ServerImportMedium;
import ms.server.domain.ServerMedienSammlung;


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
		ServerAuftrag myAuftrag = new ServerAuftrag(randomint1);
		myAuftrag.save();		
		
		Auftrag DbAuftrag = DbAdapter.getAuftrag(myAuftrag.getID());
		assertEquals(myAuftrag, DbAuftrag);
		
		myAuftrag.delete();
		DbAuftrag = DbAdapter.getAuftrag(myAuftrag.getID());
		assertEquals(null, DbAuftrag);
	}
	
	@Test
	public void testGetMediensammlungList() {
		ServerAuftrag myAuftrag = new ServerAuftrag(randomint1);
		myAuftrag.save();
		ServerMedienSammlung myMediensammlung = new ServerMedienSammlung(myAuftrag.getID(), 1, "Name"+randomint1);
		myMediensammlung.save();
		List<ServerMedienSammlung> MedienDbList = DbAdapter.getMediensammlungList(myAuftrag);
		
		//assertEquals(myMediensammlung, MedienDbList.get(0));
		
		myMediensammlung.delete();
		myAuftrag.delete();
		Auftrag DbAuftrag = DbAdapter.getAuftrag(myAuftrag.getID());
		//assertEquals(null, DbAuftrag);
		myMediensammlung = DbAdapter.getMediensammlung(myMediensammlung.getID());
		//assertEquals(null, myMediensammlung);
	}
	
	
	
	
}
