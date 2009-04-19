package ms;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ms.client.ClientTest;

public class MediaStopfTest extends TestCase {
	public static Test suite() {
	    TestSuite suite = new TestSuite();
	    suite.addTest(ClientTest.suite());
	    return suite;
	}
}
