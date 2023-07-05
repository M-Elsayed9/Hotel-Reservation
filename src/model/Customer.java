package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastname;
    private final String email;
    private static final String emailRegEx = "^[^@]*@[^@]*\\.com$";
    private static final Pattern pattern = Pattern.compile(emailRegEx);

    public Customer(String firstName, String lastname, String email) {
        if(!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Error, Invalid email");
        }
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return firstName + " " + lastname;
    }
    @Override
    public String toString() {
        return "First Name: " + firstName + "\nLast Name " + lastname +
                "\nEmail: " + email + "\n";
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
