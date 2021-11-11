package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {

	@Autowired
	private TodoRepository repository;  
	
	public String testService() {    
		//TodoEntity 생성
		TodoEntity entity = TodoEntity.builder().title("My first todo item").userId("userId001").build();
		//TodoEntiity 저장
		repository.save(entity);
		//TodoEntity 검색
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
 		return savedEntity.getTitle();
	}
	
	public List<TodoEntity> create(final TodoEntity entity){
		//validations
		validate(entity);
		
		//save
		repository.save(entity);
		
		log.info("Entity Id : {} is saved", entity.getId());
		
		//find user by id
		return repository.findByUserId(entity.getUserId()); 	  
	}
	
	public List<TodoEntity> retrieve(final String userId){
		log.info("Entity Id : {} is retrieved", userId);
		
		return repository.findByUserId(userId);
	}
	
	public List<TodoEntity> update(final TodoEntity entity){
		// 1. 저장할 엔티티가 유효한지 확인한다. 
		validate(entity);
		
		// 2 넘겨받은 엔티티 id를 이용해 TodoEntity를 가져온다. 존재하지 않는 엔티티는 업데이트할 수 없기 때문이다.
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
		original.ifPresent(todo -> {
			// 3. 반환된 TodoEntity가 존재하면 값을 새 entity 값으로 덮어 씌운다.
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
			// 4. 데이터베이스에 새 값을 저장한다.
			repository.save(todo);
		});
		 
		return retrieve(entity.getUserId());
	}
	
	//리팩토링한 메서드
	private void validate(final TodoEntity entity) {
		if(entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null.");
		}
		
		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unkown user.");
		}
	}
}
