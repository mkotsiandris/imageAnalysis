package image.models;

import image.helpers.AnalysisHelper;

import java.util.HashMap;

public class ImageResult {

	public String angle;
	public String area;
	public String area_fraction;
	public String aspect_ratio;
	public String circularity;
	public String feret;
	public String feret_angle;
	public String feret_x;
	public String feret_y;
	public String integrated_density;
	public String kurtosis;
	public String major;
	public String max_grey_value;
	public String mean_grey_value;
	public String median;
	public String min_grey_value;
	public String min_feret;
	public String minor;
	public String perimeter;
	public String raw_integrated_density;
	public String roundness;
	public String skewness;
	public String solidity;
	public String std_dev;
	public String bounding_rectangle_origin_x;
	public String bounding_rectangle_origin_y;
	public String bounding_rectangle_width;
	public String bounding_rectangle_height;
	public String x_center_of_mass;
	public String x_centroid;
	public String y_center_of_mass;
	public String y_centroid;
	public String volume;
	public String sphericity;
	public String surfaceDiameter;
	public String volumeDiameter;
	public String volumeToSurface;

	public ImageResult(HashMap<String, String> map) {
		this.angle = map.get("Angle");
		this.area = map.get("Area");
		this.std_dev = map.get("StdDev");
		this.circularity = map.get("Circ.");
		this.area_fraction = map.get("%Area");
		this.solidity = map.get("Solidity");
		this.x_center_of_mass = map.get("XM");
		this.y_center_of_mass = map.get("YM");
		this.min_grey_value = map.get("Min");
		this.max_grey_value = map.get("Max");
		this.bounding_rectangle_origin_x = map.get("BX");
		this.bounding_rectangle_origin_y = map.get("BY");
		this.bounding_rectangle_width = map.get("Width");
		this.bounding_rectangle_height = map.get("Height");
		this.aspect_ratio = map.get("AR");
		this.circularity = map.get("Circ.");
		this.roundness = map.get("Round");
		this.solidity = map.get("Solidity");
		this.integrated_density = map.get("IntDen");
		this.raw_integrated_density = map.get("RawIntDen");
		this.skewness = map.get("Skew");
		this.kurtosis = map.get("Kurt");
		this.feret = map.get("Feret");
		this.feret_angle = map.get("FeretAngle");
		this.feret_x = map.get("FeretX");
		this.feret_y = map.get("FeretY");
		this.min_feret = map.get("MinFeret");
		this.x_centroid = map.get("X");
		this.y_centroid = map.get("Y");
		this.major = map.get("Major");
		this.minor = map.get("Minor");
		this.perimeter = map.get("Perim.");
		this.mean_grey_value = map.get("Mean");
		this.median = map.get("median");
		this.calculateExtraParameters();
	}

	public void calculateExtraParameters(){
		AnalysisHelper analysisHelper = new AnalysisHelper();
		double volume = analysisHelper.findApproximateVolume(Double.parseDouble(this.circularity));
		double surfaceDiameter = analysisHelper.getSurfaceDiameter(Double.parseDouble(this.area));
		double sphericity = analysisHelper.getSphericity(volume, Double.parseDouble(this.area));
		double volumeDiameter = analysisHelper.getVolumeDiameter(volume);
		double volumeToSurface = analysisHelper.getVolumeToSurface(volume, Double.parseDouble(this.area));
		this.volume = Double.toString(volume);
		this.surfaceDiameter =  Double.toString(surfaceDiameter);
		this.sphericity = Double.toString(sphericity);
		this.volumeDiameter = Double.toString(volumeDiameter);
		this.volumeToSurface = Double.toString(volumeToSurface);
	}

	public String replaceNullWithEmptyString(String obj) {
		if (obj == null) {
			obj = "";
		}
		return obj;
	}
}
