package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {

	@Autowired
	private TodoService service;
	
	@GetMapping("/test")
	public ResponseEntity<?> testTodo(){
		String str = service.testService(); // 테스트 서비스 이용
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createTodo(
			@AuthenticationPrincipal String userId,
			@RequestBody TodoDTO dto
			){
		try {
//			String temporaryUserId = "temporary-user"; // 이제 userId를 쓸 것이다
			
			// 1. TodoEntity로 변환한다.
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			// 2. id를 null로 초기화한다. 생성 당시에는 id가 없어야 하기 때문이다.
			entity.setId(null);
			
			// 3. 임시 사용자 아이디를 설정해 준다. 이 부분은 4장 인증과 인가에서 수정할 예정. 지금은 인증이 없으므로 한 사용자(temporary-user)만 로그인 없이 사용하는 어플이다.
			// userId를 써준다
			entity.setUserId(userId);
			
			// 4. 서비스를 이용해 Todo 엔티티를 생성한다.
			List<TodoEntity> entities = service.create(entity);
			
			// 5. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			// 6. 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			// 7. ResponseDTO를 리턴한다.
			return ResponseEntity.ok().body(response);
		}catch (Exception e) {
			// TODO: 8. 혹시 예외가 있는 경우 dto 대신 error 메세지를 넣어 리턴한다.
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}  
	}
	
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
//		String temporaryUserId = "temporary-user"; // temporary user id
		
		// 1. 서비스 메서드의 retrieve() 메서드를 이용해 Todo 리스트를 가져온다.
		// userId를 넣어준다
		List<TodoEntity> entities = service.retrieve(userId);
		
		// 2. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		// 3. 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		// 4. ResponseDTO를 리턴한다. 
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
//		String temporaryUserId = "temporary-user";		// temporary user id;
		
		// 1. dto를 entity로 변환한다.
		TodoEntity entity = TodoDTO.toEntity(dto);
		
		// 2. id를 temporaryUserId로 초기화한다. 여기는 4장 인증과 인가에서 수정할 예정
		entity.setUserId(userId);;
		
		// 3. 서비스를 이용해 entity를 업데이트한다.
		List<TodoEntity> entities = service.update(entity);
		
		// 4. 자바 스트림을 이용해 엔티티 리스트를 TodoDTO 리스트로 변환한다.
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		// 5. 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		// 6. ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response); 	 
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
	
		try{//String temporaryUserId = "temporary-user";
		
		// 1. TodoEntity로 변환한다.
		TodoEntity entity = TodoDTO.toEntity(dto);
		
		entity.setUserId(userId);
		
		// 2. 서비스를 이용해 Todo 엔티티를 삭제한다.
		List<TodoEntity> entities = service.delete(entity);
		
		// 3. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		// 4. 변환된 TodoDTO 리스트를 이용해 ResonseDTO를 초기화한다.
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		// 5. ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
		}catch (Exception e) {
			// 6. 혹시 예외가 있는 경우 dto 대신 erorr 메세지를 넣어 리턴한다.
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.ok().body(response);
		}
		
	}
}
