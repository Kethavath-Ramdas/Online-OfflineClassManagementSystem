package ClassConnect.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ClassConnect.entity.Attendance;

public interface AttendanceRepository  extends JpaRepository<Attendance, Long>{
	List<Attendance>findByuserId(int userId);
	List<Attendance>findByuserIdAndAttendanceDateBetween(int userId,LocalDate start,LocalDate end);

}
