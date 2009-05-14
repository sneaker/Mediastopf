package ms.utils.client.filter;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.imageio.ImageIO;

/**
 * analyze image pixel by pixel if it's white.
 * using two threads for better performance.
 * - NOT white tolerance of 95% (default) => rgb 242-255 are all "white"
 * - image NOT white tolerance of 5% (default)
 * => if more than 5% of the pixels are not white => image is NOT white
 * 
 * @author david
 * 
 */
public class ImageWhiteFilter {

	private static final int COLOR_TOLERANCE_IN_PERCENT = 95;
	private static final int IMAGECOLOR_TOLERANCE_IN_PERCENT = 5;
	private static final int NOT_WHITE_COLOR = 0;
	private static final int NOT_WHITE_TOLERANCE = round(NOT_WHITE_COLOR + ((double) 255 / 100 * COLOR_TOLERANCE_IN_PERCENT));

	/**
	 * analyze an image file if it's white
	 * 
	 * @param file
	 *            imagefile
	 * @return boolean, true if it is white
	 */
	public static boolean analyzeImageFile(File file) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return analyzeImage(image);
	}

	/**
	 * analyze a bufferedimage if it's white
	 * 
	 * @param image
	 *            BufferedImage
	 * @return boolean true, if it's white
	 */
	public static boolean analyzeImage(BufferedImage image) {
		if (image == null) {
			return false;
		}
		 int imageResolution = image.getWidth() * image.getHeight();
		 if(imageResolution<500*500) {
			 return analyzeSmallImage(image);
		 }
		 return analyzeLargeImage(image);
	}

	private static boolean analyzeSmallImage(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int halfWidth = width / 2;
		Rectangle left = new Rectangle(0, 0, halfWidth, height);
		Rectangle right = new Rectangle(halfWidth, 0, width, height);
		int notWhitePixels = analyzeWithTwoThreads(left, right, image);
		return isNotImageWhite(image, notWhitePixels);
	}

	private static boolean analyzeLargeImage(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int halfWidth = width / 2;
		int halfHeight = height / 2;
		Rectangle upperleft = new Rectangle(0, 0, halfWidth, halfHeight);
		Rectangle upperright = new Rectangle(halfWidth, 0, width, halfHeight);
		Rectangle lowerleft = new Rectangle(0, halfHeight, halfWidth, height);
		Rectangle lowerright = new Rectangle(halfWidth, halfHeight, width, height);
		int notWhitePixels = analyzeWithTwoThreads(upperleft, upperright, image);
		boolean isNotWhite = isNotImageWhite(image, notWhitePixels);
		if (isNotWhite) {
			return isNotWhite;
		}
		notWhitePixels += analyzeWithTwoThreads(lowerleft, lowerright, image);
		return isNotImageWhite(image, notWhitePixels);
	}

	private static int analyzeWithTwoThreads(Rectangle rect1, Rectangle rect2, BufferedImage image) {
		ImageWhiteFilter filter = new ImageWhiteFilter();
		ImagePiece part1 = filter.new ImagePiece(rect1, image);
		ImagePiece part2 = filter.new ImagePiece(rect2, image);
		part1.start();
		part2.start();
		try {
			part1.join();
			part2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return part1.getCount() + part2.getCount();
	}

	private static boolean isNotImageWhite(BufferedImage bi, int notWhitePixelCounter) {
		int imageResolution = bi.getWidth() * bi.getHeight();
		int notWhitePixelInPercent = round((double) notWhitePixelCounter/ imageResolution * 100);
		return (notWhitePixelInPercent <= IMAGECOLOR_TOLERANCE_IN_PERCENT);
	}

	private static int round(double d) {
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(0, RoundingMode.HALF_UP);
		return bd.intValue();
	}

	private static boolean isColorNotWhite(Color c) {
		return (isColorInTolerance(c.getRed()) && isColorInTolerance(c.getGreen()) && isColorInTolerance(c.getBlue()));
	}

	private static boolean isColorInTolerance(int color) {
		return (NOT_WHITE_COLOR <= color && color < NOT_WHITE_TOLERANCE);
	}

	private class ImagePiece extends Thread {
		private int count = 0;
		private Rectangle rect;
		private BufferedImage image;

		public ImagePiece(Rectangle rect, BufferedImage image) {
			this.rect = rect;
			this.image = image;
		}

		public int getCount() {
			return count;
		}

		@Override
		public void run() {
			for (int i = rect.x; i < rect.width; i++) {
				for (int j = rect.y; j < rect.height; j++) {
					Color c = new Color(image.getRGB(i, j));
					if (isColorNotWhite(c)) {
						count++;
					}
				}
			}
		}
	}
}
