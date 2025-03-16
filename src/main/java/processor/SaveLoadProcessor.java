package processor;

import com.google.gson.Gson;
import model.Player;
import model.SaveState;
import model.enums.JobEnum;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SaveLoadProcessor {

    private final String SAVEFILE_PATH = "savefiles";

    private SaveState saveState;

    //Create savefile directory
    public SaveLoadProcessor(){
        new File(SAVEFILE_PATH).mkdirs();
    }

    public void saveGame(Player player, Integer actualScene) {
        System.out.println("Create a new save game or overwrite: ");

        List<String> files = listFiles();

        saveState = SaveState.builder()
                .actualScene(actualScene)
                .player(player)
                .build();

        //TODO: validar escolha
        short selectedOption = IOProcessor.readOption();

        //Validate option
        if(files.size() < selectedOption) {
            System.out.println("Invalid option!");
        } else if (selectedOption == 0) {
            System.out.println("Name your save file: ");
            String filename = IOProcessor.readFilename();

            saveToFile(filename);
        } else {
            System.out.println("Are you sure? (y/n)");
            if(IOProcessor.readYesOrNo()){
                saveToFile(files.get(selectedOption));
            }
        }

    }

    //Save game state
    public void saveToFile(String filename) {
        try {
            File saveFile = new File(buildSavePath(filename));

            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }

            FileWriter writer = new FileWriter(saveFile, false);
            writer.write(new Gson().toJson(saveState));
            writer.close();

            System.out.println("Saved successfully.");
        } catch (IOException exception) {
            System.out.println("Save failed.");
        }
    }

    //Load game state
    public void loadGame() {
        listFiles();
    }

    //List save files
    private List<String> listFiles() {
        File directory = new File(SAVEFILE_PATH);

        if (directory.exists() && directory.isDirectory()) {
            String[] filesArray = directory.list();

            if(filesArray != null) {
                List<String> files = new ArrayList<>(Arrays.asList(filesArray));
                Collections.sort(files);
                files.addFirst("Create new file");

                for (int i = 0; i < files.size(); i++) {
                    System.out.println((i) + " - " + files.get(i));
                }

                return files;
            } else {
                System.out.println("There is no save files!");
            }
        } else {
            System.out.println("Error listing save files!");
        }

        return new ArrayList<>();
    }

    private String buildSavePath(String filename) {
        String txtExtension = ".txt";

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy-HH-mm-ss"));

        return SAVEFILE_PATH + "/" + timestamp + " " + filename + txtExtension;
    }

    public static void main(String[] args) {
        Player player = new Player("Menino", 100, 20, JobEnum.MAGE, 100);

        new SaveLoadProcessor().saveGame(player, 2);
    }
}
