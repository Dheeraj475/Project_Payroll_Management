package com.emsb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Employees")
public class Employees {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeId;

	@Column(nullable = false)
	@NotNull(message = "Name must be given!")
	
	private String name;

	@Column(nullable = false)
	@NotNull(message = "Age must be given!")
	private int age;

	@Column(nullable = false)
	@NotNull(message = "Gender must be given!")
	
	private String gender;

	@Column(nullable = false)
	@NotNull(message = "Desigantion must be given!")
	
	private String designation;

	@Column(nullable = false)
	@NotNull(message = "Rating must be given!")
	private int rating;

	private double salary;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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

	public Employees(int employeeId, String name, int age, String gender, String designation, int rating,
			double salary) {
		super();
		this.employeeId = employeeId;
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
		return "Employees [employeeId=" + employeeId + ", name=" + name + ", age=" + age + ", gender=" + gender
				+ ", designation=" + designation + ", rating=" + rating + ", salary=" + salary + "]";
	}

}
