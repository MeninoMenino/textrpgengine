package processor;

import com.google.gson.Gson;
import model.Player;
import model.SaveState;
import model.enums.JobEnum;
import model.enums.MessageEnum;

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

    //Save game state
    public void saveGame(Player player, Integer actualScene) {
        IOProcessor.write(MessageEnum.CREATE_SAVE_OR_OVERWRITE);

        List<String> files = listFiles(MessageEnum.SAVE_OPTION.getMessage());

        saveState = SaveState.builder()
                .actualScene(actualScene)
                .player(player)
                .build();

        //TODO: validar escolha
        short selectedOption = IOProcessor.readOption();

        //Validate option
        if(files.size() < selectedOption) {
            IOProcessor.write(MessageEnum.INVALID_OPTION);
        } else if (selectedOption == 0) {
            IOProcessor.write(MessageEnum.NAME_SAVE_FILE);
            String filename = IOProcessor.readFilename();

            saveToFile(filename);
        } else {
            IOProcessor.write(MessageEnum.ARE_YOU_SURE);
            if(IOProcessor.readYesOrNo()){
                saveToFile(files.get(selectedOption));
            }
        }

    }

    //List save files
    private List<String> listFiles(String optionZero) {
        File directory = new File(SAVEFILE_PATH);

        if (directory.exists() && directory.isDirectory()) {
            String[] filesArray = directory.list();

            if(filesArray != null) {
                List<String> files = new ArrayList<>(Arrays.asList(filesArray));

                if(optionZero != null && !optionZero.isEmpty()) {
                    Collections.sort(files);
                    files.addFirst(optionZero);
                }

                for (int i = 0; i < files.size(); i++) {
                    IOProcessor.write((i) + " - " + files.get(i));
                }

                return files;
            } else {
                IOProcessor.write(MessageEnum.NO_SAVE_FILES);
            }
        } else {
            IOProcessor.write(MessageEnum.ERROR_LISTING_SAVE_FILES);
        }

        return new ArrayList<>();
    }

    private void saveToFile(String filename) {
        try {
            File saveFile = new File(buildSavePath(filename));

            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }

            FileWriter writer = new FileWriter(saveFile, false);
            writer.write(new Gson().toJson(saveState));
            writer.close();

            IOProcessor.write(MessageEnum.SAVED_SUCCESSFULLY);
        } catch (IOException exception) {
            IOProcessor.write(MessageEnum.SAVE_FAILED);
        }
    }

    private String buildSavePath(String filename) {
        String txtExtension = ".txt";

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy-HH-mm-ss"));

        return SAVEFILE_PATH + "/" + timestamp + " " + filename + txtExtension;
    }

    //Load game state
    public void loadGame() {
        IOProcessor.write(MessageEnum.CHOOSE_FILE);

        List<String> files = listFiles(null);

        IOProcessor.readOption();
    }

    public static void main(String[] args) {
        //Player player = new Player("Menino", 100, 20, JobEnum.MAGE, 100);

        //new SaveLoadProcessor().saveGame(player, 2);

        new SaveLoadProcessor().loadGame();
    }
}
