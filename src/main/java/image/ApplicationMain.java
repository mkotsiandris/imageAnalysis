package image;

import ij.ImagePlus;
import image.helpers.ProcessHelper;
import image.models.ImageResult;
import image.models.Measurement;

import java.awt.image.BufferedImage;
import java.util.List;


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

	public ApplicationMain(ImagePlus imagePlus, String uploadedFilePath){
		this.imagePlus = imagePlus;
		this.filePath = uploadedFilePath;
	}

	public List<ImageResult> analyseImage(){
		ProcessHelper processHelper = new ProcessHelper(this.imagePlus, this.filePath);
		int measurements = this.measurement.convertMeasurementListToInt(this.selectedMeasurements);
		return processHelper.analyseImage(measurements, this.selectedThreshold);
	}

	public List<ImageResult> countParticles() {
		ProcessHelper processHelper = new ProcessHelper(this.imagePlus, this.filePath);
		int measurements = this.measurement.convertMeasurementListToInt(this.selectedMeasurements);
		return processHelper.countParticles(this.selectedThreshold, measurements);
	}

	public BufferedImage applyThreshold(String selectedThreshold){
		if (selectedThreshold != null){
			ProcessHelper processHelper = new ProcessHelper(this.imagePlus, this.filePath);
			return processHelper.applyThreshold(selectedThreshold);
		} else {
			ProcessHelper processHelper = new ProcessHelper(this.imagePlus, this.filePath);
			return processHelper.applyThreshold("Default");
		}

	}
}
