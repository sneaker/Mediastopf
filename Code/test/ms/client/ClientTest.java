package ms.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ms.client.filesys.DirectoryObserverTest;

public class ClientTest extends TestCase {

	public static Test suite() {
	    TestSuite suite = new TestSuite();
	    suite.addTestSuite(DirectoryObserverTest.class);
	    return suite;
	}
}
