package ms.utils.client.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.imageio.ImageIO;

import ms.utils.client.filter.ImageWhiteFilter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ImageWhiteFilterTest {

	
	private static final String TEMPDIR = System.getProperty("java.io.tmpdir") + File.separator;
	
	private int imageHeight = 111;
	private int imageWidth = imageHeight;
	private File file;
	
	@Before
	public void setUp() {
		file = new File(TEMPDIR + "testImage");
	}
	
	@After
	public void tearDown() {
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void testImageFileWhite() {
		BufferedImage image = createImage(new Color(255, 255, 255));
		try {
			ImageIO.write(image, "jpg", file);
			image = ImageIO.read(file);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		boolean isNotWhite = ImageWhiteFilter.analyzeImage(image);
		assertTrue(isNotWhite);
	}
	
	@Test
	public void testImageNull() {
		BufferedImage image = null;
		boolean isNotWhite = ImageWhiteFilter.analyzeImage(image);
		assertFalse(isNotWhite);
	}
	
	@Test
	public void testImageWhite() {
		BufferedImage image = createImage(new Color(255, 255, 255));
		boolean isNotWhite = ImageWhiteFilter.analyzeImage(image);
		assertTrue(isNotWhite);
	}
	
	@Test
	public void testImageLargeWhite() {
		imageHeight = 1111;
		testImageWhite();
	}
	
	@Test
	public void testImageWhiteBoundry() {
		BufferedImage image = createImage(new Color(242, 242, 242));
		boolean isNotWhite = ImageWhiteFilter.analyzeImage(image);
		assertTrue(isNotWhite);
	}
	
	@Test
	public void testImageLargeWhiteBoundry() {
		imageHeight = 1111;
		testImageWhiteBoundry();
	}
	
	@Test
	public void testImageNotWhite() {
		BufferedImage image = createImage(new Color(0, 0, 0));
		boolean isNotWhite = ImageWhiteFilter.analyzeImage(image);
		assertFalse(isNotWhite);
	}
	
	@Test
	public void testImageLargeNotWhite() {
		imageHeight = 1111;
		testImageNotWhite();
	}
	
	@Test
	public void testImageNotWhiteBoundry() {
		BufferedImage image = createImage(new Color(241, 241, 241));
		boolean isNotWhite = ImageWhiteFilter.analyzeImage(image);
		assertFalse(isNotWhite);
	}
	
	@Test
	public void testImageLargeNotWhiteBoundry() {
		imageHeight = 1111;
		testImageNotWhiteBoundry();
	}
	
	@Test
	public void testImage95PercentWhite() {
		int imageResolution = imageHeight*imageWidth;
		int split = round((double)imageResolution/100*5);
		BufferedImage image = createSplitImage(split);
		boolean isNotWhite = ImageWhiteFilter.analyzeImage(image);
		assertTrue(isNotWhite);
	}
	
	@Test
	public void testImageLarge95PercentWhite() {
		imageHeight = 1111;
		testImage95PercentWhite();
	}
	
	@Test
	public void testImage94PercentWhite() {
		int imageResolution = imageHeight*imageWidth;
		int split = round((double)imageResolution/100*6);
		BufferedImage image = createSplitImage(split);
		boolean isNotWhite = ImageWhiteFilter.analyzeImage(image);
		assertFalse(isNotWhite);
	}
	
	@Test
	public void testImageLarge94PercentWhite() {
		imageHeight = 1111;
		testImage94PercentWhite();
	}
	
	private int round(double d) {
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(0, RoundingMode.HALF_UP);
		return bd.intValue();
	}
	
	private BufferedImage createImage(Color c) {
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				image.setRGB(i, j, c.getRGB());
			}
		}
		return image;
	}
	
	private BufferedImage createSplitImage(int split) {
		int paintedPixels = 0;
		BufferedImage image = createImage(new Color(255, 255, 255));
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				if(split <= paintedPixels) {
					break;
				}
				image.setRGB(i, j, new Color(0, 0, 0).getRGB());
				paintedPixels++;
			}
		}
		return image;
	}
}
