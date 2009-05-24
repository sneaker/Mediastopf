package ms.application.client;

import ms.utils.ImageWhiteFilterTest;
import ms.utils.client.directorypoller.DirectoryObserverTest;
import ms.utils.networking.client.ServerConnectionTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { DirectoryObserverTest.class, ImageWhiteFilterTest.class,
		ServerConnectionTest.class })
public class ClientTest {

}
