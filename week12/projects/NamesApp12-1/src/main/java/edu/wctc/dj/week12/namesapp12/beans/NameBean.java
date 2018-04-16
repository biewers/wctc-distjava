package edu.wctc.dj.week12.namesapp12.beans;

import edu.wctc.dj.week12.namesapp12.model.Name;
import edu.wctc.dj.week12.namesapp12.service.NameService;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("nameBean")
@Scope("session")
public class NameBean implements Serializable {

	@Autowired
	private NameService nameService;

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

	public String allNames() throws Exception {
		nameList = nameService.getAllNames();
		return "nameList";
	}
}
