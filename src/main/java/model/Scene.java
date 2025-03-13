package model;

import lombok.Getter;

import java.util.List;

@Getter
public class Scene {

    private Integer id;
    private String description;
    private List<Option> options;

}
