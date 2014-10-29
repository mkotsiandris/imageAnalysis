
package image.helpers;

import ij.IJ;
import ij.ImagePlus;
import ij.measure.Calibration;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import ij.plugin.filter.ParticleAnalyzer;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class ProcessHelper {
	ImagePlus imagePlus;
	ImageConverter imageConverter;
	ImageProcessor imageProcessor;

	public ProcessHelper(String filePath) {
		this.imagePlus = IJ.openImage(filePath);
		this.imageConverter = new ImageConverter(this.imagePlus);
		this.imageProcessor = this.imagePlus.getProcessor();
		imageConverter.convertToGray8();
	}

	public ProcessHelper(ImagePlus imagePlus) {
		this.imagePlus = imagePlus;
		this.imageConverter = new ImageConverter(this.imagePlus);
		this.imageProcessor = this.imagePlus.getProcessor();
		imageConverter.convertToGray8();
	}

	public static void cropAndResize(ImagePlus imp, int targetWidth, int targetHeight) throws Exception {
		ImageProcessor ip = imp.getProcessor();
		System.out.println("size1: " + ip.getWidth() + "x" + ip.getHeight());
		ip.setInterpolationMethod(ImageProcessor.BILINEAR);
		ip = ip.resize(targetWidth * 2, targetHeight * 2);
		System.out.println("size2: " + ip.getWidth() + "x" + ip.getHeight());

		int cropX = ip.getWidth() / 2;
		int cropY = ip.getHeight() / 2;
		ip.setRoi(cropX, cropY, targetWidth, targetHeight);
		ip = ip.crop();
		System.out.println("size3: " + ip.getWidth() + "x" + ip.getHeight());
		BufferedImage croppedImage = ip.getBufferedImage();

		System.out.println("size4: " + ip.getWidth() + "x" + ip.getHeight());
		new ImagePlus("croppedImage", croppedImage).show();

		ImageIO.write(croppedImage, "jpg", new File("cropped.jpg"));
	}

	public HashMap<String, Double> getPorosity() throws NullPointerException {
		Double porosity;
		Double area;
		Double max;
		Double min;
		HashMap<String, Double> resultsMap = new HashMap<>();

		try {
			this.imagePlus.getProcessor().setAutoThreshold("Default");
			int measurements =
					Measurements.AREA +
							Measurements.MEAN +
							Measurements.MIN_MAX +
							Measurements.STD_DEV +
							Measurements.MODE +
							Measurements.MEDIAN +
							Measurements.AREA_FRACTION +
							Measurements.LIMIT;

			ResultsTable rt = new ResultsTable();
			Analyzer analyzer = new Analyzer(this.imagePlus, measurements, rt);
			analyzer.measure();
			porosity = rt.getValue("%Area", rt.getCounter() - 1);
			area = rt.getValue("Area", rt.getCounter() - 1);
			min = rt.getValue("Min", rt.getCounter() - 1);
			max = rt.getValue("Max", rt.getCounter() - 1);

			resultsMap.put("porosity", porosity);
			resultsMap.put("area", area);
			resultsMap.put("min", min);
			resultsMap.put("max", max);

			rt.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultsMap;
	}

	public void countParticles(String thresholdType) {
		try {
			this.imagePlus.getProcessor().setAutoThreshold(thresholdType);
			this.imagePlus.show();
//			this.imagePlus.setRoi(0, 0, width, height - 500);
			int measurments = Measurements.AREA +
					Measurements.FERET +
					Measurements.PERIMETER +
					Measurements.CIRCULARITY;
			ResultsTable rt = new ResultsTable();
			ParticleAnalyzer particleAnalyzer = new ParticleAnalyzer(ParticleAnalyzer.SHOW_OUTLINES, measurments, rt, 10, 99999);
			particleAnalyzer.analyze(this.imagePlus);
			rt.show("My analyzer");
		} catch (Exception e) {
			System.out.println("Exception on countParticles");
			e.printStackTrace();
		}
	}

	public void calibrateImage(double pX, double pY, String unit, ImagePlus imp) {
		double originX = 0.0;
		double originY = 0.0;
		Calibration cal = imp.getCalibration();
		cal.setUnit(unit);
		cal.pixelWidth = pX;
		cal.pixelHeight = pY;
		cal.xOrigin = originX / pX;
		cal.yOrigin = originY / pY;
	}

}

