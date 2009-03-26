package ch.nomoresecrets.mediastopf.client.utils;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * rotate a 2D image with an angle in deegree around the imagecenter.
 * 
 * @author DT
 */
public class RotateImage {
	
	private static double ANGLE = 0;
	
	/**
	 * 2D image rotation with angle in deegree around the imagecenter.
	 * y-axis = 0 (Grad)
	 * clockwise = positiv rotation
	 * 
	 * @param image BufferedImage
	 * @param angle Rotation in deegree
	 * 
	 * @return BufferedImage
	 */
	public static BufferedImage rotate(BufferedImage image, double angle) {
		ANGLE = -angle;
		while (360 < ANGLE) {
			ANGLE -= 360;
		}
		while (ANGLE < 0) {
			ANGLE += 360;
		}
		AffineTransform at = new AffineTransform();
		at.rotate(Math.toRadians(ANGLE), image.getWidth() / 2, image.getHeight() / 2);
		at.preConcatenate(translate(at, image));
		AffineTransformOp ato = new AffineTransformOp(at, null);
		return ato.filter(image, null);
	}
	
	/**
	 * translate image to a position where the whole image is drawn.
	 * 
	 * @param at AffineTransform
	 * @param bi BufferedImage
	 * @param angle Rotation in deegree
	 * 
	 * @return att Translate AffineTransform
	 */
	private static AffineTransform translate(AffineTransform at, BufferedImage bi) {
		double ytrans = 0;
		double xtrans = 0;
		if (isAngleBetween(0, 90)) {
			ytrans = transformPointY(0.0, 0.0, at);
			xtrans = transformPointX(0.0, bi.getHeight(), at);
		} else if (isAngleBetween(90, 180)) {
			ytrans = transformPointY(0.0, bi.getHeight(), at);
			xtrans = transformPointX(bi.getWidth(), bi.getHeight(), at);
		} else if (isAngleBetween(180, 270)) {
			ytrans = transformPointY(bi.getWidth(), bi.getHeight(), at);
			xtrans = transformPointX(bi.getWidth(), 0.0, at);
		} else if (isAngleBetween(270, 360)) {
			ytrans = transformPointY(bi.getWidth(), 0.0, at);
			xtrans = transformPointX(0.0, 0.0, at);
		}
		AffineTransform att = new AffineTransform();
		att.translate(-xtrans, -ytrans);
		return att;
	}
	
	private static boolean isAngleBetween(int lower, int upper) {
		return lower <= ANGLE && ANGLE < upper;
	}
	
	private static double transformPointY(double x, double y, AffineTransform at) {
		return at.transform(new Point2D.Double(x, y), null).getY();
	}
	
	private static double transformPointX(double x, double y, AffineTransform at) {
		return at.transform(new Point2D.Double(x, y), null).getX();
	}
}
