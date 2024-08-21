package com.emsb.entity;

public class Employees {

	private int id;
	private String name;
	private int age;
	private String gender;
	private String designation;
	private int rating;
	private double salary;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Employees(int id, String name, int age, String gender, String designation, int rating, double salary) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.designation = designation;
		this.rating = rating;
		this.salary = salary;
	}

	public Employees() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Employees [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", designation="
				+ designation + ", rating=" + rating + ", salary=" + salary + "]";
	}

}
