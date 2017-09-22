package com.libertymutual.goforcode.controllers;

import com.libertymutual.goforcode.models.Apartment;
import com.libertymutual.goforcode.models.User;
import com.libertymutual.goforcode.utilities.AutoCloseableDb;

import spark.Request;
import spark.Response;
import spark.Route;

public class LikeController {

	public static final Route like = (Request req, Response res) -> {
		try (AutoCloseableDb db = new AutoCloseableDb())	{
			Apartment apartment = Apartment.findById(Integer.parseInt(req.params("id")));
			User currentUser = (User) req.session().attribute("currentUser");
			apartment.add(currentUser);
			res.redirect("/apartments/" + req.params("id"));
			return "";
		}
	};
}
