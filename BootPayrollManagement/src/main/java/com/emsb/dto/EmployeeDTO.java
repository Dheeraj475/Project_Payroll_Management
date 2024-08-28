package com.emsb.dto;

public class EmployeeDTO {

	private String name;
	private int age;
	private String gender;
	private String designation;
	private int rating;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public EmployeeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployeeDTO(String name, int age, String gender, String designation, int rating) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.designation = designation;
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "EmployeeDTO [name=" + name + ", age=" + age + ", gender=" + gender + ", designation=" + designation
				+ ", rating=" + rating + "]";
	}

}
