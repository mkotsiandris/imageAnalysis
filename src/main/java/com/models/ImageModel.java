package com.models;

import com.helpers.ImageModelHelper;
import com.helpers.ProcessHelper;
import com.helpers.ValidationHelper;
import ij.IJ;
import ij.ImagePlus;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import ij.plugin.filter.ParticleAnalyzer;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class ImageModel extends ImagePlus {

	public String filePath;
	public String type;
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
