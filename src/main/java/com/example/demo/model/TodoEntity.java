package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
//@Entity(name = "Todo") // 이름 지어준다. name 없으면 @Entity("이 이름을 쓴다."), 이것도 없으면 클래스 이름
@Entity
@Table(name = "Todo")
public class TodoEntity {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id; 
	private String userId;
	private String title;
	private boolean done; // true -todo 완료한 경우
}
