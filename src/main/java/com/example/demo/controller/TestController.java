package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TestRequestBodyDTO;

@RestController
@RequestMapping("test") // 리소스
public class TestController {

	@GetMapping
	public String testController() {
		return "Hello World";
	}
	
	@GetMapping("/testGetMaping")
	public String testControllerWithPath() {
		return "Hello World! testGetMapping";
	}
	
	@GetMapping("/{id}")
	public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
		return "ID " + id;
	}
	
	// /test 경로는 이미 존재하므로 /test/testRequestParam로 지정했다.	
	@GetMapping("/testRequestParam")
	public String testControllerRequestParam(@RequestParam(required = false) int id) {
		return "Param ID : " + id;
	}
	
	// /test 경로는 이미 존재하므로 /test/testRequestBody로 지정했다.
	// Postman 에서 Body에 JSON 형태로 넣은 다음 실행하면 원하는 결과가 나온다
	@GetMapping("/testRequestBody")
	public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
		return "RequestBody ID : " + testRequestBodyDTO.getId() + 
				" Message : " + testRequestBodyDTO.getMessage();
	}
}
