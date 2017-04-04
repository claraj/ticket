package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Date;

/**
 * Created by DarthVader on 4/4/17.
 */
public class TicketGui extends JFrame{
    private JPanel rootPanel;
    private DefaultListModel<Ticket> listModel;
    private JButton addTicketButton;
    private JButton searchButton;
    private JTextField searchTxt;
    private JButton quitButton;
    private JComboBox priorityComboBox1;
    private JTextField reporterTxt;
    private JTextField issueTxt;
    private JList resultsList;
    private JButton resolveTicketButton;
    private JButton showAllTicketsButton;
    private JTextField resolutionTextField;
    private JButton showAllResolvedButton;
    private JComboBox ticketIdComboBox1;


    static LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>(); //list for tickets in queue

    static LinkedList<Ticket> resolvedTicket = new LinkedList<>(); //list of resolved tickets


    //Writes any tickets that were open again and reads any open tickets into the ticket queue list

    BufferedWriter openWriter = new BufferedWriter(new FileWriter("open_tickets.txt",true));

    Scanner fileInput = new Scanner(new File("open_tickets.txt"));

    String dateForm = "EEE MMM dd hh:mm:ss z yyyy";
    SimpleDateFormat format = new SimpleDateFormat(dateForm);


    public TicketGui() throws Exception {

        super("Ticket Manager");

        try {
            while (fileInput.hasNext()) {
                Ticket openTicket = new Ticket(fileInput.nextLine(),Integer.parseInt(fileInput.nextLine().substring(10)),
                        fileInput.nextLine(),
                        format.parse(fileInput.nextLine().substring(15)),
                        null,
                        null);
                ticketQueue.add(openTicket);
            }
        }catch
                (NumberFormatException ex) {
            System.out.println("Error");
        }

        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        listModel = new DefaultListModel<Ticket>();
        resultsList.setModel(listModel);


        for (Ticket t : ticketQueue){
            listModel.addElement(t);
            ticketIdComboBox1.addItem(t.getTicketID());
        }


        listeners();
        pack();
        setSize(900,400);
        setVisible(true);

        priorityComboBox1.addItem(1);
        priorityComboBox1.addItem(2);
        priorityComboBox1.addItem(3);
        priorityComboBox1.addItem(4);
        priorityComboBox1.addItem(5);
    }

    public static int getCurrentId(){
        int id = 1;

        for (Ticket ticket : ticketQueue){
            if (ticket.getTicketID() > id){
                id = ticket.getTicketID();
            }
        }

        id += 1;
        return id;
    }


    public void listeners(){

        addTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String reporter = reporterTxt.getText();
                String issue = issueTxt.getText();
                int priority = (Integer)priorityComboBox1.getSelectedItem();
                Date date = new Date();

                Ticket ticket = new Ticket(issue, priority, reporter, date, null, null);
                priorityOrder(ticketQueue, ticket);
                listModel.addElement(ticket);
                ticketIdComboBox1.addItem(ticket.getTicketID());
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                listModel.clear();
                String search = searchTxt.getText();

                for (Ticket ticket : ticketQueue) {
                    if (ticket.getDescription().equalsIgnoreCase(search)){
                        listModel.addElement(ticket);
                    }
                }
            }
        });

        showAllTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                listModel.clear();

                for (Ticket ticket : ticketQueue){
                    listModel.addElement(ticket);
                }
            }
        });

        showAllResolvedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                listModel.clear();

                for (Ticket ticket : resolvedTicket){
                    listModel.addElement(ticket);
                }
            }
        });

        resolveTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id = (Integer)ticketIdComboBox1.getSelectedItem();
                String resolution = resolutionTextField.getText();
                Date today = new Date();

                for (Ticket ticket : ticketQueue){
                    if (ticket.getTicketID() == id){
                        ticket.setFixDescription(resolution);
                        ticket.setDateResolved(today);
                        resolvedTicket.add(ticket);
                        ticketQueue.remove(ticket);
                        listModel.removeElement(ticket);
                        ticketIdComboBox1.removeItem(ticket.ticketID);
                        break;
                    }
                }
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

    try {
        BufferedWriter quitWriter = new BufferedWriter(new FileWriter("open_tickets.txt"));


        for (Ticket t : ticketQueue) { //for each ticket in the ticketqueue list it writes its info in this format
            quitWriter.write(t.getDescription() + "\nPriority: " + t.getPriority() + "\n" +
                    t.getReporter() + "\nDate reported: " + t.getDateReported() + "\n");
        }

        String date = new SimpleDateFormat("MMM_dd_yyyy").format(new Date());

        String closedTix = "Resolved_tix_as_of_" + date + ".txt"; //creating a resolved txt file

        BufferedWriter resolvedWriter = new BufferedWriter(new FileWriter(closedTix));

        for (Ticket t : resolvedTicket) { //similar to the open ticket txt file but with resolved tickets
            resolvedWriter.write("Issue: " + t.getDescription() + "\nPriority: " + t.getPriority() + "\nReporter: " +
                    t.getReporter() + "\nDate reported: " + t.getDateReported() + "\nDate resolved: " +
                    t.getDateResolved() + "\nResolution: " + t.getFixDescription() + "\n");
        }

        quitWriter.close();
        resolvedWriter.close();
    }catch(IOException ioException){
        JOptionPane.showMessageDialog(TicketGui.this, "Error updating open_tickets.txt" + "Please try again." );
    }
    System.exit(0);
                }
            });
        }


    public void priorityOrder(LinkedList<Ticket> tickets, Ticket ticket){


        int tixPriority = ticket.getPriority();

        for (int x = 0; x < tickets.size(); x++){

            if (tixPriority >= tickets.get(x).getPriority()){
                tickets.add(x, ticket);
                return;
            }
        }

        tickets.addLast(ticket);

    }
}
