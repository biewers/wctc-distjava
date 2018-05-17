package edu.wctc.dj.week6.namesapp62.beans;

import edu.wctc.dj.week6.namesapp62.model.Name;
import edu.wctc.dj.week6.namesapp62.model.NameService;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

@Named("nameBean")
@SessionScoped
public class NameBean implements Serializable {

	private final NameService nameService = new NameService();

	private String search;
	private Name name;
	private List<Name> nameList;

	public NameBean() {
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public List<Name> getNameList() {
		return nameList;
	}

	public String searchNames() {
		nameList = nameService.findNames(search);
		return "nameList";
	}

	public void nameDetail(AjaxBehaviorEvent event) {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
				.redirect("nameDetail.xhtml?id=" + name.getId());
		} catch (IOException ex) {
			FacesMessage msg = new FacesMessage("IOException", name.getId());
        		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public String allNames() {
		nameList = nameService.getAllNames();
		return "nameList";
	}
}
