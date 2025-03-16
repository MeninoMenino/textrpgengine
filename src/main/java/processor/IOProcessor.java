package processor;

import java.util.Arrays;
import java.util.Scanner;

public class IOProcessor {

    //Input scanner
    static Scanner scanner = new Scanner(System.in);

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
