package processor;

import java.io.File;
import java.io.IOException;

public class SaveLoadProcessor {

    private final String SAVEFILE_PATH = "savefiles";

    //Create savefile directory
    public SaveLoadProcessor(){
        new File(SAVEFILE_PATH).mkdirs();
    }

    //Save actual state
    public void save(String filename) {
        try {
            File saveFile = new File(buildSavePath(filename));
            saveFile.createNewFile();

            System.out.println("Saved successfully.");
        } catch (IOException e) {
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

        // Verificar se o caminho é uma pasta
        if (directory.exists() && directory.isDirectory()) {
            // Listar os arquivos da pasta
            String[] files = directory.list();

            // Se houver arquivos, imprimir cada um deles
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
