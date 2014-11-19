package web;


import ij.ImagePlus;
import image.ApplicationMain;
import image.helpers.FileMinion;
import image.models.Measurement;
import org.primefaces.event.FileUploadEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;

@ManagedBean(name = "indexController")
@ViewScoped

public class IndexController implements Serializable {

	private final String FORM_SUBMITTED = "The form submitted successfully!";
	private final String DIR_PATH2 = "/home/beast/Projects/imageAnalysis/src/main/webapp/WEB-INF/files/";
	private final String DIR_PATH = "/Users/cerebro/Projects/imageAnalysis/src/main/webapp/WEB-INF/files/";
	private final int BUFFER_SIZE = 6124;

	private FormModel formModel;
	private String thresholdType;
	private FileMinion fileMinion;
	private List<String> measurements;
	private String[] selectedMeasurements;
	private BufferedImage bufferedImage;
	private String uploadedFilePath;
	private String function;

	private HashMap<String, String> result;

	public IndexController() {
		this.formModel = new FormModel();
	}

	@PostConstruct
	public void init(){
		Measurement measurementModel = new Measurement();
		this.measurements = measurementModel.getMeasurementList();
		this.fileMinion = new FileMinion();
		result = new HashMap<>();
	}

	public String submitForm() {
		try{
			String msg = FORM_SUBMITTED;
			ImagePlus imagePlus = new ImagePlus("theTitle", bufferedImage);
			ApplicationMain applicationMain = new ApplicationMain(this.selectedMeasurements, this.thresholdType, imagePlus, uploadedFilePath);
			result = applicationMain.analyseImage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		} catch(Exception e){
			e.printStackTrace();
		}
		fileMinion.deleteDirectoryAndFiles(DIR_PATH + getSessionID());
		return "detail?faces-redirect=true";
	}

	public void handleFileUpload(FileUploadEvent event) {
		String sessionID = this.getSessionID();
		this.uploadedFilePath = DIR_PATH + sessionID+ "/" + event.getFile().getFileName();
		try {
			fileMinion.createUserDir(sessionID, DIR_PATH);
			File result = new File(this.uploadedFilePath);
			FileOutputStream fileOutputStream = new FileOutputStream(result);

			byte[] buffer = new byte[BUFFER_SIZE];

			int bulk;
			InputStream inputStream = event.getFile().getInputstream();
			bufferedImage = ImageIO.read(inputStream);
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

			FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, "The files were not uploaded!", "");
			FacesContext.getCurrentInstance().addMessage(null, error);
		}
	}

	private String getSessionID(){
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		return session.getId();
	}


	/* Getters and Setters */
	public FormModel getFormModel() {
		return formModel;
	}

	public void setFormModel(FormModel formModel) {
		this.formModel = formModel;
	}

	public String getThresholdType() {
		return thresholdType;
	}

	public void setThresholdType(String thresholdType) {
		this.thresholdType = thresholdType;
	}

	public String[] getSelectedMeasurements() {
		return selectedMeasurements;
	}

	public void setSelectedMeasurements(String[] selectedMeasurements) {
		this.selectedMeasurements = selectedMeasurements;
	}

	public List<String> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(List<String> measurements) {
		this.measurements = measurements;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public HashMap<String, String> getResult() {
		return result;
	}

	public void setResult(HashMap<String, String> result) {
		this.result = result;
	}
}