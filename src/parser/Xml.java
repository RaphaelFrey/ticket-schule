package parser;

import ticket.Ticket;

import java.io.*;
import java.util.ArrayList;

public class Xml extends Parsers{

    @Override
    public ArrayList<ArrayList<String>> parse(File file) {

        ArrayList<ArrayList<String>> Tickets = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            ArrayList<String> Ticket = new ArrayList<>();
            while((line = bufferedReader.readLine()) != null){
                line = line.trim();
                if (!line.startsWith("<?") && !line.startsWith("<Tic") && !line.startsWith("</")){
                    if (line.startsWith("<A")){
                        Ticket.clear();
                        String[] split = line.split(">");
                        String[] split2 = split[1].split("<");
                        Ticket.add(split2[0]);
                    }
                    else {
                        String[] split = line.split(">");
                        String[] split2 = split[1].split("<");
                        Ticket.add(split2[0]);
                        if (Ticket.size() == 3){
                            Tickets.add(new ArrayList<>(Ticket));
                        }
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Tickets;
    }

    @Override
    public void write(Ticket[] ticketArray) {
        File file = new File("output.xml");
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            bufferedWriter.write("<Tickets>\n");
            for (Ticket ticket: ticketArray) {
                bufferedWriter.write("\t<Ticket>\n");
                bufferedWriter.write("\t\t<Owner>" + ticket.getOwner() + "</Owner>\n");
                bufferedWriter.write("\t\t<Title>" + ticket.getHeading() + "</Title>\n");
                bufferedWriter.write("\t\t<Description>" + ticket.getDescription() + "</Description>\n");
                bufferedWriter.write("\t\t<date>" + ticket.getTimestamp() + "</date>\n");
                bufferedWriter.write("\t\t<state>" + ticket.getTicketStatus() + "</state>\n");
                bufferedWriter.write("\t</Ticket>\n");
            }
            bufferedWriter.write("</Tickets>");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
