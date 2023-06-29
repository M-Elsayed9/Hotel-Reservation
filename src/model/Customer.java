package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastname;
    private String email;
    private final String emailRegEx = "^[^@]*@[^@]*\\.com$";
    private final Pattern pattern = Pattern.compile(emailRegEx);

    public Customer(String firstName, String lastname, String email) {
        if(!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Error, Invalid email");
        }
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastname +
                "\nEmail: " + email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, email);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Customer) {
            Customer other = (Customer) obj;
            return this.firstName.equals(other.firstName) &&
                    this.lastname.equals(other.lastname) &&
                    this.email.equals(other.email);
        }else {
            return false;
        }
    }

}
