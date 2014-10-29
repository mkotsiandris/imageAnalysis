package image.helpers;

import java.io.File;
import java.io.IOException;

public class FileMinion {

	public void createUserDir(final String dirName, String dirPath) throws IOException {
		try {
			final File homeDir = new File(dirPath);
			final File dir = new File(homeDir, dirName);
			if (!dir.exists() && !dir.mkdirs()) {
				throw new IOException("Unable to create " + dir.getAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int deleteFile(String filepath) {
		try {
			File file = new File(filepath);
			return (file.delete()) ? 1 : -1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int deleteDirectoryAndFiles(String dirPath) {
		File directory = new File(dirPath);
		if (!directory.exists()) {
			return -1;
		} else {
			try {
				this.delete(directory);
				return 1;
			} catch (IOException e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	private void delete(File file) throws IOException {
		if (file.isDirectory()) {
			if (file.list().length == 0) {
				file.delete();
				System.out.println("Directory is deleted : " + file.getAbsolutePath());
			} else {
				String files[] = file.list();
				for (String temp : files) {
					File fileDelete = new File(file, temp);
					delete(fileDelete);
				}
				if (file.list().length == 0) {
					file.delete();
					System.out.println("Directory is deleted : " + file.getAbsolutePath());
				}
			}
		} else {
			file.delete();
			System.out.println("File is deleted : " + file.getAbsolutePath());
		}
	}
}