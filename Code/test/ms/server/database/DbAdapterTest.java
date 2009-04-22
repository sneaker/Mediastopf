package ms.server.database;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import ms.server.domain.Auftrag;
import ms.server.domain.Einlesegeraet;
import ms.server.domain.Einlesestation;
import ms.server.domain.ImportMedium;
import ms.server.domain.Mediensammlung;
import ms.server.domain.Sammelstation;

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
	
	@Test
	public void testGetImportMediumList() {
		Mediensammlung myMediensammlung = new Mediensammlung("Name"+randomint1, randomint2, randomint3);
		myMediensammlung.save();
		Einlesegeraet myEinlesegeraet = new Einlesegeraet("Name"+randomint1, randomint2);
		myEinlesegeraet.save();
		ImportMedium myImportMedium = new ImportMedium("Name"+randomint1, myMediensammlung.getID(), myEinlesegeraet.getID());
		myImportMedium.save();
		List<ImportMedium> ImportMediumDBListEinlesegeraet = DbAdapter.getImportMediumList(myEinlesegeraet);
		List<ImportMedium> ImportMediumDBListMediensammlung = DbAdapter.getImportMediumList(myMediensammlung);
		
		assertEquals(ImportMediumDBListEinlesegeraet.get(0), myImportMedium);
		assertEquals(ImportMediumDBListMediensammlung.get(0), myImportMedium);
		
		myImportMedium.delete();
		myMediensammlung.delete();
		myEinlesegeraet.delete();
		myMediensammlung = DbAdapter.getMediensammlung(myMediensammlung.getID());
		assertEquals(null, myMediensammlung);
		myImportMedium = DbAdapter.getImportMediumList(myImportMedium.getID());
		assertEquals(null, myImportMedium);
	}
	
	@Test
	public void testGetSammelstationList() {
		Sammelstation mySammelstation = new Sammelstation("Name" + randomint1, "Netzwerkadresse" + randomint2);
		mySammelstation.save();		
		
		Sammelstation DbSammelstation = DbAdapter.getSammelstation(mySammelstation.getID());
		assertEquals(mySammelstation, DbSammelstation);
		
		mySammelstation.delete();
		DbSammelstation = DbAdapter.getSammelstation(mySammelstation.getID());
		assertEquals(null, DbSammelstation);
	}
	
	@Test
	public void testGetEinlesestationList() {
		Sammelstation mySammelstation = new Sammelstation("Name" + randomint1, "Netzwerkadresse" + randomint2);
		mySammelstation.save();
		Einlesestation myEinlesestation = new Einlesestation("Name"+randomint1, mySammelstation.getID(), "Netzwerkadresse" + randomint2);
		myEinlesestation.save();
		List<Einlesestation> EinlesestationDbList = DbAdapter.getEinlesestation(mySammelstation);
		
		assertEquals(myEinlesestation, EinlesestationDbList.get(0));
		
		myEinlesestation.delete();
		mySammelstation.delete();
		Sammelstation DbSammelstation = DbAdapter.getSammelstation(mySammelstation.getID());
		assertEquals(null, DbSammelstation);
		myEinlesestation = DbAdapter.getEinlesestation(myEinlesestation.getID());
		assertEquals(null, myEinlesestation);
	}
	
	@Test
	public void testGetEinlesegeraetList() {
		Einlesestation myEinlesestation = new Einlesestation("Name" + randomint1, randomint2, "Netzwerkadresse" + randomint3);
		myEinlesestation.save();
		Einlesegeraet myEinlesegeraet = new Einlesegeraet("Name"+randomint1, myEinlesestation.getID());
		myEinlesegeraet.save();
		List<Einlesegeraet> EinlesegeraetDbList = DbAdapter.getEinlesegeraet(myEinlesestation);
		
		assertEquals(myEinlesegeraet, EinlesegeraetDbList.get(0));
		
		myEinlesegeraet.delete();
		myEinlesestation.delete();
		Einlesestation DbEinlesestation = DbAdapter.getEinlesestation(myEinlesestation.getID());
		assertEquals(null, DbEinlesestation);
		myEinlesegeraet = DbAdapter.getEinlesegeraet(myEinlesegeraet.getID());
		assertEquals(null, myEinlesegeraet);
	}
}
