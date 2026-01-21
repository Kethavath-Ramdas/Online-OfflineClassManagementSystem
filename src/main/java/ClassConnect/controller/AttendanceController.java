package ClassConnect.controller;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ClassConnect.dto.AttendanceRequest;
import ClassConnect.entity.Attendance;
import ClassConnect.service.AttendanceService;
import ClassConnect.util.ResponseStructure;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // MARK ATTENDANCE
    @PostMapping("/mark")
    public ResponseEntity<ResponseStructure<Attendance>> mark(
            @RequestBody AttendanceRequest request) {

        Attendance attendance = new Attendance();
        attendance.setClassId(request.classId);
        attendance.setUserId(request.userId);
        attendance.setClassType(request.classType);
        attendance.setAttendanceDate(request.attendanceDate);
        attendance.setStatus(request.status);

        Attendance saved = attendanceService.markAttendance(attendance);

        ResponseStructure<Attendance> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Attendance marked successfully");
        response.setData(saved);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET HISTORY
    @GetMapping("/history")
    public ResponseEntity<ResponseStructure<List<Attendance>>> getHistory(
            @RequestParam int userId) {

        List<Attendance> list = attendanceService.getHistory(userId);

        ResponseStructure<List<Attendance>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Attendance fetched successfully");
        response.setData(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // GET HISTORY BETWEEN DATES
    @GetMapping("/history/range")
    public ResponseEntity<ResponseStructure<List<Attendance>>> getHistoryBetweenDates(
            @RequestParam int userId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end) {

        List<Attendance> list =
        attendanceService.getHistoryBetweenDates(userId, start, end);
        ResponseStructure<List<Attendance>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Attendance fetched successfully");
        response.setData(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

