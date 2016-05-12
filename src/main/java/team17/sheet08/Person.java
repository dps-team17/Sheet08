package team17.sheet08;

import java.io.Serializable;

public class Person implements Serializable{

    private String nickname;
    private String email;

    public Person(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int hash = 17;
        hash = prime*hash + nickname.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == this) return true;

        if (obj == null) return false;

        if (obj.getClass() != this.getClass()) return false;

        Person other = (Person) obj;

        return other.nickname.equals(this.getNickname());
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", nickname, email);
    }
}
