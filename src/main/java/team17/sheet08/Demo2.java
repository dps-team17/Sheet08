package team17.sheet08;


import java.io.IOException;
import java.util.Map;

public class Demo2 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {


        Map<String, Person> contacts = new ChordMap<>(5,5);

        Person p = new Person("Tim", "tim@email.com");

        contacts.put(p.getNickname(), p);

        System.out.println(contacts.get(p.getNickname()));
    }

    static void hash(Object key){
        System.out.println(key.hashCode());
    }
}
