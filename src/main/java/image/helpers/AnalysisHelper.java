package image.helpers;

/**
 * Created by cerebro on 10/19/14.
 */
public class AnalysisHelper {
	final double PI = 3.14;

	double circularity, length, width, feretDiameter;

	public AnalysisHelper(double circularity, double length, double width, double feretDiameter){
		this.circularity = circularity;
		this.length = length;
		this.width = width;
		this.feretDiameter = feretDiameter;
	}

	public  double findApproximateVolume(double circularity){
		if (circularity > 0){
			return this.getEqualSphericalVolume(this.feretDiameter, this.length, this.width);
		} else {
			return this.getEqualCylinderVolume(this.feretDiameter, this.length, this.width);
		}
	}

	private double getEqualCylinderVolume(double feretDiameter, double length, double width) {
		double radius = feretDiameter/2;
		double volume = PI*radius*radius*length;
		return volume;
	}

	private double getEqualSphericalVolume(double feretDiameter, double length, double width) {
		double radius = feretDiameter/2;
		double volume = PI*4*radius*radius*radius/3;
		return volume;
	}
}
