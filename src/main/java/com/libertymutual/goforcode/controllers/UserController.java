package com.libertymutual.goforcode.controllers;

import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.goforcode.models.User;
import com.libertymutual.goforcode.utilities.AutoCloseableDb;
import com.libertymutual.goforcode.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {

	public static final Route newForm = (Request req, Response res) -> {
		return MustacheRenderer.getInstance().render("signup/newForm.html", null);
	};
	public static final Route create = (Request req, Response res) -> {
		String hashed = BCrypt.hashpw(req.params("password"), BCrypt.gensalt());
		User user = new User();
		user.setEmail(req.queryParams("email"));
		user.setPassword(hashed);
		user.setFirstName(req.queryParams("firstName"));
		user.setLastName(req.queryParams("lastName"));
		try (AutoCloseableDb db = new AutoCloseableDb()){
			user.saveIt();
			req.session().attribute("currentUser", user);
			res.redirect("/");
			return "";
		}
	};
	
	

}
