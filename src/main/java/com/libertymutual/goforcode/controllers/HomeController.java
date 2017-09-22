package com.libertymutual.goforcode.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.libertymutual.goforcode.models.Apartment;
import com.libertymutual.goforcode.utilities.AutoCloseableDb;
import com.libertymutual.goforcode.utilities.MustacheRenderer;
import spark.Request;
import spark.Response;
import spark.Route;

public class HomeController {
	public static final Route index = (Request req, Response res) -> {
		try (AutoCloseableDb db = new AutoCloseableDb()) {
			List<Apartment> apartments = Apartment.where("is_active = ?", true);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("activeApartments", apartments);
			model.put("currentUser", req.session().attribute("currentUser"));
			model.put("noUser", req.session().attribute("currentUser") == null);
			model.put("csrf", req.session().attribute("csrfToken"));
			return MustacheRenderer.getInstance().render("home/index.html", model);
			// return VelocityRenderer.getInstance().render(model, "home/index2.html");
		}
	};

}
