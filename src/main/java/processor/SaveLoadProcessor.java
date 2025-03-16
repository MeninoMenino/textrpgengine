package processor;

import com.google.gson.Gson;
import lombok.Setter;
import model.SaveState;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveLoadProcessor {

    private final String SAVEFILE_PATH = "savefiles";

    @Setter
    private SaveState saveState;

    //Create savefile directory
    public SaveLoadProcessor(){
        new File(SAVEFILE_PATH).mkdirs();
    }

    //Save game state
    public void save(String filename) {
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
    public void load() {
        listFiles();
    }

    //List save files
    private void listFiles() {
        File directory = new File(SAVEFILE_PATH);

        if (directory.exists() && directory.isDirectory()) {
            String[] files = directory.list();

            if (files != null) {
                for (String file : files) {
                    System.out.println(file);
                }
            } else {
                System.out.println("There is no save files!");
            }
        } else {
            System.out.println("Error listing save files!");
        }
    }

    private String buildSavePath(String filename) {
        String txtExtension = ".txt";

        return SAVEFILE_PATH + "/" + filename + txtExtension;
    }

}
