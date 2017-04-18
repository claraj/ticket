package com.happy;

import java.util.Date;

public class Ticket {

    private int priority;
    private String reporter; //Stores person or department who reported issue
    private String description;
    private Date dateReported;
    private String resolution;
    private Date resolutionDate;




    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {

        this.resolution = resolution;
    }

    public Date getResolutionDate() {

        return resolutionDate;
    }

    public void setResolutionDate(Date resolutionDate) {

        this.resolutionDate = resolutionDate;
    }


    //TODO Problem 1: explain the role of ticketIdCounter and ticketID

    //STATIC Counter - one variable, shared by all Ticket objects.
    //If any Ticket object modifies this counter, all Ticket objects will have the modified value
    //Make it private - only Ticket objects should have access
    private static int staticTicketIDCounter = 1;

    //The ID for each ticket - an instance variable. Each Ticket will have it's own ticketID variable
    protected int ticketID;

    // TODO problem 6: tickets need to store the resolution date and a string describing the resolution
    // Either add them to this class or create another class called ResolvedTicket - which
    // do you think is the better approach?



    public Ticket(String desc, int p, String rep, Date date) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.ticketID = staticTicketIDCounter;
        staticTicketIDCounter++;
    }

    // constructor  to change ticket id by not reset 1 when the user restart program

    public Ticket(String desc, int p, String rep, Date date, int id) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;

        staticTicketIDCounter = id + 1;
        this.ticketID = id;
    }



    protected int getPriority() {
        return priority;
    }
    public String getDescription(){
        return  description;
    }


    public int getTicketID() {

        return ticketID;
    }

    public String toString() {
        String resolutions = (resolutionDate == null) ? "unresolved":
                this.resolutionDate.toString();
        String resolution = (this.resolution ==null) ? "unsolved":
                this.resolution.toString();
        return ("ID= " + this.ticketID + " Issued: " + this.description + " Priority: "
                + this.priority + " Reported by: "
                + this.reporter + " Reported on: " + this.dateReported + " Resolution: " + resolution
                + " Resolved Date: " + resolution);
    }



    // public String toString(){
   //     return("ID: " + this.ticketID + " Issue: " + this.description + " Priority: " + 					this.priority + " Reported by: "
     //           + this.reporter + " Reported on: " + this.dateReported);
    //}
}

