package de.structuremade.ms.lessonservice.api.json.answer.array;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lessons {
    private String id;
    private String name;
    private Teacher teacher;
}
