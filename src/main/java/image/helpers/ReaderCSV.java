package image.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReaderCSV {
	private BufferedReader br;
	private HashMap<String, String> map;
	private String filePath;

	public ReaderCSV(String theFile) {
		filePath = theFile;

	}

	public HashMap<String, String> read() {
		String line;
		String csvSplitBy = ",";

		try {
			map = new HashMap<>();
			br = new BufferedReader(new FileReader(filePath));
			String[] rowHeaders = br.readLine().split(csvSplitBy);
			while ((line = br.readLine()) != null) {
				String[] row = line.split(csvSplitBy);
				map = matchHeadersWithData(rowHeaders, row);
			}

			//loop map
			for (Map.Entry<String, String> entry : map.entrySet()) {

				System.out.println("Country [code= " + entry.getKey() + " , name="
						+ entry.getValue() + "]");

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return map;
	}

	private HashMap<String, String> matchHeadersWithData(String[] headers, String[] data) {

		if (headers.length != data.length) {
			return map;
		} else {
			for (int i=0; i<headers.length; i++) {
				map.put(headers[i], data[i]);
			}
		}
		map = deleteEmptyElements(map);
		return map;
	}

	private HashMap<String, String> deleteEmptyElements(HashMap<String, String> m) {
		for(Iterator<Map.Entry<String, String>> it = map.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = it.next();
			if(entry.getKey().isEmpty()) {
				it.remove();
			}
		}
		return m;
	}

}
