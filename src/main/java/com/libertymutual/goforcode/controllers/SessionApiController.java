package com.libertymutual.goforcode.controllers;

import org.javalite.common.JsonHelper;
import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.goforcode.models.User;
import com.libertymutual.goforcode.utilities.AutoCloseableDb;

import spark.Request;
import spark.Response;
import spark.Route;

public class SessionApiController {

	public static final Route create = (Request req, Response res) -> {
		User unauthenticatedUser = new User();
		String userJson = req.body();
		unauthenticatedUser.fromMap(JsonHelper.toMap(userJson));
		try (AutoCloseableDb db = new AutoCloseableDb()) {
			User actualUser = User.findFirst("email = ?", unauthenticatedUser.getEmail());
			if (actualUser != null && BCrypt.checkpw(unauthenticatedUser.getPassword(), actualUser.getPassword())) {
				req.session().attribute("currentUser", actualUser);
				res.header("Content-Type", "application/json");
				return actualUser.toJson(true);
			}
		}
		return null;
	};

}
