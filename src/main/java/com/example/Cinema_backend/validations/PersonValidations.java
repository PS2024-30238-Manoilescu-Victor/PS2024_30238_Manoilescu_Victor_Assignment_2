package com.example.Cinema_backend.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonValidations {


    public static boolean validareEmail(String email)
    {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]+@[a-zA-Z]+(.ro|.com|.org)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static int validareParola(String parola)
    {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9,. ]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(parola);
        if (parola.length() < 5 || parola.length() > 20)
            return 1;
        if (matcher.matches())
            return 0;
        else
            return 2;
    }

    public static boolean validareNume(String nume)
    {
        Pattern pattern = Pattern.compile("[a-zA-Z -]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(nume);
        return matcher.matches();
    }

    public static boolean validareNrTelefon(String nrTelefon)
    {
        Pattern pattern = Pattern.compile("[0-9]{10}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(nrTelefon);
        return matcher.matches();
    }

}
