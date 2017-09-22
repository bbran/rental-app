package com.libertymutual.goforcode.controllers;

import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.goforcode.models.User;
import com.libertymutual.goforcode.utilities.AutoCloseableDb;
import com.libertymutual.goforcode.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {

	public static final Route newForm = (Request req, Response res) -> {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("currentUser", req.session().attribute("currentUser"));
		model.put("noUser", req.session().attribute("currentUser") == null);
		model.put("csrf", req.session().attribute("csrfToken"));
		return MustacheRenderer.getInstance().render("signup/newForm.html", model);
	};
	public static final Route create = (Request req, Response res) -> {
		String hashed = BCrypt.hashpw(req.queryParams("password"), BCrypt.gensalt());
		User user = new User();
		user.setEmail(req.queryParams("email"));
		user.setPassword(hashed);
		user.setFirstName(req.queryParams("first_name"));
		user.setLastName(req.queryParams("last_name"));
		try (AutoCloseableDb db = new AutoCloseableDb()){
			user.saveIt();
			req.session().attribute("currentUser", user);
			res.redirect("/");
			return "";
		}
	};
	
	

}
