import parser.Csv;
import parser.Json;
import parser.Xml;
import ticket.Ticket;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TicketApp {
    private static final Json json = new Json();
    private static final Xml xml = new Xml();
    private static final Csv csv = new Csv();
    private static Ticket[] tickets;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print(getTimestamp() + "Enter your username (admin) $: ");
        String input = scanner.nextLine();
        String username = input.equals("") ? "admin" : input;
        while(true) {
            System.out.print(getPrefix(username) + "Do you want to add (a = default) a ticket, read ticket/s from a file (r), " +
                    "print all tickets (p) or quit the application (q)? : ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("r")) {
                readTicketFromFile(username, scanner);

            } else if (input.equalsIgnoreCase("p")) {
                if (tickets != null ) {
                    System.out.print(getPrefix(username) + "Choose a File Format to print to. (j, t = default, x): ");
                    input = scanner.nextLine();
                    switch (input) {
                        case "j" -> json.write(tickets);
                        case "x" -> xml.write(tickets);
                        default -> csv.write(tickets);
                    }
                } else {
                    System.out.println("No Tickets in Database");
                }
            } else if (input.equalsIgnoreCase("q")) {
                break;
            } else {
                System.out.print("Enter a Ticket Heading: ");
                String heading = scanner.nextLine().trim();
                System.out.print("Enter a Ticket Description: ");
                String description = scanner.nextLine().trim();
                if (heading.isBlank() || description.isBlank()) {
                    System.out.println("You either entered invalid Heading or Description");
                } else {
                    Ticket ticket = new Ticket(username, heading, description);
                    if (tickets != null && tickets.length > 0) {
                        Ticket[] tempTickets = tickets.clone();
                        tickets = new Ticket[tempTickets.length + 1];
                        int i = 0;
                        for (Ticket t : tempTickets) {
                            tickets[i] = t;
                            i++;
                        }
                        tickets[i] = ticket;
                    } else {
                        tickets = new Ticket[1];
                        tickets[0] = ticket;
                    }
                }
            }
        }
    }

    private static boolean checkFile(String username, File file) {
        if (!file.isFile()) {
            System.out.println(getPrefix(username) + "You entered an invalid Path or Extension.");
            return false;
        }
        return true;
    }

    public static ArrayList<ArrayList<String>> parseTicket(File file) {
        ArrayList<ArrayList<String>> ticketsList = new ArrayList<>();
        String extension = getExtensionByStringHandling(file.getName()).orElse("");
        switch (extension) {
            case "json" -> ticketsList = json.parse(file);
            case "xml" -> ticketsList = xml.parse(file);
            case "txt" -> ticketsList = csv.parse(file);
            default -> {
            }
        }
        return ticketsList;
    }

    private static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private static String getPrefix(String username) {
        return getTimestamp() + "(" +  username + ") ";
    }

    private static String getTimestamp() {
        return new SimpleDateFormat("[HH:mm:ss] ").format(new java.util.Date());
    }

    private static void readTicketFromFile(String username, Scanner scanner) {
        boolean hasFile = false;
        File file = new File("");
        while (!hasFile) {
            System.out.print(getPrefix(username) + "Enter File with absolut Path and Extension: ");
            String input = scanner.nextLine();
            file = new File(input);
            hasFile = checkFile(username, file);
        }
        ArrayList<ArrayList<String>> ticketStrings = parseTicket(file);
        int i = 0;
        if (tickets != null && tickets.length > 0) {
            Ticket[] tempTickets = tickets.clone();
            tickets = new Ticket[tempTickets.length + ticketStrings.size()];
            for (Ticket ticket : tempTickets) {
                tickets[i] = ticket;
                i++;
            }
        }
        else {
            tickets = new Ticket[ticketStrings.size()];
        }
        for (ArrayList<String> ticketArray: ticketStrings) {
            tickets[i] = new Ticket(ticketArray.get(0), ticketArray.get(1), ticketArray.get(2));
            i++;
        }
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }
 }