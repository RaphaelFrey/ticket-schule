package parser;

import ticket.Ticket;

import java.io.File;
import java.util.ArrayList;

public abstract class Parsers {

    public abstract ArrayList<ArrayList<String>> parse(File file);
    public abstract void write(Ticket[] tickets);
}
