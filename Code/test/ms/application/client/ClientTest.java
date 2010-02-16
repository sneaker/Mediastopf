package ms.application.client;

import ms.utils.ImageWhiteFilterTest;
import ms.utils.client.directorypoller.DirectoryObserverTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses( { 
		DirectoryObserverTest.class, 
		ImageWhiteFilterTest.class
		})
		
public class ClientTest {

}
