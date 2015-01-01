package image;

import ij.ImagePlus;
import image.helpers.ProcessHelper;
import image.models.ImageResult;
import image.models.Measurement;

import java.util.HashMap;


public class ApplicationMain {

	private String[] selectedMeasurements;
	private String selectedThreshold;
	private Measurement measurement;
	private String filePath;
	private ImagePlus imagePlus;

	public ApplicationMain(String[] selectedMeasurements, String selectedThreshold, ImagePlus imagePlus, String theUploadedFilePath){
		this.selectedMeasurements = selectedMeasurements;
		this.selectedThreshold = selectedThreshold;
		this.measurement = new Measurement();
		this.imagePlus = imagePlus;
		this.filePath = theUploadedFilePath;
	}

	public HashMap<Integer, ImageResult> analyseImage(){
		ProcessHelper processHelper = new ProcessHelper(this.imagePlus, this.filePath);
		int measurements = this.measurement.convertMeasurementListToInt(this.selectedMeasurements);
		return processHelper.analyseImage(measurements, this.selectedThreshold);
	}

	public HashMap<Integer, ImageResult> countParticles() {
		ProcessHelper processHelper = new ProcessHelper(this.imagePlus, this.filePath);
		int measurements = this.measurement.convertMeasurementListToInt(this.selectedMeasurements);
		return processHelper.countParticles(this.selectedThreshold, measurements);
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
