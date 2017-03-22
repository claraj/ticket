package com.company;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;

public class Ticket {

    private int priority;
    private String reporter; //Stores person or department who reported issue
    private String description;
    private Date dateReported;
    private Date dateResolved;
    private String fixDescription;

    //TODO Problem 1: explain the role of ticketIdCounter and ticketID

    //STATIC Counter - one variable, shared by all Ticket objects.
    //If any Ticket object modifies this counter, all Ticket objects will have the modified value
    //Make it private - only Ticket objects should have access
    private static int ticketIdCounter = 1;

    protected static void ReadID(){
        try{
            ticketIdCounter = ReadIDCounter();
        }
        catch (Exception Ex){
            System.out.println("Error");
        }
    }

    protected static void WriteID(){
        try{
            WriteIDCounter();
        }
        catch (Exception Ex){
            System.out.println("Error");
        }
    }

    protected static int ReadIDCounter() throws Exception{
        int countNum = 1;
        FileReader input = new FileReader("current_ticket.txt");
        countNum = input.read();
        input.close();
        return countNum;
    }

    protected static void WriteIDCounter() throws Exception{
        FileWriter output = new FileWriter("current_ticket.txt");
        output.write(ticketIdCounter);
        output.close();
    }

    //The ID for each ticket - an instance variable. Each Ticket will have it's own ticketID variable
    protected int ticketID;

    // TODO problem 6: tickets need to store the resolution date and a string describing the resolution
    // Either add them to this class or create another class called ResolvedTicket - which
    // do you think is the better approach?

    public Ticket(String desc, int p, String rep, Date date, String fix, Date resDate) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.dateResolved = resDate;
        this.fixDescription = fix;
        this.ticketID = ticketIdCounter;
        ticketIdCounter++;
    }
    public String getDescription() {
        return description;
    }

    protected int getPriority() {
        return priority;
    }

    public int getTicketID() {
        return ticketID;
    }

    public Date getDateResolved() {return dateResolved;}

    public String getFixDescription() {
        return fixDescription;
    }

    public String getReporter() {
        return reporter;
    }

    public Date getDateReported() {
        return dateReported;
    }

    public void setDateResolved(Date dateResolved) {
        this.dateResolved = dateResolved;
    }

    public void setFixDescription(String fixDescription) {
        this.fixDescription = fixDescription;
    }

    public String toString(){
        return("ID: " + this.ticketID + " Issue: " + this.description + " Priority: " + 					this.priority + " Reported by: "
                + this.reporter + " Reported on: " + this.dateReported + " Resolved on: " +
        this.dateResolved + " Resolution: " + this.fixDescription);
    }
}

