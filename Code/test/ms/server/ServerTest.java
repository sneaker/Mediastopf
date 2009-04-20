package ms.server;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ms.server.database.CreateDeleteDomainObjectsTest;
import ms.server.database.DbAdapterTest;
import ms.server.filesys.FileIOTest;

public class ServerTest extends TestCase {
	
	public static Test suite() {
	    TestSuite suite = new TestSuite();
	    suite.addTestSuite(FileIOTest.class);
	    suite.addTestSuite(CreateDeleteDomainObjectsTest.class);
	    suite.addTestSuite(DbAdapterTest.class);
	    return suite;
	}
}
