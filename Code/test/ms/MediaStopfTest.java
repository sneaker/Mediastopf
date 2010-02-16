package ms;

import junit.framework.TestCase;

import ms.application.client.ClientTest;
import ms.application.server.ServerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { ClientTest.class, ServerTest.class, ImportMediumTest.class })

public class MediaStopfTest extends TestCase {

}
