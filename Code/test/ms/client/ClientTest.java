package ms.client;

import ms.client.filesys.DirectoryObserverTest;
import ms.client.filter.ImageWhiteFilterTest;
import ms.client.networking.ServerConnectionTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { DirectoryObserverTest.class, ImageWhiteFilterTest.class,
		ServerConnectionTest.class })
public class ClientTest {

}
