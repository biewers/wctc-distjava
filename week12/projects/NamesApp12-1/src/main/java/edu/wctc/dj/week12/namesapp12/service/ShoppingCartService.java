package edu.wctc.dj.week9.namesapp9.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ShoppingCartService {

	private static final Map<String, ShoppingCart> contents = new HashMap<>();

	public ShoppingCart getContents(String sessionId) {
		ShoppingCart cart = contents.computeIfAbsent(sessionId,
			(String t) -> new ShoppingCart());

//		ShoppingCart cart = contents.computeIfAbsent(sessionId,
//			new Function<String, ShoppingCart>() {
//			@Override
//			public ShoppingCart apply(String t) {
//				return new ShoppingCart();
//			}
//		});
		return cart;
	}

	public void update(String sessionId, ShoppingCart cart) {
		contents.put(sessionId, cart);	
	}

	public void delete(String sessionId) {
		contents.remove(sessionId);
	}

}
