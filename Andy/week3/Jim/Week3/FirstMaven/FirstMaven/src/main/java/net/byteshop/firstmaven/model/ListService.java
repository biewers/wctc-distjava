package net.byteshop.firstmaven.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jlombardo
 */
public class ListService {
    private List<String> messages;

    public ListService() {
        messages = new ArrayList<>();
        messages.add("Hello World");
        messages.add("Hello Java");
    }

    public List<String> getMessages() {
        return messages;
    }
    
    public String getRandomMessage() {
        Random r = new Random(System.nanoTime());
        int index = r.nextInt(messages.size());
        return messages.get(index);
    }
    
}
