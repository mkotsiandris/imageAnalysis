
package image.helpers;

import ij.ImagePlus;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import ij.plugin.filter.ParticleAnalyzer;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import image.models.ImageResult;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;

public class ProcessHelper {
	private ImagePlus imagePlus;
	private ImageConverter imageConverter;
	private String resultCsvPath;

	public ProcessHelper(ImagePlus imagePlus, String theFilePathName) {
		this.imagePlus = imagePlus;
		this.resultCsvPath = theFilePathName+"_result.csv";
		this.imageConverter = new ImageConverter(this.imagePlus);
		this.imageConverter.convertToGray8();
	}


	public List<ImageResult> analyseImage(int measurements, String threshold) throws NullPointerException {
		List<ImageResult> resultsMap = new ArrayList<>();
		try {
			this.imagePlus.getProcessor().setAutoThreshold(threshold);
			ResultsTable rt = new ResultsTable();
			Analyzer analyzer = new Analyzer(this.imagePlus, measurements, rt);
			analyzer.measure();
			new File(resultCsvPath);
			rt.saveAs(resultCsvPath);
			ReaderCSV readerCSV = new ReaderCSV(resultCsvPath);
			resultsMap = readerCSV.read();
			//makeExtraCalculations(resultsMap);
			rt.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultsMap;
	}

	public BufferedImage applyThreshold(String threshold) {
		ImagePlus marios = new ImagePlus();
		try {
			this.imagePlus.getProcessor().setAutoThreshold(threshold);
			marios = this.imagePlus;
		} catch (Exception e) {
			System.out.println("Exception on countParticles");
			e.printStackTrace();
		}
		return marios.getBufferedImage();

	}

	public List<ImageResult> countParticles(String thresholdType, int measurements) {
		List<ImageResult> resultsMap = new ArrayList<>();
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

