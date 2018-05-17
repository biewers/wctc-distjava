package edu.wctc.dj.week10.namesapp10.beans;

import edu.wctc.dj.week10.namesapp10.model.Name;
import edu.wctc.dj.week10.namesapp10.model.ShoppingCart;
import edu.wctc.dj.week10.namesapp10.service.ShoppingCartService;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "cartBean")
@Scope("session")
public class CartBean implements Serializable {

	private final String sessionId;
	private final ShoppingCart cart;
	private final ShoppingCartService cartService;

	@Autowired
	public CartBean(ShoppingCartService cartService) {
		this.cartService = cartService;

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
