package ms.application.client;

import ms.utils.client.filter.ImageWhiteFilterTest;
import ms.utils.filesys.client.DirectoryObserverTest;
import ms.utils.networking.client.ServerConnectionTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { DirectoryObserverTest.class, ImageWhiteFilterTest.class,
		ServerConnectionTest.class })
public class ClientTest {

}
