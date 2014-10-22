package servlet;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ViewScoped
public class UserController {
	private User registrationUser;

	public UserController() {
		this.registrationUser = new User();
	}

	public User getRegistrationUser(){
		return this.registrationUser;
	}

	public void setRegistrationUser(User registrationUser){
		this.registrationUser = registrationUser;
	}

	public String register() {
		System.out.println("Register User :"+ this.registrationUser);
		String msg = "User Registered Successfully";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return "registration.jsf?faces-redirect=true";
	}
}
