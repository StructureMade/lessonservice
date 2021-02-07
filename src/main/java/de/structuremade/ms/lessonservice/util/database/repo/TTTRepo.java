package de.structuremade.ms.lessonservice.util.database.repo;

import de.structuremade.ms.lessonservice.util.database.entity.School;
import de.structuremade.ms.lessonservice.util.database.entity.TimeTableTimes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TTTRepo extends JpaRepository<TimeTableTimes, String> {
    Iterable<TimeTableTimes> findAllBySchool(School schoolid);
}
