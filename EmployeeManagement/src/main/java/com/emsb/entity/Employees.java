package com.emsb.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="Employees")
public class Employees {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	@NotNull(message = "Name must be given!")
	@Min(value = 4, message = "Name must be at least 4 characters!" )
	private String name;
	
	@Column(nullable = false)
	@NotNull(message = "Age must be given!")
	@Min(value = 21, message = "Age must be at least 21!")
	@Max(value = 60, message = "Age must be at most 60!")
	private int age;
	
	@Column(nullable = false)
	@NotNull(message = "Gender must be given!")
	@Min(value=4, message = "Gender must be at least 4 characters!")
	private String gender;
	
	@Column(nullable = false)
	@NotNull(message = "Desigantion must be given!")
	@Min(value=5, message = "Designation must be at least 5 characters!")
	private String designation;
	
	@Column(nullable = false)
	@NotNull(message = "Rating must be given!")
	@Min(value =1, message = "Rating must be at least 1!")
	@Max(value =5, message = "Rating must be at most 5!")
	private int rating;
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
