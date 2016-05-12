package team17.sheet08;


import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class Demo2 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
    	
    	String names[] = {"Aaron", "Ben", "Carter", "Derek", "Elvis", "Finnick", "George", 
    			"Harry", "Isaac", "Jen", "Kelly", "Lisa", "Mary", "Nora", "Olga", "Paula",
    			"Rose", "Susan", "Tanner"};

        Map<String, Person> contacts = new ChordMap<>(5,5);

        // Person p = new Person("Tim", "tim@email.com");
        // contacts.put(p.getNickname(), p);
        // System.out.println(contacts.get(p.getNickname()));
        
        // put all persons into the DHT
        for (String name : names) {
        	contacts.put(name, new Person(name, name + "@email.com"));
        }

        // look for some persons
        Random random = new Random();
        for (int i = 0; i < names.length / 2; i++) {
        	int randomIndex = random.nextInt(names.length);
        	System.out.println("Get " + names[randomIndex] + ": " + contacts.get(names[randomIndex]));
        }
        
        System.out.println();
        
        // delete some persons
        System.out.println("Remove Aaron: " + contacts.remove("Aaron"));
        System.out.println("Remove Ben: " + contacts.remove("Ben"));
        System.out.println("Remove Carter: " + contacts.remove("Carter"));
        System.out.println("Remove Derek: " + contacts.remove("Derek"));
        System.out.println("Remove Elvis: " + contacts.remove("Elvis"));
        System.out.println("Remove Finnick: " + contacts.remove("Finnick"));
        System.out.println("Remove George: " + contacts.remove("George"));
        
        System.out.println();
        
        // again look for some persons, some may have been removed in the meanwhile
        for (int i = 0; i < names.length / 2; i++) {
        	int randomIndex = random.nextInt(names.length);
        	System.out.println("Get " + names[randomIndex] + ": " + contacts.get(names[randomIndex]));
        }
        
    }

    static void hash(Object key){
        System.out.println(key.hashCode());
    }
}
