package model;

import model.enums.JobEnum;
import processor.ActionProcessor;
import processor.IOProcessor;
import processor.SaveLoadProcessor;
import processor.SceneProcessor;

import java.util.List;
import java.util.Map;

public class Game {

    //---Processors---
    SaveLoadProcessor saveLoadProcessor = new SaveLoadProcessor();
    SceneProcessor sceneProcessor = new SceneProcessor();
    Map<Integer, Scene> scenes = sceneProcessor.processScenes();

    //Player
    Player player;

    public Game() {
        //Scene init
        Scene actualScene = scenes.get(0);
        //Player init
        player = new Player("Menino", 100, 20, JobEnum.WARRIOR, 100);

        while(actualScene != null) {
            //Player stats (at the top of every scene)
            printPlayerStats();

            System.out.println(actualScene.getDescription() + "\n");

            List<Option> options = actualScene.getOptions();

            //Show scene options
            printOptions(options);

            //Get player choice
            short input = IOProcessor.readOption();

            //Validate option
            if(options.size() < input) {
                System.out.println("Invalid option!");
                continue;
            }

            //Process selected scene
            Option selectedOption = options.get(input - 1);

            if(selectedOption.getAction() != null) {
                ActionProcessor.processAction(player, selectedOption.getAction());
            }

            actualScene = scenes.get(selectedOption.getDestinationScene());

            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

        System.out.println("Finish");

    }

    private void printPlayerStats() {
        System.out.println("| Name: " + player.getName() +
                "  --  HP: " + player.getHp() +
                "  --  MP: " + player.getMp() +
                "  --  Class: " + player.getJob().getJobName() +
                "  --  Gold: " + player.getGold() +
                " |\n");
    }

    private void printOptions(List<Option> options) {
        int optCount = 1;
        for(Option option : options) {
            System.out.println(optCount + " - " + option.getDescription());
            optCount++;
        }
    }

}
