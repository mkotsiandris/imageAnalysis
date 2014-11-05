package image.models;


import ij.measure.Measurements;

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

	private List<String> measurementList;
	public HashMap<String, Integer> measurementMap;

	public Measurement(){
		this.populateMeasurementList();
		this.matchingMeasurementsWithName();
	}

	private void populateMeasurementList(){
		this.measurementList = new ArrayList<String>();
		this.measurementList.add(AREA);
		this.measurementList.add(AREA_FRACTION);
		this.measurementList.add(MIN_MAX);
		this.measurementList.add(CIRCULARITY);
		this.measurementList.add(STD_DEV);
		this.measurementList.add(CENTER_OF_MASS);
	}

	private void matchingMeasurementsWithName(){
		this.measurementMap = new HashMap<>();
		this.measurementMap.put(AREA, Measurements.AREA);
		this.measurementMap.put(AREA_FRACTION, Measurements.AREA_FRACTION);
		this.measurementMap.put(MIN_MAX, Measurements.MIN_MAX);
		this.measurementMap.put(CIRCULARITY, Measurements.CIRCULARITY);
		this.measurementMap.put(STD_DEV, Measurements.STD_DEV);
		this.measurementMap.put(CENTER_OF_MASS, Measurements.CENTER_OF_MASS);
	}

	public List<String> getMeasurementList() {
		return measurementList;
	}



}
