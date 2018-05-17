package edu.wctc.dj.week12.namesapp12.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {
	private final List<Name> contents = new ArrayList<>();

	public List<Name> getContents() {
		return contents;
	}

	public int getItemsInCart() {
		return contents.size();
	}

	public void add(Name name) {
		contents.add(name);
	}

	public void remove(Name name) {
		contents.remove(name);
	}

	public void removeAll() {
		contents.clear();
	}

}
