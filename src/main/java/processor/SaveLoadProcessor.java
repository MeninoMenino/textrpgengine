package processor;

import com.google.gson.Gson;
import model.Player;
import model.SaveState;
import model.enums.MessageEnum;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SaveLoadProcessor {

    private final String SAVEFILE_PATH = "savefiles/";

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

    private void saveToFile(String filename) {
        try {
            File saveFile = new File(buildPath(filename));

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

    //Load game state
    public SaveState loadGame() {
        IOProcessor.write(MessageEnum.CHOOSE_FILE);

        List<String> files = listFiles(null);

        //TODO: validar escolha
        short selectedOption = IOProcessor.readOption();

        //Validate option
        if(files.size() < selectedOption) {
            IOProcessor.write(MessageEnum.INVALID_OPTION);
        } else {
            IOProcessor.write(MessageEnum.ARE_YOU_SURE);
            if(IOProcessor.readYesOrNo()){
                String fileContent = loadFromFile(files.get(selectedOption));

                return new Gson().fromJson(fileContent, SaveState.class);
            }
        }

        return null;
    }

    private String loadFromFile(String selectedOption) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(SAVEFILE_PATH + selectedOption));
            return String.join("\n", lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //General functions

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

    private String buildPath(String filename) {
        String txtExtension = ".txt";

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy-HH-mm-ss"));

        return SAVEFILE_PATH + timestamp + " " + filename + txtExtension;
    }

    public static void main(String[] args) {
        //Player player = new Player("Menino", 100, 20, JobEnum.MAGE, 100);

        //new SaveLoadProcessor().saveGame(player, 2);

        SaveState stt = new SaveLoadProcessor().loadGame();

        System.out.println(stt.getPlayer().getName());
        System.out.println("Scene: " + stt.getActualScene());
    }
}
