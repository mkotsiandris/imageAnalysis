package web;


import ij.ImagePlus;
import image.ApplicationMain;
import image.helpers.FileMinion;
import image.models.ImageResult;
import image.models.Measurement;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "indexController")
@SessionScoped

public class IndexController implements Serializable {

	//constants
	private final String FORM_SUBMITTED = "The form submitted successfully!";
	private final String IMAGEANALYSIS = "imageAnalysis";
	private final String PARTICLEANALYSIS = "particleAnalysis";
	private final String DIR_PATH2 = "/src/main/webapp/WEB-INF/files/";
	private final String DIR_PATH = "src/main/webapp/WEB-INF/files/";
	private final String BLANK_IMAGE_PATH = "src/main/webapp/WEB-INF/files/blank.jpg";
	private final int BUFFER_SIZE = 6124;

	//variables
	private String thresholdType;
	private FileMinion fileMinion;
	private List<String> measurements;
	private String[] selectedMeasurements;

	public String getSuccess() {
		return success;
	}

	private String success;

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	private BufferedImage bufferedImage;
	private String uploadedFilePath;
	private String function;
	private ImageResult imageResult;
	private List<ImageResult> resultMap;
	private DefaultStreamedContent imgPreview;

	@PostConstruct
	public void init(){
		Measurement measurementModel = new Measurement();
		this.measurements = measurementModel.getMeasurementList();
		this.fileMinion = new FileMinion();
	}

	public String submitForm() {
		try{
			String msg = FORM_SUBMITTED;
			ImagePlus imagePlus = new ImagePlus("theTitle", bufferedImage);
			ApplicationMain applicationMain = new ApplicationMain(this.selectedMeasurements, this.thresholdType, imagePlus, uploadedFilePath);
			this.resultMap = new ArrayList<>();
			if (this.function.equals(IMAGEANALYSIS)){
				this.resultMap = applicationMain.analyseImage();
			} else {
				this.resultMap = applicationMain.countParticles();
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		} catch(Exception e){
			e.printStackTrace();
		}
		fileMinion.deleteDirectoryAndFiles(DIR_PATH + getSessionID());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("result", this.resultMap);
		return "detail?faces-redirect=true";
	}

	public void handleFileUpload(FileUploadEvent event) {
		success = "something";
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
			success = "YEI";

		} catch (IOException e) {
			this.uploadedFilePath = null;
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

	private DefaultStreamedContent getBlankImage() {
		try {
			File blankImage = new File(BLANK_IMAGE_PATH);
			return new DefaultStreamedContent(new FileInputStream(blankImage));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			return new DefaultStreamedContent();
		}
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

	public ImageResult getImageResult() {
		return imageResult;
	}

	public void setImageResult(ImageResult imageResult) {
		this.imageResult = imageResult;
	}

	public List<ImageResult> getResultMap() {
		return resultMap;
	}

	public void setResultMap(List<ImageResult> resultMap) {
		this.resultMap = resultMap;
	}

	public String getUploadedFilePath() {
		return uploadedFilePath;
	}

	public DefaultStreamedContent getImgPreview() {
//		try {
//			ImagePlus imagePlus = new ImagePlus("theTitle", bufferedImage);
//			ApplicationMain applicationMain = new ApplicationMain(imagePlus, uploadedFilePath);
//			BufferedImage temp = applicationMain.applyThreshold(this.thresholdType);
//			ByteArrayOutputStream os = new ByteArrayOutputStream();
//			ImageIO.write(temp, "jpg", os);
//			InputStream is = new ByteArrayInputStream(os.toByteArray());
//			return new DefaultStreamedContent(is, "image/jpeg");
//		}catch (IOException e) {
//			e.printStackTrace();
//			return new DefaultStreamedContent();
//		}
//	}
		if (bufferedImage != null ) {
			ImagePlus imagePlus = new ImagePlus("theTitle", bufferedImage);
			ApplicationMain applicationMain = new ApplicationMain(imagePlus, uploadedFilePath);
			BufferedImage temp = applicationMain.applyThreshold(this.thresholdType);
			int newWidth = new Double(temp.getWidth() * 0.5).intValue();
			int newHeight = new Double(temp.getHeight() * 0.5).intValue();
			BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = newImage.createGraphics();
			g.drawImage(temp, 0, 0, newWidth, newHeight, null);
			g.dispose();
			ByteArrayOutputStream bas = new ByteArrayOutputStream();
			try {
				ImageIO.write(newImage,"png", bas);
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte[] data = bas.toByteArray();
			InputStream is = new ByteArrayInputStream(data);
			imgPreview = new DefaultStreamedContent(is);
		} else {
			imgPreview = this.getBlankImage();
		}
		return imgPreview;

	}

	public void updateThresholdType(ValueChangeEvent event) {
		event.getNewValue().toString();
	}

}