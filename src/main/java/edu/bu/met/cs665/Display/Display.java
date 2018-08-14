package edu.bu.met.cs665.Display;

///just a class to fake that we could be displaying to anywhere.  For this assignment being a console app it is just going to throw whatever string to stdout
public class Display {
    //allows a standard seperator text for each outputWithSeparator
    private static final String SEPARATOR_TEXT = "\n***************************************\n";

    //synched to prevent overlapping outputWithSeparator
    //could be changed to allow outputWithSeparator anywhere
    public synchronized static void outputWithSeparator(String stringToDisplay) {
        System.out.println(SEPARATOR_TEXT + stringToDisplay + SEPARATOR_TEXT);
    }

    public synchronized static void output (String stringToDisplay){
        System.out.println(stringToDisplay);
    }
}
