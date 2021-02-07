package de.structuremade.ms.lessonservice.api.routes;

import com.google.gson.Gson;
import de.structuremade.ms.lessonservice.api.json.CreateLesson;
import de.structuremade.ms.lessonservice.api.json.answer.GetLessons;
import de.structuremade.ms.lessonservice.api.json.answer.GetTimes;
import de.structuremade.ms.lessonservice.api.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/service/lesson")
public class LessonRoute {

    @Autowired
    LessonService lessonService;

    private Gson gson;

    @CrossOrigin
    @PostMapping("/create")
    public void createLesson(@RequestBody CreateLesson createLesson, HttpServletRequest request, HttpServletResponse response){
        switch (lessonService.create(createLesson, request.getHeader("Authorization").substring(7))){
            case 0 -> response.setStatus(HttpStatus.UNAUTHORIZED.value());
            case 1 -> response.setStatus(HttpStatus.CREATED.value());
            case 2 -> response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @CrossOrigin
    @GetMapping("/gettimes")
    public Object getTimes(HttpServletResponse response, HttpServletRequest request){
        GetTimes gt = lessonService.getTimes(request.getHeader("Authorization").substring(7));
        if (gt != null){
            if(gt.getTimes() == null){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
            response.setStatus(HttpStatus.OK.value());
            return gson.toJson(gt);
        }else{
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    @CrossOrigin
    @GetMapping("/getlessons")
    public Object getLessons(HttpServletResponse response, HttpServletRequest request){
        GetLessons gt = lessonService.getLessons(request.getHeader("Authorization").substring(7));
        if (gt != null){
            if(gt.getLessons() == null){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
            response.setStatus(HttpStatus.OK.value());
            return gson.toJson(gt);
        }else{
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

}
