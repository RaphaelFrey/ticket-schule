package ticket;

import java.text.SimpleDateFormat;

public class Ticket {

    private String owner;
    private String heading;
    private String description;
    private TicketStatus ticketStatus;
    private final String timestamp;

    public Ticket(String owner, String heading, String description) {
        this.owner = owner;
        this.heading = heading;
        this.description = description;
        this.timestamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new java.util.Date());
        this.ticketStatus = TicketStatus.Open;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "owner='" + owner + '\'' +
                ", heading='" + heading + '\'' +
                ", description='" + description + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", ticketStatus='" + ticketStatus + '\'' +
                '}';
    }
}
