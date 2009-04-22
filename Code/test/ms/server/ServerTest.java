package ms.server;

import ms.server.database.CreateDeleteDomainObjectsTest;
import ms.server.database.DbAdapterTest;
import ms.server.filesys.FileIOTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { FileIOTest.class, CreateDeleteDomainObjectsTest.class,
		DbAdapterTest.class })
public class ServerTest {

}
