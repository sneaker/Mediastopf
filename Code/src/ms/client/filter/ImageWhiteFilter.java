package ms.client.filter;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.imageio.ImageIO;

/**
 * analyze image pixel by pixel if it's white
 * using two threads for better performance.
 * - white tolerance of 5% (default)
 * 	=> rgb 242-255 are all "white"
 * - image white tolerance of 95% (default)
 * 	=> if more than 95% of the pixels are white
 * 		=> image is white
 * 
 * @author david
 *
 */
public class ImageWhiteFilter {
	
	private static final int COLOR_TOLERANCE_IN_PERCENT = 5;
	private static final int IMAGECOLOR_TOLERANCE_IN_PERCENT = 95;
	private static final int WHITE_COLOR = 255;
	private static final int WHITE_TOLERANCE = round(WHITE_COLOR-((double)255/100*COLOR_TOLERANCE_IN_PERCENT));
	
	/**
	 * analyze an image file if it's white
	 * 
	 * @param file imagefile
	 * @return boolean, true if it is white
	 */
	public static boolean analyzeImageFile(File file) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return analyzeImage(image);
	}
	
	/**
	 * analyze a bufferedimage if it's white
	 * 
	 * @param image BufferedImage
	 * @return boolean true, if it's white
	 */
	public static boolean analyzeImage(BufferedImage image) {
		int imageResolution = image.getWidth() * image.getHeight();
		if(imageResolution<1000*1000) {
			return analyzeSmallImage(image);
		}
		return analyzeLargeImage(image);
	}
	
	private static boolean analyzeSmallImage(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int halfWidth = width/2;
		Rectangle left = new Rectangle(0, 0, halfWidth, height);
		Rectangle right = new Rectangle(halfWidth, 0, width, height);
		int whitePixels = analyzeWithTwoThreads(left, right, image);
		return isImageWhite(image, whitePixels);
	}
	
	private static boolean analyzeLargeImage(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int halfWidth = width/2;
		int halfHeight = height/2;
		Rectangle upperleft = new Rectangle(0, 0, halfWidth, halfHeight);
		Rectangle upperright = new Rectangle(halfWidth, 0, width, halfHeight);
		Rectangle lowerleft = new Rectangle(0, halfHeight, halfWidth, height);
		Rectangle lowerright = new Rectangle(halfWidth, halfHeight, width, height);
		
		int whitePixels = analyzeWithTwoThreads(upperleft, upperright, image);
		boolean isWhite = isImageWhite(image, whitePixels);
		if(isWhite) {
			return isWhite;
		}
		whitePixels += analyzeWithTwoThreads(lowerleft, lowerright, image);
		return isImageWhite(image, whitePixels);
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
	
	private static boolean isImageWhite(BufferedImage bi, int whitePixelCounter) {
		int imageResolution = bi.getWidth()*bi.getHeight();
		int whitePixelInPercent = round((double)whitePixelCounter/imageResolution*100);
		return(IMAGECOLOR_TOLERANCE_IN_PERCENT <= whitePixelInPercent);
	}
	
	private static int round(double d) {
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(0, RoundingMode.HALF_UP);
		return bd.intValue();
	}
	
	private static boolean isColorWhite(Color c) {
		return (isColorInTolerance(c.getRed()) && isColorInTolerance(c.getGreen()) && isColorInTolerance(c.getBlue()));
	}
	
	private static boolean isColorInTolerance(int color) {
		return (WHITE_TOLERANCE <= color && color <= WHITE_COLOR);
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
					if(isColorWhite(c)) {
						count++;
					}
				}
			}
		}
	}
}
