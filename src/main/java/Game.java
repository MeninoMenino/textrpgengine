import model.Option;
import model.Player;
import model.Scene;
import model.enums.JobEnum;
import processor.ActionProcessor;
import processor.SceneProcessor;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) {

        //---Processors---
        SceneProcessor sceneProcessor = new SceneProcessor();
        Map<Integer, Scene> scenes = sceneProcessor.processScenes();

        //Input scanner
        Scanner scanner = new Scanner(System.in);

        //Scene init
        Scene actualScene = scenes.get(0);
        //Player init
        Player player = new Player("Menino", 100, 20, JobEnum.WARRIOR, 100);

        while(actualScene != null) {
            System.out.println("| Name: " + player.getName() +
                    "  --  HP: " + player.getHp() +
                    "  --  MP: " + player.getMp() +
                    "  --  Class: " + player.getJob().getJobName() +
                    "  --  Gold: " + player.getGold() +
                    " |\n");

            System.out.println(actualScene.getDescription() + "\n");

            List<Option> options = actualScene.getOptions();

            int optCount = 1;
            for(Option option : options) {
                System.out.println(optCount + " - " + option.getDescription());
                optCount++;
            }

            short input = scanner.nextShort();

            if(options.size() < input) {
                System.out.println("Invalid option!");
                continue;
            }

            Option selectedOption = options.get(input - 1);
            Scene selectedScene = scenes.get(selectedOption.getDestinationScene());

            if(selectedOption.getAction() != null) {
                ActionProcessor.processAction(player, selectedOption.getAction());
            }

            actualScene = selectedScene;
        }

        System.out.println("Finish");

    }

}
