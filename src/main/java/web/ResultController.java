package web;

import image.models.ImageResult;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.*;

@ManagedBean(name = "resultController")
@ViewScoped
public class ResultController implements Serializable{

	@ManagedProperty("#{indexController}")
	private IndexController indexController;
	private ImageResult imageResult;
	private List<ImageResult> imageResults;

	@PostConstruct
	public void init() {
		this.imageResult = indexController.getImageResult();
	}

	public List<ImageResult> getImageResults() {
		return imageResults;
	}

	public void setImageResults(List<ImageResult> imageResults) {
		this.imageResults = imageResults;
	}

	public ImageResult getImageResult() {
		return imageResult;
	}

	public void setImageResult(ImageResult imageResult) {
		this.imageResult = imageResult;
	}

	public void setIndexController(IndexController indexController) {this.indexController = indexController;}

}


