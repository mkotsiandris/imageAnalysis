package image;

import ij.IJ;
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
		this.imagePlus = imagePlus;
	}

	public void countParticles() {
		ProcessHelper processHelper = new ProcessHelper(this.imagePlus);
//		HashMap<String, Double> mapper = processHelper.getPorosity();
		String marios = processHelper.countParticles(this.selectedThreshold, 4);
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
