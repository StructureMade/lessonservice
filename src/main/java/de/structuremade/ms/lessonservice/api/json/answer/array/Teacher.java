package de.structuremade.ms.lessonservice.api.json.answer.array;

import de.structuremade.ms.lessonservice.util.database.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Teacher {
    private String id;
    private String abbreviation;
    private String firstname;
    private String name;

    public Teacher(User user){
        this.id = user.getId();
        this.abbreviation = user.getAbbreviation();
        this.firstname = user.getFirstname();
        this.name = user.getName();
    }
}
