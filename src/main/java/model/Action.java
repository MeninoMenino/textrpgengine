package model;

import lombok.Getter;
import model.enums.OperationEnum;

@Getter
public class Action {

    OperationEnum operation;
    Integer amount;

}
