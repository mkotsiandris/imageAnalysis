package web;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@ManagedBean(name = "indexController")
@ViewScoped
public class IndexController {

	private final String FORM_SUBMITTED = "The form submitted successfully!";
	private final String ERROR_OCURED = "An error ocurred during submission!";
	private static final int BUFFER_SIZE = 6124;

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
		File result = new File(("/Users/cerebro/Projects/imageAnalysis/src/main/webapp/WEB-INF/files" + event.getFile().getFileName()));
		System.out.println(extContext.getRealPath
				("//WEB-INF//files//" + event.getFile().getFileName()));

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(result);

			byte[] buffer = new byte[BUFFER_SIZE];

			int bulk;
			InputStream inputStream = event.getFile().getInputstream();
			while (true) {
				bulk = inputStream.read(buffer);
				if (bulk < 0) {
					break;
				}
				fileOutputStream.write(buffer, 0, bulk);
				fileOutputStream.flush();
			}

			fileOutputStream.close();
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