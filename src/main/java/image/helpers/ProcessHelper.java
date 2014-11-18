
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
	private ImagePlus imagePlus;
	private ImageConverter imageConverter;
	private ImageProcessor imageProcessor;
	private String resultCsvPath;
	private int measurements;

	public ProcessHelper(ImagePlus imagePlus, String theFilePathName) {
		this.imagePlus = imagePlus;
		this.resultCsvPath = theFilePathName+"_result.csv";
		this.imageConverter = new ImageConverter(this.imagePlus);
		this.imageProcessor = this.imagePlus.getProcessor();
		imageConverter.convertToGray8();
	}


	public HashMap<String, String> analyseImage(int measurements, String threshold) throws NullPointerException {
		HashMap<String, String> resultsMap = new HashMap<>();
		try {
			this.imagePlus.getProcessor().setAutoThreshold(threshold);
			ResultsTable rt = new ResultsTable();
			Analyzer analyzer = new Analyzer(this.imagePlus, measurements, rt);
			analyzer.measure();
			new File(resultCsvPath);
			rt.saveAs(resultCsvPath);
			ReaderCSV readerCSV = new ReaderCSV(resultCsvPath);
			resultsMap = readerCSV.read();
			rt.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultsMap;
	}

	public HashMap<String, String> countParticles(String thresholdType, int measurements) {
		HashMap<String, String> resultsMap = new HashMap<>();
		try {
			this.imagePlus.getProcessor().setAutoThreshold(thresholdType);
			ResultsTable rt = new ResultsTable();
			ParticleAnalyzer particleAnalyzer = new ParticleAnalyzer(ParticleAnalyzer.SHOW_OUTLINES, measurements, rt, 10, 99999);
			particleAnalyzer.analyze(this.imagePlus);
			new File(resultCsvPath);
			rt.saveAs(resultCsvPath);
			ReaderCSV readerCSV = new ReaderCSV(resultCsvPath);
			resultsMap = readerCSV.read();
			rt.reset();
		} catch (Exception e) {
			System.out.println("Exception on countParticles");
			e.printStackTrace();
		}
		return resultsMap;
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

