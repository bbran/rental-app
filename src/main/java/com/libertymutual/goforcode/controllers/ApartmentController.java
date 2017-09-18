package com.libertymutual.goforcode.controllers;

import java.util.HashMap;
import java.util.Map;

import com.libertymutual.goforcode.models.Apartment;
import com.libertymutual.goforcode.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class ApartmentController {
	public static final Route details = (Request req, Response res) -> {
		Apartment apartment = Apartment.findById(Integer.parseInt(req.params(":id")));
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("apartment", apartment);
		return MustacheRenderer.getInstance().render("addresses/details.html", model);
	};

}
