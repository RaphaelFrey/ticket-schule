package parser;

import ticket.Ticket;

import java.io.*;
import java.util.ArrayList;

public class Json extends Parsers {

    /**
     * @param file to parse into ArrayList of ArrayLists which represents ticket arrays.
     * @return an ArrayList full of ArrayList<String> which represents a temporary Ticket
     */
    @Override
    public ArrayList<ArrayList<String>> parse(File file) {

        ArrayList<ArrayList<String>> ticketList = new ArrayList<>();
        ArrayList<String> ticketArray = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            int count = 1;
            while (line != null) {
                if (line.contains("\"tickets\":")) {
                    line = reader.readLine();
                    continue;
                }
                if (line.contains("[")) {
                    line = line.replace("[", "");
                }
                if (line.contains("]")) {
                    line = line.replace("]", "");
                }
                if (line.contains("{")) {
                    line = line.replace("{", "");
                }
                if (line.contains("}")) {
                    line = line.replace("}", "");
                }
                line = line.trim();
                if (line.isBlank()) {
                    line = reader.readLine();
                    continue;
                }
                if (line.contains(":")) {
                    String[] strings = line.split(":");
                    String string = strings[1];
                    string = string.replace("\"", "");
                    string = string.trim();
                    if (string.endsWith(",")) {
                        StringBuilder sb= new StringBuilder(string);
                        sb.deleteCharAt(sb.length() -1);
                        string = sb.toString();
                    }

                    ticketArray.add(string);
                    if (count%3 == 0) {
                        ticketList.add(ticketArray);
                        ticketArray = new ArrayList<>();
                    }
                    count++;
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ticketList;
    }

    /**
     *
     * @param tickets - An Array of Tickets. Each will be printed to the same JSON file
     * @throws IOException
     */
    @Override
    public void write(Ticket[] tickets) {
        File file = new File("ticket.json");
        StringBuilder stringBuilder = new StringBuilder("{\n  \"tickets\": [");
        int i = 0;
        for (Ticket ticket : tickets) {
            stringBuilder.append("""

                      {
                        "name": "\
                    """).append(ticket.getOwner())
                    .append("\",").append("\n")
                    .append("    \"heading\":")
                    .append(" ").append("\"")
                    .append(ticket.getHeading())
                    .append("\",").append("\n")
                    .append("    \"description\":")
                    .append(" ").append("\"")
                    .append(ticket.getDescription())
                    .append("\",").append("\n")
                    .append("    \"date\":")
                    .append(" ").append("\"")
                    .append(ticket.getTimestamp())
                    .append("\",").append("\n")
                    .append("    \"status\":")
                    .append(" ").append("\"")
                    .append(ticket.getTicketStatus())
                    .append("\"").append("\n")
                    .append("  }");
            if (i != tickets.length -1) {
                stringBuilder.append(",");
            }
            i++;
        }
        stringBuilder.append("\n ]\n}");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
