/**
 * Created by marioskotsiandris on 10/17/14.
 */
package image;

import image.models.ImageModel;

public class ApplicationMain {

	public void mainFunction(String filePath) {
		ImageModel imageModel = new ImageModel(filePath);
		imageModel.countParticlesProcess("Default");
	}
}
