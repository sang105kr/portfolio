package com.kh.portfolio.common.vo;

public class CodeVO {
	private String code_id;
	private String name;
	
	public CodeVO() {}
	
	public CodeVO(String code_id, String name) {
		super();
		this.code_id = code_id;
		this.name = name;
	}
	public String getCode_id() {
		return code_id;
	}
	public void setCode_id(String code_id) {
		this.code_id = code_id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "CodeVO [code_id=" + code_id + ", name=" + name + "]";
	}
}
