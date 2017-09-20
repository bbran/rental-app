package com.libertymutual.goforcode.controllers;

import org.javalite.common.JsonHelper;
import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.goforcode.models.User;
import com.libertymutual.goforcode.utilities.AutoCloseableDb;

import spark.Request;
import spark.Response;
import spark.Route;

public class UserApiController {
	
	public static final Route create = (Request req, Response res) -> {
		User user = new User();
		String userJson = req.body();
		user.fromMap(JsonHelper.toMap(userJson));
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		try (AutoCloseableDb db = new AutoCloseableDb()){
			user.saveIt();
			res.status(201);
			return user.toJson(true);
		}
	};

}
