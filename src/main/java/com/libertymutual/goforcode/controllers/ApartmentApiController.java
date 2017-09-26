package com.libertymutual.goforcode.controllers;

import com.libertymutual.goforcode.models.Apartment;
import com.libertymutual.goforcode.models.User;
import com.libertymutual.goforcode.utilities.AutoCloseableDb;

import static spark.Spark.notFound;

import org.javalite.activejdbc.LazyList;
import org.javalite.common.JsonHelper;

import spark.Request;
import spark.Response;
import spark.Route;

public class ApartmentApiController {
	
	public static final Route details = (Request req, Response res) -> {
		try (AutoCloseableDb db = new AutoCloseableDb())	{
			String idAsString = req.params("id");
			int id = Integer.parseInt(idAsString);
			Apartment apartment = Apartment.findById(id);
			if (apartment != null)	{
				res.header("Content-Type", "application/json");
				return apartment.toJson(true);
			}
			notFound("Did not find that.");
			return "";
		}
	};
	public static final Route create = (Request req, Response res) -> {
		Apartment apartment = new Apartment();
		String apartmentJson = req.body();
		apartment.fromMap(JsonHelper.toMap(apartmentJson));
		try (AutoCloseableDb db = new AutoCloseableDb())	{
			apartment.saveIt();
			res.status(201);
			return apartment.toJson(true);
		}
		
	};
	public static final Route index = (Request req, Response res) -> {
		try (AutoCloseableDb db = new AutoCloseableDb())	{
			LazyList<Apartment> activeApartments = Apartment.where("is_active = ?", true);
			if (activeApartments.size() > 0)	{
				res.header("Content-Type", "application/json");
				return activeApartments.toJson(true);
			}
			notFound("Did not find that.");
			return "";
		}
	};
	
	

}
