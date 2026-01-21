package ClassConnect.controller;
	import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.GetMapping;

	@Controller
	public class PageController {


	    @GetMapping("/register")
	    public String registerPage() {
	        return "register";
	    }

	    @GetMapping("/")
	    public String loginPage() {
	        return "login";   
	    }

	    @GetMapping("/dashboard")
	    public String dashboard() {
	        return "dashboard";
	    }
	    @GetMapping("/class/create-page")
	    public String createClassPage() {
	        return "create-class";
	    }
	    @GetMapping("/attendance")
	    public String markAttendancePage() {
	        return "attendance";
	    }

	    @GetMapping("/attendance/history-page")
	    public String attendanceHistoryPage() {
	        return "attendance-history";
	    }
	  
	}

