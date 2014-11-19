package web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "resultController")
@ViewScoped
public class ResultController implements Serializable{

	@ManagedProperty("#{indexController}")
	private IndexController indexController;
	private HashMap<String, String> result;


	@PostConstruct
	public void init() {
		this.result = indexController.getResult();
	}

	/*Getters and Setters */
	public List<Map.Entry<String, String>> getResult() {
		return new ArrayList(this.result.entrySet());
	}

	public void setResult(HashMap<String, String> result) {
		this.result = result;
	}

	public void setIndexController(IndexController indexController) {
		this.indexController = indexController;
	}
}
