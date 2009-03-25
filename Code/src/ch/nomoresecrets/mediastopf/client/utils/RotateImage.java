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
	
	/**
	 * 2D image rotation with angle in deegree around the imagecenter.
	 * y-axis = 0°
	 * clockwise = positiv rotation
	 * 
	 * @param image BufferedImage
	 * @param angle Rotation in deegree
	 * 
	 * @return BufferedImage
	 */
	public static BufferedImage rotate(BufferedImage image, double angle) {
		angle = -angle;
		while (360 < angle) {
			angle -= 360;
		}
		while (angle < 0) {
			angle += 360;
		}
		AffineTransform at = new AffineTransform();
		at.rotate(Math.toRadians(angle), image.getWidth() / 2, image.getHeight() / 2);
		at.preConcatenate(translate(at, image, angle));
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
	private static AffineTransform translate(AffineTransform at, BufferedImage bi, double angle) {
		double ytrans = 0;
		double xtrans = 0;
		if (0 <= angle && angle < 90) {
			ytrans = transform_point(0.0, 0.0, at).getY();
			xtrans = transform_point(0.0, bi.getHeight(), at).getX();
		} else if (90 <= angle && angle < 180) {
			ytrans = transform_point(0.0, bi.getHeight(), at).getY();
			xtrans = transform_point(bi.getWidth(), bi.getHeight(), at).getX();
		} else if (180 <= angle && angle < 270) {
			ytrans = transform_point(bi.getWidth(), bi.getHeight(), at).getY();
			xtrans = transform_point(bi.getWidth(), 0.0, at).getX();
		} else if (270 <= angle && angle < 360) {
			ytrans = transform_point(bi.getWidth(), 0.0, at).getY();
			xtrans = transform_point(0.0, 0.0, at).getX();
		}
		AffineTransform att = new AffineTransform();
		att.translate(-xtrans, -ytrans);
		return att;
	}
	
	private static Point2D transform_point(double x, double y, AffineTransform at) {
		Point2D p2d = new Point2D.Double(x, y);
		return at.transform(p2d, null);
	}
}
