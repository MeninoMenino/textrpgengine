package model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JobEnum {

    WARRIOR("Warrior"),
    MAGE("Mage"),
    THIEF("Thief");

    private final String jobName;

}
