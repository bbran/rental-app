package com.libertymutual.goforcode.models;

import org.javalite.activejdbc.Model;

public class ApartmentsUsers extends Model {
	
	public ApartmentsUsers()	{}
	
	public ApartmentsUsers(Long apartmentId, Long userId)	{
		setApartmentId(apartmentId);
		setUserId(userId);
	}
	
	public int getApartmentId()	{
		return getInteger("apartment_id");
	}
	public void setApartmentId(Long apartmentId)	{
		set("apartment_id", apartmentId);
	}
	public int getUserId()	{
		return getInteger("user_id");
	}
	public void setUserId(Long userId)	{
		set("user_id", userId);
	}

}
