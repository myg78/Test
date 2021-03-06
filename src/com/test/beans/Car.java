package com.test.beans;

public class Car {
	
	private String year;
	private String make;
	private String model;
	
	public Car() {}
	
	public Car(String year, String make, String model) {
		this.year = year;
		this.make = make;
		this.model = model;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public void printCar() {
		System.out.println(year + " " + make +  " " + model);
	}

	@Override
	public String toString() {
		return "Car{" +
				"year='" + year + '\'' +
				", make='" + make + '\'' +
				", model='" + model + '\'' +
				'}';
	}
}
