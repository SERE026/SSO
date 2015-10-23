package com.bs.api.model;

import java.io.Serializable;

public class User implements Serializable{
	
	private static final long serialVersionUID = 8905785185471601900L;
	
	private Long id;
	private String name;
	private String password;
	
	private int age;
	private int sex;
	
	private String job;
	private String university;
	
	
	public User(){}
	
	
	
	public User(String name, String password, int age, int sex, String job,
			String university) {
		super();
		this.name = name;
		this.password = password;
		this.age = age;
		this.sex = sex;
		this.job = job;
		this.university = university;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", age=" + age
				+ ", sex=" + sex + ", job=" + job + ", university="
				+ university + "]";
	}
}
