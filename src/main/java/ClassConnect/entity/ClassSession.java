package ClassConnect.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ClassSession {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String classType;
	private String classroom;
	private String meetinglink;
	@JsonFormat(pattern = "yyyy-MM-dd")
	public LocalDate classDate;
	 private String time;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public LocalDate getClassDate() {
		return classDate;
	}
	public void setClassDate(LocalDate classDate) {
		this.classDate = classDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getClassroom() {
		return classroom;
	}
	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}
	public String getMeetinglink() {
		return meetinglink;
	}
	public void setMeetinglink(String meetinglink) {
		this.meetinglink = meetinglink;
	}
	

}
