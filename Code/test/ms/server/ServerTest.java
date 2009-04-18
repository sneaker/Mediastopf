package ms.server;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ms.server.filesys.FileIOTest;

public class ServerTest extends TestCase {
	
	public static Test suite() {
	    TestSuite suite = new TestSuite();
	    suite.addTestSuite(FileIOTest.class);
	    return suite;
	}
}
