package image.helpers;

/**
 * Created by cerebro on 10/19/14.
 */
public class AnalysisHelper {

	final double PI = 3.14;

	double circularity = 0.0;
	double length = 0.0;
	double width = 0.0;
	double feretDiameter = 0.0;
	double volume = 0.0;

	public AnalysisHelper(double circularity, double length, double width, double feretDiameter)
	{
		this.circularity = circularity;
		this.length = length;
		this.width = width;
		this.feretDiameter = feretDiameter;
	}

	public  double findApproximateVolume(double circularity)
	{
		if (circularity > 0){
			return this.getEqualSphericalVolume(this.feretDiameter, this.length, this.width);
		} else {
			return this.getEqualCylinderVolume(this.feretDiameter, this.length, this.width);
		}
	}

	private double getEqualCylinderVolume(double feretDiameter, double length, double width)
	{
		double radius = feretDiameter/2;
		double volume = PI*radius*radius*length;
		return volume;
	}

	private double getEqualSphericalVolume(double feretDiameter, double length, double width)
	{
		double radius = feretDiameter/2;
		double volume = PI*4*radius*radius*radius/3;
		return volume;
	}

	public double getSurfaceDiameter(double area)
	{
		return Math.sqrt(area/PI);
	}

	public double getVolumeDiameter(double volume)
	{
		return Math.cbrt(6*volume/PI);
	}

	public double getVolumeToSurface(double volume, double area)
	{
		return 6*volume / area;
	}
}
