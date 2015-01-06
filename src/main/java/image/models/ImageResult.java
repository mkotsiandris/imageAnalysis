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
	public String volume = "";
	public String sphericity = "";
	public String surfaceDiameter = "";
	public String volumeDiameter = "";
	public String volumeToSurface = "";

	public ImageResult(HashMap<String, String> map) {
		this.angle = replaceNull(map.get("Angle"));
		this.area = replaceNull(map.get("Area"));
		this.std_dev = replaceNull(map.get("StdDev"));
		this.circularity = replaceNull(map.get("Circ."));
		this.area_fraction = replaceNull(map.get("%Area"));
		this.solidity = replaceNull(map.get("Solidity"));
		this.x_center_of_mass = replaceNull(map.get("XM"));
		this.y_center_of_mass = replaceNull(map.get("YM"));
		this.min_grey_value = replaceNull(map.get("Min"));
		this.max_grey_value = replaceNull(map.get("Max"));
		this.bounding_rectangle_origin_x = replaceNull(map.get("BX"));
		this.bounding_rectangle_origin_y = replaceNull(map.get("BY"));
		this.bounding_rectangle_width = replaceNull(map.get("Width"));
		this.bounding_rectangle_height = replaceNull(map.get("Height"));
		this.aspect_ratio = replaceNull(map.get("AR"));
		this.circularity = replaceNull(map.get("Circ."));
		this.roundness = replaceNull(map.get("Round"));
		this.solidity = replaceNull(map.get("Solidity"));
		this.integrated_density = replaceNull(map.get("IntDen"));
		this.raw_integrated_density = replaceNull(map.get("RawIntDen"));
		this.skewness = replaceNull(map.get("Skew"));
		this.kurtosis = replaceNull(map.get("Kurt"));
		this.feret = replaceNull(map.get("Feret"));
		this.feret_angle = replaceNull(map.get("FeretAngle"));
		this.feret_x = replaceNull(map.get("FeretX"));
		this.feret_y = replaceNull(map.get("FeretY"));
		this.min_feret = replaceNull(map.get("MinFeret"));
		this.x_centroid = replaceNull(map.get("X"));
		this.y_centroid = replaceNull(map.get("Y"));
		this.major = replaceNull(map.get("Major"));
		this.minor = replaceNull(map.get("Minor"));
		this.perimeter = replaceNull(map.get("Perim."));
		this.mean_grey_value = replaceNull(map.get("Mean"));
		this.median = replaceNull(map.get("median"));
		this.calculateExtraParameters();
	}

	public void calculateExtraParameters(){
		AnalysisHelper analysisHelper = new AnalysisHelper();
		if (!this.volume.isEmpty() && !this.area.isEmpty()) {
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
	}

	public String replaceNull(String input) {
		return input == null ? "" : input;
	}
}
