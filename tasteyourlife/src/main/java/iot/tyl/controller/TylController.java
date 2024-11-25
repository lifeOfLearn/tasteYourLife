package iot.tyl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import iot.tyl.util.contant.CommunityType;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class TylController {
	
	@GetMapping
	public String index() {
		return "index.html";
	}
	
	@GetMapping("${path.forget.root}")
	public String forget(@RequestParam String token , HttpServletRequest request) {
		request.setAttribute(CommunityType.TOKEN.name(), token);
		return "forgetPath/forget.html";
		//TODO interceptor check token => to cookie  submit=>forget.des
	}
	
}
