package web;

import image.models.ImageResult;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

@ManagedBean(name = "resultController")
@ViewScoped
public class ResultController implements Serializable{


	@ManagedProperty("#{indexController}")
	private IndexController indexController;
	private ImageResult imageResult;
	private String columnTemplate = "Area Min Max";
	private List<ColumnModel> columns;
	private List<String> validColumnKeys;

	@PostConstruct
	public void init() {
		this.imageResult = indexController.getImageResult();
		this.validColumnKeys.addAll(this.imageResult.properties.keySet());
	}

	private void createDynamicColumns() {
		String[] columnKeys = columnTemplate.split(" ");
		columns = new ArrayList<ColumnModel>();

		for(String columnKey : columnKeys) {
			String key = columnKey.trim();

			if(validColumnKeys.contains(key)) {
				columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));
			}
		}
	}

	public void updateColumns() {
		//reset table state
		UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:results");
		table.setValueExpression("sortBy", null);

		//update columns
		createDynamicColumns();
	}


	public ImageResult getImageResult() {
		return imageResult;
	}

	public void setImageResult(ImageResult imageResult) {
		this.imageResult = imageResult;
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	/*Declare column model*/
	static public class ColumnModel implements Serializable {

		private String header;
		private String property;

		public ColumnModel(String header, String property) {
			this.header = header;
			this.property = property;
		}

		public String getHeader() {
			return header;
		}

		public String getProperty() {
			return property;
		}
	}

}


