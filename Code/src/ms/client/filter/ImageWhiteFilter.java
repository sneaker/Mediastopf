package ms.client.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
	
	private static final int WHITE_COLOR_TOLERANCE_IN_PERCENT = 10;
	private static final int WHITE_IMAGE_TOLERANCE_IN_PERCENT = 95;
	private static final int WHITE_COLOR = 255;
	private static final int WHITE_TOLERANCE = WHITE_COLOR-(WHITE_COLOR/WHITE_COLOR_TOLERANCE_IN_PERCENT);
	
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
		int whitePixelCounter = 0;
		for (int i = 0; i < bi.getWidth();i ++) {
			for (int j = 0; j < bi.getHeight(); j++) {
				Color c = new Color(bi.getRGB(i, j));
				if(isColorWhite(c)) {
					whitePixelCounter++;
				}
			}
		}
		return isImageWhite(bi, whitePixelCounter);
	}

	private static boolean isImageWhite(BufferedImage bi, int whitePixelCounter) {
		int imageResolution = bi.getWidth()*bi.getHeight();
		int whitePixelInPercent = (int)((double)whitePixelCounter/imageResolution*100);
		return(WHITE_IMAGE_TOLERANCE_IN_PERCENT <= whitePixelInPercent);
	}
	
	private static boolean isColorWhite(Color c) {
		return (isColorInTolerance(c.getRed()) && isColorInTolerance(c.getGreen()) && isColorInTolerance(c.getBlue()));
	}
	
	private static boolean isColorInTolerance(int color) {
		return (WHITE_TOLERANCE <= color && color <= WHITE_COLOR);
	}
}
