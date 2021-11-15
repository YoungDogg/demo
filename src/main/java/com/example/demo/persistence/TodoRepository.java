package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String>{
	 
	// ?1은 매서드의 매개변수의 순서위치다.
	//@Query("select * from Todo t where t.userId = ?1")
	// 오류 해결법 : Todo의 풀네임을 적어주면 된다고 한다. 예) com.example.demo.persistence.Todo...
	List<TodoEntity> findByUserId(String userId);
}
