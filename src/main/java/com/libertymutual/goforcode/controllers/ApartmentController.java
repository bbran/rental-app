package com.libertymutual.goforcode.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.libertymutual.goforcode.models.Apartment;
import com.libertymutual.goforcode.models.User;
import com.libertymutual.goforcode.utilities.AutoCloseableDb;
import com.libertymutual.goforcode.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class ApartmentController {
	public static final Route details = (Request req, Response res) -> {
		try (AutoCloseableDb db = new AutoCloseableDb()) {
			Apartment apartment = Apartment.findById(Integer.parseInt(req.params(":id")));
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("apartment", apartment);
			return MustacheRenderer.getInstance().render("apartments/details.html", model);
		}
	};
	
	public static final Route newForm = (Request req, Response res) -> {
		return MustacheRenderer.getInstance().render("apartments/newForm.html", null);
	};
	
	public static final Route create = (Request req, Response res) -> {
		User user = req.session().attribute("currentUser");
		Long userId = (Long) user.getId();
		Apartment apartment = new Apartment(
					Integer.parseInt(req.queryParams("rent")),
					Integer.parseInt(req.queryParams("number_of_bedrooms")),
					Integer.parseInt(req.queryParams("number_of_bathrooms")),
					Integer.parseInt(req.queryParams("square_footage")),
					req.queryParams("address"),
					req.queryParams("city"),
					req.queryParams("state"),
					req.queryParams("zip")
				);
		apartment.setUserId(userId);
		try (AutoCloseableDb db = new AutoCloseableDb()) {
			apartment.saveIt();
			res.redirect("/");
			return "";
		}
	};

	public static final Route index = (Request req, Response res) -> {
		User user = req.session().attribute("currentUser");
		Long userId = (Long) user.getId();
		try (AutoCloseableDb db = new AutoCloseableDb())	{
			List<Apartment> apartments = Apartment.where("user_id = ?", userId);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("apartments", apartments);
			return MustacheRenderer.getInstance().render("apartments/index.html", model);
		}
	};

}
