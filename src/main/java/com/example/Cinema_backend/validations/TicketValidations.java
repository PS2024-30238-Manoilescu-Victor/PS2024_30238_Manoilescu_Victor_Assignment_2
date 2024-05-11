package com.example.Cinema_backend.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicketValidations {

    public static boolean nonNegative(int numar)
    {
        return numar>=0;
    }
    public static boolean nonNegative(Float numar)
    {
        return numar>=0;
    }
    public static boolean validareRating(int rating)
    {
        return (rating >=1 && rating <= 10);
    }
    public static boolean validareProcent(int percentage)
    {
        return (percentage >=0 && percentage <= 100);
    }
    public static boolean validareData(String data)
    {
        Pattern pattern = Pattern.compile("([0-3]?[0-9]/[0-1][0-9]/[0-9]+)|([0-3]?[0-9]:[0-1][0-9]:[0-9]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }

    public static boolean validareOra(String ora)
    {
        Pattern pattern = Pattern.compile("[0-2][0-9]:[0-5][0-9]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ora);
        return matcher.matches();
    }

}
