
package image.helpers;

import ij.ImagePlus;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import ij.plugin.filter.ParticleAnalyzer;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import image.models.ParticleResult;
import image.models.Result;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.HashMap;
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

	public Result analyseImage(int measurements, String threshold) throws NullPointerException {
		List<ParticleResult> resultsMap = new ArrayList<>();
		Result theResult;
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
			theResult = new Result(resultsMap, this.applyThreshold(threshold));
			theResult.staticParticle = new ParticleResult(this.calculateAverageModel(rt, readerCSV.headersArray));
			theResult.staticParticle.setId("Average Particle");
			theResult.particleResults.add(0, theResult.staticParticle);
			rt.reset();
		} catch (Exception e) {
			theResult = new Result();
			e.printStackTrace();
		}
		return theResult;
	}

	public BufferedImage applyThreshold(String threshold) {
		ImagePlus temp = new ImagePlus();
		try {
			this.imagePlus.getProcessor().setAutoThreshold(threshold);
			temp = this.imagePlus;
		} catch (Exception e) {
			System.out.println("Exception on countParticles");
			e.printStackTrace();
		}
		return temp.getBufferedImage();

	}

	public Result countParticles(String thresholdType, int measurements) {
		List<ParticleResult> resultsMap = new ArrayList<>();
		Result theResult;
		try {
			this.imagePlus.getProcessor().setAutoThreshold(thresholdType);
			ResultsTable rt = new ResultsTable();
			ParticleAnalyzer particleAnalyzer = new ParticleAnalyzer(ParticleAnalyzer.SHOW_OUTLINES, measurements, rt, 10, 99999);
			particleAnalyzer.analyze(this.imagePlus);
			new File(resultCsvPath);
			rt.saveAs(resultCsvPath);
			ReaderCSV readerCSV = new ReaderCSV(resultCsvPath);
			resultsMap = readerCSV.read();
			theResult = new Result(resultsMap, particleAnalyzer.getOutputImage().getBufferedImage());
			theResult.staticParticle = new ParticleResult(this.calculateAverageModel(rt, readerCSV.headersArray));
			theResult.staticParticle.setId("Average Particle");
			theResult.particleResults.add(0,theResult.staticParticle);
			rt.reset();
		} catch (Exception e) {
			theResult = new Result();
			System.out.println("Exception on countParticles");
			e.printStackTrace();
		}
		return theResult;
	}

	public HashMap<String, String> calculateAverageModel(ResultsTable rt, String[] headers) {
		HashMap<String, String> averageMap = new HashMap<>();
		for (int i = 1; i<headers.length; i++){
			int idx = rt.getColumnIndex(headers[i]);
			averageMap.put(rt.getColumnHeading(idx), getAverageFromArray(rt.getColumn(idx)));
		}
		return averageMap;
	}

	public String getAverageFromArray(float[] array) {
		float temp = 0;
		for (int i = 0; i<array.length; i++) {
			temp = temp + array[i];
		}
		return String.valueOf(temp/array.length);
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

