package com.example.Cinema_backend.constants;

public class ConstantsTicket {

    public static String nonexistentTicket(Long id){return "The ticket with id \"" + id + "\" doesn't exist!";}
    public static String negNrTickets(){return "Cannot have a negative number of tickets.";}
    public static String negPrice(){return "Cannot have a negative price.";}
    public static String wrongData(){return "Wrong data format.";}
    public static String wrongOra(){return "Wrong hour format.";}
    public static String wrongRating(){return "Rating should be between 1 and 10";}
    public static String noMoreTickets(){return "No more tickets left";}

}
