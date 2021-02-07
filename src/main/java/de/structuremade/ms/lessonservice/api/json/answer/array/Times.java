package de.structuremade.ms.lessonservice.api.json.answer.array;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class Times {
    private String id;
    private Time begin;
    private Time end;
}

