package manager;

import java.util.Scanner;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class Main {

    private static String features[] = {"todo"};
    public static void main(String[] args) {
        boolean error = false;
        Scanner input = new Scanner(System.in);
        String cmd;
        while(!error){
            while ((cmd = input.nextLine().replaceAll("\\s+","")).compareTo("") != 0) {
                String argv[] = cmd.split("\\s+");
                if (argv.length <= 0){
                    error = true;
                    System.out.println("wtf?");
                }
                else{
                    int i = 0;
                    while(features[i].toLowerCase().compareTo(argv[i].toLowerCase()) != 0){
                        i++;
                    }
                    if (i >= features.length){
                        error = true;
                        System.out.println("feature not found");
                    }
                    else{

                    }
                }
            }

        }
    }
    public static void printError(String error){
        System.out.println(error);
    }
}
