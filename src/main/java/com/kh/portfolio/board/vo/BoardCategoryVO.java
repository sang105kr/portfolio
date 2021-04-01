package com.kh.portfolio.board.vo;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data	//getter,setter,toString,equals,hashcode,디폴트생성자 자동 생성
@NoArgsConstructor	 //디폴트 생성자 자동 생성
@AllArgsConstructor  //모든 멤버필드를 매개변수로 갖는 생성자 자동 생성
public class BoardCategoryVO {

		@NotNull(message = "분류를 선택해 주세요!")
		private Integer cid;
		private String cname;
		
}
