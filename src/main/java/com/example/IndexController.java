package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class IndexController {

    @Autowired
	public ExternalService externalService;

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", "RestTemplate Example Application");
		return "index";
	}
}
