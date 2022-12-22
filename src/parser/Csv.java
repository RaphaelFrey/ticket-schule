package parser;

import ticket.Ticket;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Csv extends Parsers {

    @Override
    public ArrayList<ArrayList<String>> parse(File file) {

        ArrayList<ArrayList<String>> tickets = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            int count = 0;
            while (line != null) {
                if (count == 0) {
                    count++;
                    line = bufferedReader.readLine();
                    continue;
                }
                ArrayList<String> ticket = new ArrayList<>(Arrays.asList(line.split(",")));
                for (String t : ticket) {
                    t = t.trim();
                }
                tickets.add(ticket);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tickets;
    }
    @Override
    public void write(Ticket[] tickets) {

        File neueTickets = new File("newTickets.txt");
        try {
            FileWriter myWriter = new FileWriter(neueTickets);
            String kopf = "autor, titel, beschreibung, datum, status";
            myWriter.write(kopf);
            for (Ticket ticket: tickets) {
                String line = ticket.getOwner() + ","
                        + ticket.getHeading() + ","
                        + ticket.getDescription() + ","
                        + ticket.getTimestamp() + ","
                        + ticket.getTicketStatus();
                myWriter.write(System.lineSeparator());
                myWriter.write(line);
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
