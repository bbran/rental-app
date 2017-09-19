package com.libertymutual.goforcode.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.libertymutual.goforcode.models.Apartment;
import com.libertymutual.goforcode.utilities.MustacheRenderer;
import com.libertymutual.goforcode.utilities.VelocityRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class HomeController {
	public static final Route index = (Request req, Response res) -> {
		List<Apartment> apartments = Apartment.findAll();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("apartments", apartments);
		model.put("currentUser", req.session().attribute("currentUser"));
		model.put("noUser", req.session().attribute("currentUser") == null);
//		return MustacheRenderer.getInstance().render("home/index.html", model);
		return VelocityRenderer.getInstance().render(model, "home/index2.html");
	};

}