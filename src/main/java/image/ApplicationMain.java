package image;

import ij.ImagePlus;
import image.helpers.ProcessHelper;
import image.models.Measurement;

import java.util.HashMap;


public class ApplicationMain {

	private String[] selectedMeasurements;
	private String selectedThreshold;
	private Measurement measurement;
	private String filePath;
	private ImagePlus imagePlus;

	public ApplicationMain(String[] selectedMeasurements, String selectedThreshold, String filePath){
		this.selectedMeasurements = selectedMeasurements;
		this.selectedThreshold = selectedThreshold;
		this.filePath = filePath;
		this.measurement = new Measurement();
	}

	public ApplicationMain(String[] selectedMeasurements, String selectedThreshold, ImagePlus imagePlus){
		this.selectedMeasurements = selectedMeasurements;
		this.selectedThreshold = selectedThreshold;
		this.measurement = new Measurement();
		this.imagePlus = imagePlus;
	}

	public void analyseImage(){
		ProcessHelper processHelper = new ProcessHelper(this.imagePlus);
		int measurements = this.measurement.convertMeasurementListToInt(this.selectedMeasurements);
		HashMap<String, Double> analysisMap = processHelper.getPorosity(measurements);
	}

	public void countParticles() {
		ProcessHelper processHelper = new ProcessHelper(this.imagePlus);
		int measurements = this.measurement.convertMeasurementListToInt(this.selectedMeasurements);
		String marios = processHelper.countParticles(this.selectedThreshold, measurements);
	}

	public String[] getSelectedMeasurements() {
		return selectedMeasurements;
	}

	public void setSelectedMeasurements(String[] selectedMeasurements) {
		this.selectedMeasurements = selectedMeasurements;
	}

	public String getSelectedThreshold() {
		return selectedThreshold;
	}

	public void setSelectedThreshold(String selectedThreshold) {
		this.selectedThreshold = selectedThreshold;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}



}
