package de.structuremade.ms.lessonservice.api.service;

import de.structuremade.ms.lessonservice.api.json.CreateLesson;
import de.structuremade.ms.lessonservice.api.json.answer.GetLessons;
import de.structuremade.ms.lessonservice.api.json.answer.GetTimes;
import de.structuremade.ms.lessonservice.api.json.answer.array.Teacher;
import de.structuremade.ms.lessonservice.api.json.answer.array.Times;
import de.structuremade.ms.lessonservice.util.JWTUtil;
import de.structuremade.ms.lessonservice.util.database.entity.LessonRoles;
import de.structuremade.ms.lessonservice.util.database.entity.Lessons;
import de.structuremade.ms.lessonservice.util.database.entity.TimeTableTimes;
import de.structuremade.ms.lessonservice.util.database.repo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class LessonService {

    private final Logger LOGGER = LoggerFactory.getLogger(LessonService.class);

    @Autowired
    LessonRoleRepo lessonRoleRepo;

    @Autowired
    LessonRepo lessonRepo;

    @Autowired
    LessonSettingsRepo lessonSettingsRepo;

    @Autowired
    TTTRepo tttRepo;

    @Autowired
    SchoolRepo schoolRepo;

    @Autowired
    JWTUtil jwtUtil;

    public int create(CreateLesson createLesson, String jwt){
        List<TimeTableTimes> times = new ArrayList<>();
        try{
            LOGGER.info("Get Lessonrole");
            LessonRoles lr = lessonRoleRepo.getOne(createLesson.getLesson());
            LOGGER.info("Check if u have access to do this");
            if (!lr.getSchool().equals(schoolRepo.getOne(jwtUtil.extractSpecialClaim(jwt, "schoolid")))) return 0;
            LOGGER.info("Try to create an Lesson");
            Lessons lesson = new Lessons();
            lesson.setDay(createLesson.getDay());
            lesson.setRoom(createLesson.getRoom());
            lesson.setState(0);
            LOGGER.info("Get Settings and setup them");
            lesson.setSettings(lessonSettingsRepo.getOne(createLesson.getSettings()));
            LOGGER.info("Get times and add them to List");
            createLesson.getTimes().forEach(time -> {
                times.add(tttRepo.getOne(time));
            });
            lesson.setTimes(times);
            lesson.setLessonRoles(lr);
            LOGGER.info("All successfully added to Entity and now save it");
            lessonRepo.save(lesson);
            return 1;
        }catch (Exception e){
            LOGGER.error("Couldn't create final lesson", e.fillInStackTrace());
            return 2;
        }

    }

    public GetTimes getTimes(String jwt){
        List<Times> times = new ArrayList<>();
        try {
            if (jwtUtil.isTokenExpired(jwt)) return  new GetTimes();
            LOGGER.info("Get all Times of School and add them to a List");
            tttRepo.findAllBySchool(schoolRepo.getOne(jwtUtil.extractSpecialClaim(jwt,"schoolid"))).forEach(tttime ->{
               Times time = new Times();
               time.setId(tttime.getId());
               time.setBegin(tttime.getStarttime());
               time.setEnd(tttime.getEndtime());
               times.add(time);
            });
            return new GetTimes(times);
        }catch (Exception e){
            LOGGER.error("Couldn't get the  Times", e.fillInStackTrace());
            return null;
        }
    }

    @Transactional
    public GetLessons getLessons(String jwt){
        List<de.structuremade.ms.lessonservice.api.json.answer.array.Lessons> lessons = new ArrayList<>();
        try {
            if (jwtUtil.isTokenExpired(jwt)) return  new GetLessons();
            LOGGER.info("Get all Lessons of this School and add them to an List");
            lessonRoleRepo.findAllBySchool(schoolRepo.getOne(jwtUtil.extractSpecialClaim(jwt,"schoolid"))).forEach(lr ->{
                de.structuremade.ms.lessonservice.api.json.answer.array.Lessons lesson = new de.structuremade.ms.lessonservice.api.json.answer.array.Lessons();
                lesson.setId(lr.getId());
                lesson.setName(lr.getName());
                lesson.setTeacher(new Teacher(lr.getTeacher()));
                lessons.add(lesson);
            });
            return new GetLessons(lessons);
        }catch (Exception e){
            LOGGER.error("Couldn't get the Lessons", e.fillInStackTrace());
            return null;

        }
    }
}
