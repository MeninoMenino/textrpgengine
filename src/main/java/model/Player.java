package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.enums.JobEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    String name;
    Integer hp;
    Integer mp;
    JobEnum job;
    Integer gold;

}