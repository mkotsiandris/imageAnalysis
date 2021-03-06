package image.models;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

public class Result {

	public List<ParticleResult> particleResults;
	public ParticleResult staticParticle;
	private HashMap<String, Boolean> selectedMeasurements;
	private BufferedImage processedImage;
	public  HashMap<String, String> averageMap;

	public Result(List<ParticleResult> theParticleResults, BufferedImage theImage) {
		this.particleResults = theParticleResults;
		this.processedImage = theImage;
	}

	public Result(){
		this.particleResults = null;
		this.processedImage = null;
	}

	//Getters and Setters//
	public List<ParticleResult> getParticleResults() {
		return particleResults;
	}

	public BufferedImage getProcessedImage() {
		return processedImage;
	}

	public void setParticleResults(List<ParticleResult> particleResults) {
		this.particleResults = particleResults;
	}

	public void setProcessedImage(BufferedImage processedImage) {
		this.processedImage = processedImage;
	}

	public void setSelectedMeasurements(HashMap<String, Boolean> selectedMeasurements) {
		this.selectedMeasurements = selectedMeasurements;
	}

	public HashMap<String, Boolean> getSelectedMeasurements() {
		return selectedMeasurements;
	}


	private void makeStatisticCalculations() {

	}

}
