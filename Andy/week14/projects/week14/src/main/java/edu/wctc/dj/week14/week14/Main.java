package edu.wctc.dj.week14.week14;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		Main main = new Main();
		main.run(args);
	}

	public void run(String[] args) throws Exception {
		//lambdas();
//		streams();
		reflection();
	}

	public void lambdas() {
		LOGGER.info("We're running!");

		try {
			throw new Exception("Bang!");
		} catch (Exception e) {
			LOGGER.error("Something failed", e);
		}

		// A new class that implements Barkable
		Barkable barky = new Dog("Doug", "Jones");
		doSomething(barky);

		// Anonymous inner classes
		barky = new Barkable() {
			@Override
			public void bark(int i) {
				LOGGER.info("The inner class says woof " + i);
			}
			
		};
		doSomething(barky);

		// Lambda
		barky = (i) -> {
			LOGGER.info("The lambda says woof " + i);
		};
		doSomething(barky);
	}

	private void doSomething(Barkable barky) {
		barky.bark((int) (Math.random()*100));
	}

	public void streams() {
		List<Dog> dogList = 
			Arrays.asList(
				new Dog("Joey", "Last"),
				new Dog("Isaac", "Jones")
			);

		LOGGER.info("Dogs = " + joinDogNames(dogList));
	}

	private String joinDogNames(List<Dog> dogList) {
		String result = null;
		if (dogList != null) {
			result = dogList.stream()
				.map((dog) -> {
					return dog.getFirstName() + " " + dog.getLastName();
				}).collect(Collectors.joining(","));
		}
		return result;
	}

	private List<String> listDogNames(List<Dog> dogList) {
		List<String> result = null;
		if (dogList != null) {
			result = dogList.stream()
				.map((dog) -> {
					return dog.getFirstName() + " " + dog.getLastName();
				}).collect(Collectors.toList());
		}
		return result;
	}

	public void reflection() throws Exception {
		String className = "edu.wctc.dj.week14.week14.Dog";
		Class<?> aClass = Class.forName(className);
		Constructor<?> theCtor = aClass.getConstructor(String.class, String.class);
		Object theThing = theCtor.newInstance("First", "Last");
		LOGGER.info("The thing is " + theThing);

		Method theBarkMethod = aClass.getMethod("bark", int.class);
		theBarkMethod.invoke(theThing, 234);
	}
}
