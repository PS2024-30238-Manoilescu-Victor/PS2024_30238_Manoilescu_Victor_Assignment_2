package com.example.Cinema_backend.constants;

public class ConstantsPerson {

    public static String wrongEmail(){return "Wrong email format inserted.";}
    public static String wrongNrTelefon(){return "Invalid phone number inserted.";}
    public static String wrongPassLen(){return "Password must be between 5 and 20 characters.";}
    public static String wrongPassChar(){return "Invalid characters used in password.";}
    public static String wrongName(){return "Name contains invalid characters.";}
    public static String wrongSurname(){return "Surname contains invalid characters.";}
    public static String nonexistentPerson(Long id){return "The person with id \"" + id + "\" doesn't exist!";}

    public static String nonexistentOrder(Long id){return "The order with id \"" + id + "\" doesn't exist!";}

    public static String nonexistentCart(){return "There is no existing cart";}

}
