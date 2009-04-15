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
 * analyze image pixel by pixel if it's white:
 * - white tolerance of 10% (default)
 * 	=> rgb 230-255 are all "white"
 * - image white tolerance of 95% (default)
 * 	=> if more than 95% of the pixels are white
 * 		=> image is white
 * 
 * @author david
 *
 */
public class ImageWhiteFilter {
	
	private static final int COLOR_TOLERANCE_IN_PERCENT = 10;
	private static final int IMAGECOLOR_TOLERANCE_IN_PERCENT = 95;
	private static final int WHITE_COLOR = 255;
	private static final int WHITE_TOLERANCE = WHITE_COLOR-(WHITE_COLOR/COLOR_TOLERANCE_IN_PERCENT);
	
	/**
	 * analyze an image file if it's white
	 * 
	 * @param file imagefile
	 * @return boolean, true if it is white
	 */
	public static boolean analyzeImageFile(File file) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return analyzeImage(bi);
	}
	
	/**
	 * analyze a bufferedimage if it's white
	 * 
	 * @param bi BufferedImage
	 * @return boolean true, if it's white
	 */
	public static boolean analyzeImage(BufferedImage bi) {
		int halfX = bi.getWidth()/2;
		int halfY = bi.getHeight()/2;
		int width = bi.getWidth();
		int height = bi.getHeight();
		if(bi.getWidth() % 2 == 1) {
			halfX += 1;
		}
		if(bi.getHeight() % 2 == 1) {
			halfY += 1;
		}
		Rectangle upperleft = new Rectangle(0, 0, bi.getWidth()/2, bi.getHeight()/2);
		Rectangle upperright = new Rectangle(halfX, 0, width, bi.getHeight()/2);
		Rectangle lowerleft = new Rectangle(0, halfY, bi.getWidth()/2, height);
		Rectangle lowerright = new Rectangle(halfX, halfY, width, height);
		
		ImageWhiteFilter filter = new ImageWhiteFilter();
		WhitePixelCounter first = filter.new WhitePixelCounter(upperleft, bi);
		WhitePixelCounter sec = filter.new WhitePixelCounter(upperright, bi);
		WhitePixelCounter third = filter.new WhitePixelCounter(lowerleft, bi);
		WhitePixelCounter forth = filter.new WhitePixelCounter(lowerright, bi);
		first.start();
		sec.start();
		third.start();
		forth.start();
		try {
			first.join();
			sec.join();
			third.join();
			forth.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int whitePixels = first.getCount() + sec.getCount() + third.getCount() + forth.getCount();
		return isImageWhite(bi, whitePixels);
	}
	
	private static boolean isImageWhite(BufferedImage bi, int whitePixelCounter) {
		int imageResolution = bi.getWidth()*bi.getHeight();
		BigDecimal bd = new BigDecimal((double)whitePixelCounter/imageResolution*100);
		bd = bd.setScale(0, RoundingMode.HALF_UP);
		int whitePixelInPercent = bd.intValue();
		// TODO
		System.out.println(whitePixelInPercent);
		return(IMAGECOLOR_TOLERANCE_IN_PERCENT <= whitePixelInPercent);
	}
	
	private static boolean isColorWhite(Color c) {
		return (isColorInTolerance(c.getRed()) && isColorInTolerance(c.getGreen()) && isColorInTolerance(c.getBlue()));
	}
	
	private static boolean isColorInTolerance(int color) {
		return (WHITE_TOLERANCE <= color && color <= WHITE_COLOR);
	}
	
	private class WhitePixelCounter extends Thread {
		private int count = 0;
		private BufferedImage bi;
		private Rectangle rect;
		
		public WhitePixelCounter(Rectangle rect, BufferedImage bi) {
			this.rect = rect;
			this.bi = bi;
		}
		
		public int getCount() {
			return count;
		}
		
		@Override
		public void run() {
			for (int i = rect.x; i < rect.width;i ++) {
				for (int j = rect.y; j < rect.height; j++) {
					Color c = new Color(bi.getRGB(i, j));
					if(isColorWhite(c)) {
						count++;
					}
				}
			}
		}
	}
}
