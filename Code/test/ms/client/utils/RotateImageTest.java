package ms.client.utils;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;

import ms.client.utils.RotateImage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * RotateImage Test Class
 * 
 * @author DT
 */
public class RotateImageTest {
	
	private BufferedImage image, rotImage;

	@Before
	public void setUp() throws Exception { 
	    int width = 100;
	    int height = 200;
	    int[] data = new int[width * height];
	    int i = 0;
	    for (int y = 0; y < height; y++) {
	      int red = (y * 255) / (height - 1);
	      for (int x = 0; x < width; x++) {
	        int green = (x * 255) / (width - 1);
	        int blue = 128;
	        data[i++] = (red << 16) | (green << 8) | blue;
	      }
	    }
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.setRGB(0, 0, width, height, data, 0, width);
	}

	@After
	public void tearDown() throws Exception {
		image.flush();
	}

	@Test
	public void testRotate0Degree() {
		rotImage = RotateImage.rotate(image, 0);
		assertEquals("Width", rotImage.getWidth(), 100);
		assertEquals("Height", rotImage.getHeight(), 200);
	}

	@Test
	public void testRotate360Degree() {
		rotImage = RotateImage.rotate(image, 360);
		assertEquals("Width", rotImage.getWidth(), 100);
		assertEquals("Height", rotImage.getHeight(), 200);
	}

	@Test
	public void testRotate359Degree() {
		rotImage = RotateImage.rotate(image, 359);
		assertEquals("Width", rotImage.getWidth(), 104);
		assertEquals("Height", rotImage.getHeight(), 202);
	}

	@Test
	public void testRotate315Degree() {
		rotImage = RotateImage.rotate(image, 315);
		assertEquals("Width", rotImage.getWidth(), 213);
		assertEquals("Height", rotImage.getHeight(), 213);
	}

	@Test
	public void testRotate270Degree() {
		rotImage = RotateImage.rotate(image, 270);
		assertEquals("Width", rotImage.getWidth(), 200);
		assertEquals("Height", rotImage.getHeight(), 100);
	}

	@Test
	public void testRotate269Degree() {
		rotImage = RotateImage.rotate(image, 269);
		assertEquals("Width", rotImage.getWidth(), 202);
		assertEquals("Height", rotImage.getHeight(), 104);
	}

	@Test
	public void testRotate225Degree() {
		rotImage = RotateImage.rotate(image, 225);
		assertEquals("Width", rotImage.getWidth(), 213);
		assertEquals("Height", rotImage.getHeight(), 213);
	}

	@Test
	public void testRotate180Degree() {
		rotImage = RotateImage.rotate(image, 180);
		assertEquals("Width", rotImage.getWidth(), 100);
		assertEquals("Height", rotImage.getHeight(), 200);
	}

	@Test
	public void testRotate179Degree() {
		rotImage = RotateImage.rotate(image, 179);
		assertEquals("Width", rotImage.getWidth(), 104);
		assertEquals("Height", rotImage.getHeight(), 202);
	}

	@Test
	public void testRotate135Degree() {
		rotImage = RotateImage.rotate(image, 135);
		assertEquals("Width", rotImage.getWidth(), 213);
		assertEquals("Height", rotImage.getHeight(), 213);
	}

	@Test
	public void testRotate90Degree() {
		rotImage = RotateImage.rotate(image, 90);
		assertEquals("Width", rotImage.getWidth(), 200);
		assertEquals("Height", rotImage.getHeight(), 100);
	}

	@Test
	public void testRotate89Degree() {
		rotImage = RotateImage.rotate(image, 89);
		assertEquals("Width", rotImage.getWidth(), 202);
		assertEquals("Height", rotImage.getHeight(), 104);
	}

	@Test
	public void testRotate45Degree() {
		rotImage = RotateImage.rotate(image, 45);
		assertEquals("Width", rotImage.getWidth(), 213);
		assertEquals("Height", rotImage.getHeight(), 213);
	}
}
