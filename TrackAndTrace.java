import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TrackAndTrace {

    public static Scanner input = new Scanner(System.in);
    public static int numberOfTests = 0;

    public static void main(String[] args) {     
        // Ask user for infected person's arrival and departure times   
        System.out.println("Enter the start time:");
        int infStart = input.nextInt();
        System.out.println("Enter the end time:");
        int infEnd = input.nextInt();
        //Check if user has inputted any filenames
        if (args.length > 0) {
            System.out.println("There are "+readFiles(infStart, infEnd, args)+" tests needed.");
        } else
            System.out.println("There are "+getOverlaps(infStart, infEnd)+" tests needed.");
    }

    public static boolean overlap(int infStart, int infEnd, int customerStart, int customerEnd) {
        //if any time periods cross midnight, take away 7 (opening time for restaurants)
        if (infStart > infEnd || customerStart > customerEnd){
            infStart -= 7; infEnd -= 7; customerStart -= 7; customerEnd -= 7;
            //if a value is less than 7, add 24 to it so that all end values > start values
            if (infEnd <= 7) {infEnd += 24;}
            if (customerEnd <= 7) {customerEnd += 24;}}
        return ((infStart < customerEnd) && (customerStart < infEnd)) ? true : false;
    }

    public static int getOverlaps(int infStart, int infEnd) {
        System.out.println("Enter the number of customers:");
        int customerNum = input.nextInt();
        //loops for every customer
        for (int i=1; i<=customerNum; i++) {
            System.out.println("Enter the customer's name:");
            String customerName = input.next();
            System.out.println("Enter the arrival time:");
            int start = input.nextInt();
            System.out.println("Enter the departure time:");
            int end = input.nextInt();
            if (overlap(infStart, infEnd, start, end)) {
                System.out.println(customerName+" needs a test.");
                numberOfTests++;
            } else {
                System.out.println(customerName+" does not need a test.");
            }
        }
        return numberOfTests;
    }

    public static int readFiles(int infStart, int infEnd, String[] args) {
        for (String fileName: args) {
            try {
                File file = new File(fileName);
                Scanner fileInput = new Scanner(file);
                readFile(file, fileInput, infStart, infEnd);
            }
            catch (FileNotFoundException e) {
                // Error statement if file not found
                System.err.println("WARNING: "+fileName+" not found."); 
            }
        }
        return numberOfTests;
    }

    public static void readFile (File file, Scanner fileInput, int infStart, int infEnd) {
        //Read the file and check for overlaps
        while (fileInput.hasNext()){
            String customerName = fileInput.next();
            int start = fileInput.nextInt();
            int end = fileInput.nextInt();
            if (overlap(infStart, infEnd, start, end)){
                numberOfTests++;
                System.out.println(customerName+" needs a test.");
            }
        }
    }
}
