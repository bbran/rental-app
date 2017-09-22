package com.libertymutual.goforcode.filters;

import static spark.Spark.halt;

import java.util.UUID;

import com.libertymutual.goforcode.models.Apartment;
import com.libertymutual.goforcode.models.User;
import com.libertymutual.goforcode.utilities.AutoCloseableDb;

import spark.Filter;
import spark.Request;
import spark.Response;

public class SecurityFilters {

	public static final Filter isAuthenticated = (Request req, Response res) -> {
		if (req.session().attribute("currentUser") == null) {
			res.redirect("/login?returnPath=" + req.pathInfo());
			halt();
		}
	};
	public static final Filter isLister = (Request req, Response res) -> {
		User currentUser = (User) req.session().attribute("currentUser");
		try (AutoCloseableDb db = new AutoCloseableDb())	{
			Apartment apartment = Apartment.findById(Integer.parseInt(req.params("id")));
			if (!apartment.getUserId().equals(currentUser.getId()))	{
				halt();
			}
		}
	};
	public static final Filter csrfPostMatch = (Request req, Response res) -> {
		String csrf = req.queryParams("csrf");
		if (req.requestMethod() == "POST" && !req.session().attribute("csrfToken").toString().equals(csrf))	{
			halt();
		}
	};
	public static final Filter setCsrfToken = (Request req, Response res) -> {
		if (req.session().isNew())	{
			req.session().attribute("csrfToken", UUID.randomUUID());			
		}
		System.out.println(req.cookies());
	};
}
