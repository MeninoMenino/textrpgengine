package model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageEnum {

    //Option
    ARE_YOU_SURE("Are you sure? (y/n)"),
    INVALID_OPTION("Invalid option!"),

    //Save and Load
    CHOOSE_SAVE("Create a new save game or overwrite: "),
    NAME_SAVE_FILE("Name your save file: "),
    SAVED_SUCCESSFULLY("Saved successfully."),
    SAVE_FAILED("Save failed."),
    NO_SAVE_FILES("There are no save files!"),
    ERROR_LISTING_SAVE_FILES("Error listing save files!");

    private final String message;

}
