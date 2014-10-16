package com.helpers;

import com.models.ImageModel;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.util.Iterator;

public class ImageModelHelper {

	public static String getImageType(ImageModel imageModel){
		int type = imageModel.getType();
		switch (type) {
			case 1 :
				return "GRAY8";
			case 2:
				return "GRAY16";
			case 3:
				return  "GRAY32";
			case 4:
				return "COLOR_256";
			case 5:
				return "COLOR_RGB";
			default:
				return "Unknown";
		}
	}

	public static void readImageAndDisplayMetaData(String filePath) {
		try {
			File file = new File(filePath);
			ImageInputStream imageInputStream = ImageIO.createImageInputStream(file);
			Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);

			if (readers.hasNext()) {
				ImageReader reader = readers.next();
				reader.setInput(imageInputStream, true);
				IIOMetadata metadata = reader.getImageMetadata(0);

				String[] names = metadata.getMetadataFormatNames();
				int length = names.length;
				for (int i = 0; i < length; i++) {
					System.out.println( "Format name: " + names[ i ] );
					displayMetadata(metadata.getAsTree(names[i]));
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	static void displayMetadata(Node root) {
		displayMetadata(root, 0);
	}

	static void indent(int level) {
		for (int i = 0; i < level; i++)
			System.out.print("    ");
	}

	static void displayMetadata(Node node, int level) {
		// print open tag of element
		indent(level);
		System.out.print("<" + node.getNodeName());
		NamedNodeMap map = node.getAttributes();
		if (map != null) {

			// print attribute values
			int length = map.getLength();
			for (int i = 0; i < length; i++) {
				Node attr = map.item(i);
				System.out.print(" " + attr.getNodeName() +
						"=\"" + attr.getNodeValue() + "\"");
			}
		}

		Node child = node.getFirstChild();
		if (child == null) {
			// no children, so close element and return
			System.out.println("/>");
			return;
		}

		// children, so close current tag
		System.out.println(">");
		while (child != null) {
			// print children recursively
			displayMetadata(child, level + 1);
			child = child.getNextSibling();
		}

		// print close tag of element
		indent(level);
		System.out.println("</" + node.getNodeName() + ">");
	}
}
