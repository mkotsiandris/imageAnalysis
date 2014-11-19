package image.helpers;

public class AnalysisHelper {

	final double PI = 3.14;

	private double circularity = 0.0;
	private double length = 0.0;
	private double width = 0.0;
	private double feretDiameter = 0.0;
	private double volume = 0.0;

	public double findApproximateVolume(double circularity)
	{
		if (circularity > 0){
			volume = this.getEqualSphericalVolume(this.feretDiameter, this.length, this.width);
		} else {
			volume = this.getEqualCylinderVolume(this.feretDiameter, this.length, this.width);
		}
		return volume;
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
