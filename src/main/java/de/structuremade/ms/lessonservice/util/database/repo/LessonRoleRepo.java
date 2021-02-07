package de.structuremade.ms.lessonservice.util.database.repo;

import de.structuremade.ms.lessonservice.util.database.entity.LessonRoles;
import de.structuremade.ms.lessonservice.util.database.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRoleRepo extends JpaRepository<LessonRoles, String> {
    Iterable<LessonRoles> findAllBySchool(School schoolid);
}
