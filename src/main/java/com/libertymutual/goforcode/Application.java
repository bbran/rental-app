package com.libertymutual.goforcode;

import static spark.Spark.*;

import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.goforcode.controllers.ApartmentApiController;
import com.libertymutual.goforcode.controllers.ApartmentController;
import com.libertymutual.goforcode.controllers.HomeController;
import com.libertymutual.goforcode.controllers.LikeController;
import com.libertymutual.goforcode.controllers.SessionController;
import com.libertymutual.goforcode.controllers.UserApiController;
import com.libertymutual.goforcode.controllers.UserController;
import com.libertymutual.goforcode.filters.SecurityFilters;
import com.libertymutual.goforcode.models.Apartment;
import com.libertymutual.goforcode.models.ApartmentsUsers;
import com.libertymutual.goforcode.models.User;
import com.libertymutual.goforcode.utilities.AutoCloseableDb;

public class Application {
	
	public static void main(String[] args)	{
		
		String hashed = BCrypt.hashpw("test", BCrypt.gensalt());
		
		try (AutoCloseableDb db = new AutoCloseableDb())	{
			Apartment.deleteAll();
			User.deleteAll();
			ApartmentsUsers.deleteAll();
			User ben = new User("a@b.com", hashed, "Ben", "Brandvig");
			ben.saveIt();
			Apartment apt1 = new Apartment(1500, 1, 0, 350, "123 Main St", "San Francisco", "CA", "95125");
			Apartment apt2 = new Apartment(4000, 5, 6, 4000, "123 Fake St", "Seattle", "FL", "66666");
			ben.add(apt1);
			ben.add(apt2);
			apt1.saveIt();
			apt2.saveIt();
			ApartmentsUsers aptUser = new ApartmentsUsers(apt1.getId(), ben.getId());
			aptUser.saveIt();
		}
		
		before("/*", SecurityFilters.setCsrfToken);
		
		get("/", HomeController.index);
		
		path("/login", () -> {
			before("", SecurityFilters.csrfPostMatch);
			get("", SessionController.newForm);
			post("", SessionController.create);
		});
		
		path("/users", () -> {
			get("/new", UserController.newForm);
			before("", SecurityFilters.csrfPostMatch);
			post("", UserController.create);
		});
		
		path("/apartments", () -> {
			before("/new", SecurityFilters.isAuthenticated);
			get("/new", ApartmentController.newForm);
			
			before("/mine", SecurityFilters.isAuthenticated);
			get("/mine", ApartmentController.index);
			
			get("/:id", ApartmentController.details);
			
			before("", SecurityFilters.csrfPostMatch);
			before("", SecurityFilters.isAuthenticated);
			post("", ApartmentController.create);
			
			before("/:id/like", SecurityFilters.csrfPostMatch);
			before("/:id/like", SecurityFilters.isAuthenticated);
			post("/:id/like", LikeController.like);
			
			before("/:id/activations", SecurityFilters.csrfPostMatch);
			before("/:id/activations", SecurityFilters.isAuthenticated);
			before("/:id/activations", SecurityFilters.isLister);
			post("/:id/activations", ApartmentController.activate);
			
			before("/:id/deactivations", SecurityFilters.csrfPostMatch);
			before("/:id/deactivations", SecurityFilters.isAuthenticated);
			before("/:id/deactivations", SecurityFilters.isLister);
			post("/:id/deactivations", ApartmentController.deactivate);
		});
		
		before("/logout", SecurityFilters.csrfPostMatch);
		post("/logout", SessionController.destroy);
		
		path("/api", ()	-> {
			get("/apartments/:id", ApartmentApiController.details);
			post("/apartments", ApartmentApiController.create);
			post("/users", UserApiController.create);
		});
	}
}
