package de.structuremade.ms.lessonservice.api.json.answer;

import de.structuremade.ms.lessonservice.api.json.answer.array.Times;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetTimes {
    private java.util.List<Times> times;
}

