package web;

import image.models.ImageResult;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

@ManagedBean(name = "resultController")
@ViewScoped
public class ResultController implements Serializable{

	private List<ImageResult> imageResults;

	public void initialize(){
		 this.imageResults = (List<ImageResult>)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("result");
	}

	public List<ImageResult> getImageResults() {
		return imageResults;
	}

	public void setImageResults(List<ImageResult> imageResults) {
		this.imageResults = imageResults;
	}

}


