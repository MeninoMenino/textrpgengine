package processor;

import model.Option;
import model.Player;
import model.enums.MessageEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class IOProcessor {

    //Input scanner
    static Scanner scanner = new Scanner(System.in);

    //Write
    public static void write(MessageEnum message) {
        System.out.println(message.getMessage());
    }

    public static void write(String message) {
        System.out.println(message);
    }

    public static void printPlayerStats(Player player) {
        System.out.println("| Name: " + player.getName() +
                "  --  HP: " + player.getHp() +
                "  --  MP: " + player.getMp() +
                "  --  Class: " + player.getJob().getJobName() +
                "  --  Gold: " + player.getGold() +
                " |\n");
    }

    public static void printOptions(List<Option> options) {
        int optCount = 1;
        for(Option option : options) {
            System.out.println(optCount + " - " + option.getDescription());
            optCount++;
        }
    }

    //Read
    public static short readOption() {
        return scanner.nextShort();
    }

    public static String readFilename() {
        return scanner.next();
    }

    public static Boolean readYesOrNo() {
        Boolean yesOrNo = null;
        String answer;

        while(yesOrNo == null) {
            answer = scanner.next();

            if(Arrays.asList("y", "Y").contains(answer)) {
                yesOrNo = true;
            } else if(Arrays.asList("n", "N").contains(answer)){
                yesOrNo = false;
            } else {
                System.out.println("Invalid option!");
            }
        }

        return yesOrNo;
    }

}
