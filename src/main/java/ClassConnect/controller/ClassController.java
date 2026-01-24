package ClassConnect.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ClassConnect.entity.ClassSession;
import ClassConnect.service.ClassService;
import ClassConnect.util.ResponseStructure;

@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @PostMapping("/create")
    public ResponseEntity<ResponseStructure<ClassSession>> createClass(
            @RequestBody ClassSession session) {

        ClassSession session1 = new ClassSession();
        session1.setClassType(session.getClassType());
        session1.setClassroom(session.getClassroom());
        session1.setMeetinglink(session.getMeetinglink());
        session1.setClassDate(session.getClassDate());
        session1.setTime(session.getTime());

        ClassSession saved = classService.createClass(session1);

        ResponseStructure<ClassSession> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Class created successfully");
        response.setData(saved);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
