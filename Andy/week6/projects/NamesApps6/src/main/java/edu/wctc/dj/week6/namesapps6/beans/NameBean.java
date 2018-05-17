package edu.wctc.dj.week6.namesapps6.beans;

import edu.wctc.dj.week6.namesapps6.model.Name;
import edu.wctc.dj.week6.namesapps6.model.NameService;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

@Named(value = "nameBean")
@SessionScoped
public class NameBean implements Serializable {

	private final NameService nameService = new NameService();
	private Name name;
	private List<Name> nameList;

	public NameBean() {
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

	public void setNameList(List<Name> nameList) {
		this.nameList = nameList;
	}
	
	public String allNames() {
		nameList = nameService.getAllNames();
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

}
