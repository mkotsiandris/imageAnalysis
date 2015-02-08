package image.models;


import ij.measure.Measurements;
import sun.security.provider.SHA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Measurement {

	public final String AREA = "Area";
	public final String AREA_FRACTION = "Porocity";
	public final String MIN_MAX = "Minimum and Maximum";
	public final String CIRCULARITY = "Circularity";
	public final String STD_DEV = "Standard Deviation";
	public final String CENTER_OF_MASS = "Center of mass";
	public final String SHAPE_DESCRIPTORS = "Shape Descriptors";
	public final String FERET = "Feret";
	public final String SKEWNESS = "Skeweness";
	public final String KURTOSIS = "Kurtosis";
	public final String VOLUME = "Volume";

	private List<String> measurementList;
	public HashMap<String, Integer> measurementMap;
	public Boolean isVolumeSelected = false;
	public Boolean isSphericitySelected = false;
	public Boolean isVolumeToSurfaceSelected = false;

	public Measurement(){
		this.populateMeasurementList();
		this.matchingMeasurementsWithName();
	}

	private void populateMeasurementList(){
		this.measurementList = new ArrayList<>();
		this.measurementList.add(AREA);
		this.measurementList.add(AREA_FRACTION);
		this.measurementList.add(CIRCULARITY);
		this.measurementList.add(SKEWNESS);
		this.measurementList.add(STD_DEV);
		this.measurementList.add(FERET);
		this.measurementList.add(KURTOSIS);
		this.measurementList.add(SHAPE_DESCRIPTORS);
		this.measurementList.add(MIN_MAX);
		this.measurementList.add(CENTER_OF_MASS);
		this.measurementList.add(VOLUME);

	}

	private void matchingMeasurementsWithName(){
		this.measurementMap = new HashMap<>();
		this.measurementMap.put(AREA, Measurements.AREA);
		this.measurementMap.put(AREA_FRACTION, Measurements.AREA_FRACTION);
		this.measurementMap.put(MIN_MAX, Measurements.MIN_MAX);
		this.measurementMap.put(CIRCULARITY, Measurements.CIRCULARITY);
		this.measurementMap.put(STD_DEV, Measurements.STD_DEV);
		this.measurementMap.put(CENTER_OF_MASS, Measurements.CENTER_OF_MASS);
		this.measurementMap.put(FERET, Measurements.FERET);
		this.measurementMap.put(SHAPE_DESCRIPTORS, Measurements.SHAPE_DESCRIPTORS);
		this.measurementMap.put(SKEWNESS, Measurements.SKEWNESS);
		this.measurementMap.put(KURTOSIS, Measurements.KURTOSIS);
	}


	public int convertMeasurementListToInt(String[] selectedMeasurements){
		int result = 0;
		for (int i = 0; i < selectedMeasurements.length; i++) {
			if (selectedMeasurements[i].equals("Volume") || selectedMeasurements.equals("Sphericity")){
				break;
			} else {
				Integer value = measurementMap.get(selectedMeasurements[i]);
				result += value.intValue();
			}
		}
		return result;
	}

	public List<String> getMeasurementList() {
		return measurementList;
	}
}
