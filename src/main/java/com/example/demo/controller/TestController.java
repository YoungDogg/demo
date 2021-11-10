package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.support.Repositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;

import lombok.Builder;

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
	
	// ResponseDTO를 반환하는 컨트롤러 메서드
	@GetMapping("/testResponseBody")
	public ResponseDTO<String> testControllerResponseBody(){
		List<String> list = new ArrayList<>();
		list.add("Hello, ResponseDTO here");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		 
		return  response;
	}
	
	// ResponseEntity : HTTP 응답의 바디, 다른 매개변수들을 조작하고 싶을 때 사용
	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerResponseEntity(){
		List<String> list = new ArrayList<>();
		list.add("Hello, this is reponseEntity");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		// http status 400으로 설정
		//return ResponseEntity.badRequest().body(response);
		//ok 응답
		return ResponseEntity.ok().body(response);
	}
	

}
