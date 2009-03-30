package ch.nomoresecrets.mediastopf.client.logic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AuftragsListeTest {
	
	private AuftragsListe al;
	private Auftrag testAuftrag;

	@Before
	public void setUp() throws Exception {
		al = new AuftragsListe();
		testAuftrag = new Auftrag("id833", "Franz Konzikofer", "43 CDs");
	}

	@Test
	public void testAdd() {
		al.add(testAuftrag);
		assertEquals(testAuftrag, al.get(0));
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void testRemoveAuftrag() {
		al.add(testAuftrag);
		al.remove(testAuftrag);
		al.get(0);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void testRemoveInt() {
		al.add(testAuftrag);
		al.remove(0);
		al.get(0);
	}

	@Test
	public void testGet() {
		al.add(testAuftrag);
		assertEquals(al.get(0), testAuftrag);
	}

	@Test
	public void testSize() {
		al.add(testAuftrag);
		al.add(testAuftrag);
		assertEquals(2, al.size());
		al.remove(1);
		assertEquals(1, al.size());
	}

}
