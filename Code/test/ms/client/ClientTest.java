package ms.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ms.client.filesys.DirectoryObserverTest;
import ms.client.filter.ImageWhiteFilterTest;
import ms.client.networking.ServerConnectionTest;


public class ClientTest extends TestCase {

	public static Test suite() {
	    TestSuite suite = new TestSuite();
	    suite.addTestSuite(DirectoryObserverTest.class);
	    suite.addTestSuite(ServerConnectionTest.class);
	    suite.addTest(ImageWhiteFilterTest.suite());
	    return suite;
	}
}
