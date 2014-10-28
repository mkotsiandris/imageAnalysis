package web;

import ij.ImagePlus;
import image.models.ImageModel;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@ManagedBean(name = "indexController")
@ViewScoped
public class IndexController {

	private final String FORM_SUBMITTED = "The form submitted successfully!";
	private final String ERROR_OCURED = "An error ocurred during submission!";
	private static final int BUFFER_SIZE = 6124;
	private final String DIR_PATH = "/Users/cerebro/Projects/imageAnalysis/src/main/webapp/WEB-INF/files";

	private FormModel formModel;
	private UploadedFile uploadedFile;

	public IndexController() {
		this.formModel = new FormModel();
	}

	public FormModel getFormModel() {
		return formModel;
	}

	public void setFormModel(FormModel formModel) {
		this.formModel = formModel;
	}

	public int submitForm() {
		String msg = FORM_SUBMITTED;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return 1;
	}

	public void handleFileUpload(FileUploadEvent event) {
		ExternalContext extContext =
				FacesContext.getCurrentInstance().getExternalContext();
		File result = new File((DIR_PATH + event.getFile().getFileName()));

		try {
			FileOutputStream fileOutputStream;

			byte[] buffer = new byte[BUFFER_SIZE];

			int bulk;
			InputStream inputStream = event.getFile().getInputstream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);

			int filesCount = dataInputStream.readInt();
			File[] files = new File[filesCount];

			for (int i = 0; i< filesCount; i++) {
				long fileLength = dataInputStream.readLong();
				String fileName = dataInputStream.readUTF();
				byte[] bytes = new byte[(int)fileLength];
				dataInputStream.readFully(bytes);
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));


				ImageModel imageModel =  new ImageModel("the Image", bufferedImage);
				Double marios = imageModel.calculatePorosityProcess();
				System.out.println("the porositu is"+ marios.toString());


				files[i] = new File(DIR_PATH + "/" + fileName);
				fileOutputStream = new FileOutputStream(files[i]);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
				bufferedOutputStream.write(bytes, 0, (int)fileLength);
				bufferedOutputStream.close();
			}

			inputStream.close();

			FacesMessage msg =
					new FacesMessage("File Description", "file name: " +
							event.getFile().getFileName() + "file size: " +
							event.getFile().getSize() / 1024 + " Kb content type: " +
							event.getFile().getContentType() + "The file was uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, msg);


		} catch (IOException e) {
			e.printStackTrace();

			FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"The files were not uploaded!", "");
			FacesContext.getCurrentInstance().addMessage(null, error);
		}
	}
}