package kr.co.iei;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping(value="/")
	public String main() {
		return "index";
	}
	
	@GetMapping(value="/about")
	public String about() {
		return "about";
	}
	
	@GetMapping(value="/service")
	public String service() {
		return "service";
	}
	
	@GetMapping(value="/event")
	public String event() {
		return "event";
	}
	
	@GetMapping(value="/menu")
	public String menu() {
		return "menu";
	}
	
	@GetMapping(value="/book")
	public String book() {
		return "book";
	}
	
	@GetMapping(value="/blog")
	public String blog() {
		return "blog";
	}
	
	@GetMapping(value="/team")
	public String team() {
		return "team";
	}
	
	@GetMapping(value="/testimonial")
	public String testimonial() {
		return "testimonial";
	}
	
	@GetMapping(value="/error404")
	public String error404() {
		return "404";
	}
	
	@GetMapping(value="/contact")
	public String contact() {
		return "contact";
	}
}
