package ms.server.database;

import static org.junit.Assert.assertEquals;


import java.util.List;
import java.util.Random;

import ms.server.domain.*;

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
		
		List<Auftrag> myList = DbAdapter.getAuftragList(myAuftrag.getID());
		for (Auftrag item: myList) assertEquals(myAuftrag, item);
		
		myAuftrag.delete();
	}
	
	
	
	

	
	
	
	
	

}
