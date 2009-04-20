package ms;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ms.server.ServerTest;

public class MediaStopfTest extends TestCase {
	public static Test suite() {
	    TestSuite suite = new TestSuite();
	    suite.addTest(ServerTest.suite());
	    return suite;
	}
}
