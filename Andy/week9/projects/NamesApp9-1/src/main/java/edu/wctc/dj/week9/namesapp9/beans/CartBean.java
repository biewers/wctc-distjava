package edu.wctc.dj.week9.namesapp9.beans;

import edu.wctc.dj.week9.namesapp9.model.Name;
import edu.wctc.dj.week9.namesapp9.model.ShoppingCart;
import edu.wctc.dj.week9.namesapp9.model.ShoppingCartService;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;

@Named(value = "cartBean")
@SessionScoped
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
