package edu.wctc.dj.week14.week14;

public interface Barkable {

	void bark(int i);

	default void whimper() {}
	
}
