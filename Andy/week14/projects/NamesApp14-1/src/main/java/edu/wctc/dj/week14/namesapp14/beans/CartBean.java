package edu.wctc.dj.week14.namesapp14.beans;

import edu.wctc.dj.week14.namesapp14.model.Name;
import edu.wctc.dj.week14.namesapp14.model.ShoppingCart;
import edu.wctc.dj.week14.namesapp14.service.ShoppingCartService;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("cartBean")
@Scope("session")
public class CartBean implements Serializable {

	private final String sessionId;
	private final ShoppingCart cart;
	private final ShoppingCartService cartService = new ShoppingCartService();

	public CartBean() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		sessionId = facesContext.getExternalContext().getSessionId(true);
		cart = cartService.getContents(sessionId);
	}

	public int getItemsInCart() {
		return cart.getItemsInCart();
	}

	public void addToCart(Name name) {
		cart.add(name);
		cartService.update(sessionId, cart);
	}

	
}
