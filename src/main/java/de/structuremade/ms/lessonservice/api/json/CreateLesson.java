package de.structuremade.ms.lessonservice.api.json;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import java.util.List;

@Getter
@Setter
public class CreateLesson {
    @NotNull
    private List<String> times;
    @NotNull
    private String lesson;
    @NotNull
    private int settings;
    @NotNull
    private String day;
    @NotNull
    private String room;
}
