package com.libertymutual.goforcode.models;

import java.util.ArrayList;
import java.util.List;

public class Apartment {
	private int id;
	private int rent;
	private int numberOfBedrooms;
	private int numberOfBathrooms;
	private int squareFootage;
	private String address;
	private String city;
	private String state;
	private String zip;
	
	public Apartment()	{}
	
	public Apartment(int id, int rent, int numberOfBedrooms, int numberOfBathrooms, int squareFootage, String address, String city, String state, String zip)	{
		this.id = id;
		this.rent = rent;
		this.numberOfBathrooms = numberOfBathrooms;
		this.numberOfBedrooms = numberOfBedrooms;
		this.squareFootage = squareFootage;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public int getNumberOfBedrooms() {
		return numberOfBedrooms;
	}
	public void setNumberOfBedrooms(short numberOfBedrooms) {
		this.numberOfBedrooms = numberOfBedrooms;
	}
	public int getNumberOfBathrooms() {
		return numberOfBathrooms;
	}
	public void setNumberOfBathrooms(short numberOfBathrooms) {
		this.numberOfBathrooms = numberOfBathrooms;
	}
	public int getSquareFootage() {
		return squareFootage;
	}
	public void setSquareFootage(int squareFootage) {
		this.squareFootage = squareFootage;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public void setNumberOfBedrooms(int numberOfBedrooms) {
		this.numberOfBedrooms = numberOfBedrooms;
	}

	public void setNumberOfBathrooms(int numberOfBathrooms) {
		this.numberOfBathrooms = numberOfBathrooms;
	}
	
	public static List<Apartment> findAll() {
		java.util.List<Apartment> apartments = new ArrayList<Apartment>();
		apartments.add(new Apartment(1, 1500, 1, 0, 350, "123 Main St", "San Francisco", "CA", "95125"));
		apartments.add(new Apartment(2, 4000, 5, 6, 4000, "123 Fake St", "Seattle", "FL", "66666"));
		return apartments;
	}
	
	public static Apartment findById(int id)	{
		if(id == 1 || id == 2)	{
			return findAll().get(id-1);
		}
		return null;
	}

}
