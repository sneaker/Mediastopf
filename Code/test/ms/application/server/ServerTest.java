package ms.application.server;

import ms.utils.filesys.server.FileIOTest;
import ms.utils.server.database.CreateDeleteDomainObjectsTest;
import ms.utils.server.database.DbAdapterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { FileIOTest.class, CreateDeleteDomainObjectsTest.class,
		DbAdapterTest.class })
public class ServerTest {

}
