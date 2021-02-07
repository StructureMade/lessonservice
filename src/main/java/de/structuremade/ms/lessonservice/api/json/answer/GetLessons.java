package de.structuremade.ms.lessonservice.api.json.answer;

import de.structuremade.ms.lessonservice.api.json.answer.array.Lessons;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetLessons {
    private List<Lessons> lessons;
}
