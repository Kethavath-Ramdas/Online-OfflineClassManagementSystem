package ClassConnect.service;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ClassConnect.entity.Attendance;
import ClassConnect.exception.AttendanceNotFoundException;
import ClassConnect.repository.AttendanceRepository;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    // MARK ATTENDANCE
    public Attendance markAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    // GET HISTORY BY USER
    public List<Attendance> getHistory(int userId) {

        List<Attendance> list = attendanceRepository.findByuserId(userId);

        if (list.isEmpty()) {
            throw new AttendanceNotFoundException(
                    "No attendance found for userId : " + userId
            );
        }

        return list;
    }

    // GET HISTORY BETWEEN DATES
    public List<Attendance> getHistoryBetweenDates(
            int userId, LocalDate start, LocalDate end) {

        List<Attendance> list =
                attendanceRepository.findByuserIdAndAttendanceDateBetween(
                        userId, start, end
                );

        if (list.isEmpty()) {
            throw new AttendanceNotFoundException(
                    "No attendance found between given dates"
            );
        }

        return list;
    }
}
