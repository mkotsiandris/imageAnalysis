package image.models;

import image.helpers.ImageModelHelper;
import image.helpers.ProcessHelper;
import image.helpers.ValidationHelper;
import ij.IJ;
import ij.ImagePlus;

public class ImageModel extends ImagePlus {

	public String filePath;
	private int height = -1;
	private int width = -1;
	private ImagePlus imp;
	public Double porosity = -1.0;


	public ImageModel(String thePath) {
		this.filePath = String.valueOf(thePath);
		this.imp = IJ.openImage(this.filePath);
	}

	private void validateFilepath(String filePath) {
		if (ValidationHelper.isFilePathValid(filePath)) {
		} else {
			System.out.println("The filepath you are providing is not valid");
		}
	}

	public Double calculatePorosityProcess() {
		ProcessHelper processHelper = new ProcessHelper(this.filePath);
		this.porosity = processHelper.getPorosity().get("porosity");
		return this.porosity;
	}

	public void countParticlesProcess(String thresholdType) {
		ProcessHelper processHelper = new ProcessHelper(this.filePath);
		if (ValidationHelper.isThresholdTypeValid(thresholdType)){
			processHelper.countParcicles(thresholdType);
		}
	}

	public void displayImageMetaData() {
		ImageModelHelper.readImageAndDisplayMetaData(this.filePath);
	}

	public String getImageType() {
		return ImageModelHelper.getImageType(this);
	}

	public int getImageHeight() {
		if (this.height == -1) {
			this.height = this.imp.getHeight();
		}
		return this.height;
	}

	public int getImageWidth() {
		if (this.width == -1) {
			this.width = this.imp.getWidth();
		}
		return this.width;
	}
}
