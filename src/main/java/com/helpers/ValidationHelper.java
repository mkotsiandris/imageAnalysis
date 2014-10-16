package com.helpers;


import java.io.File;

public class ValidationHelper {

	public static boolean isFilePathValid(String filePath) {
		try {
			File imageFile = new File(filePath);
			if (imageFile.exists() && !imageFile.isDirectory()) {
				return true;
			} else
				System.out.println("The filepath you provided is not valid.");
			return false;
		} catch (Exception e) {
			System.out.println("Something wrong happened.");
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isThresholdTypeValid(String thresholdType) {
		switch (thresholdType) {
			case "Default":
				return true;
			case "Huang":
				return true;
			case "Intermodes":
				return true;
			case "IsoData":
				return true;
			case "IJ_IsoData":
				return true;
			case "Percentile":
				return true;
			case "Triangle":
				return true;
		}
		return false;
	}
}
