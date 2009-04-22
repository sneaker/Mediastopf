package ms;

import junit.framework.TestCase;

import ms.client.ClientTest;
import ms.server.ServerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { ClientTest.class, ServerTest.class })
public class MediaStopfTest extends TestCase {

}
