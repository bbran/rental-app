package com.libertymutual.goforcode.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.libertymutual.goforcode.models.Apartment;
import com.libertymutual.goforcode.models.ApartmentsUsers;
import com.libertymutual.goforcode.models.User;
import com.libertymutual.goforcode.utilities.AutoCloseableDb;
import com.libertymutual.goforcode.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class ApartmentController {
	public static final Route details = (Request req, Response res) -> {
		User currentUser = req.session().attribute("currentUser");
		try (AutoCloseableDb db = new AutoCloseableDb()) {
			Apartment apartment = Apartment.findById(Integer.parseInt(req.params(":id")));
			Map<String, Object> model = new HashMap<String, Object>();
			if (null != currentUser)	{
				model.put("isLister", (long) apartment.get("user_id") == (long) currentUser.get("id"));
				model.put("isLiker", null != ApartmentsUsers.findFirst("apartment_id = ? AND user_id = ?", apartment.getId(), currentUser.getId()));
				model.put("currentUser", currentUser);
				model.put("csrf", req.session().attribute("csrfToken"));
			} else	{
				model.put("noUser", true);
			}
			model.put("likers", apartment.getAll(User.class));
			model.put("listingActive", apartment.get("is_active"));
			model.put("apartment", apartment);
			System.out.println(model.toString());
			return MustacheRenderer.getInstance().render("apartments/details.html", model);
		}
	};
	
	public static final Route newForm = (Request req, Response res) -> {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("currentUser", req.session().attribute("currentUser"));
		model.put("noUser", req.session().attribute("currentUser") == null);
		model.put("csrf", req.session().attribute("csrfToken"));
		return MustacheRenderer.getInstance().render("apartments/newForm.html", model);
	};
	
	public static final Route create = (Request req, Response res) -> {
		User user = req.session().attribute("currentUser");
		Long userId = (Long) user.getId();
		Apartment apartment = new Apartment(
					Integer.parseInt(req.queryParams("rent")),
					Integer.parseInt(req.queryParams("number_of_bedrooms")),
					Integer.parseInt(req.queryParams("number_of_bathrooms")),
					Integer.parseInt(req.queryParams("square_footage")),
					req.queryParams("address"),
					req.queryParams("city"),
					req.queryParams("state"),
					req.queryParams("zip_code")
				);
		apartment.setUserId(userId);
		apartment.setIsActive(true);
		try (AutoCloseableDb db = new AutoCloseableDb()) {
			apartment.saveIt();
			res.redirect("/apartments/mine");
			return "";
		}
	};

	public static final Route index = (Request req, Response res) -> {
		User user = req.session().attribute("currentUser");
		Long userId = (Long) user.getId();
		try (AutoCloseableDb db = new AutoCloseableDb())	{
			List<Apartment> activeApartments = Apartment.where("user_id = ? AND is_active = ?", userId, true);
			List<Apartment> inactiveApartments = Apartment.where("user_id = ? AND is_active = ?", userId, false);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("currentUser", req.session().attribute("currentUser"));
			model.put("noUser", req.session().attribute("currentUser") == null);
			model.put("activeApartments", activeApartments);
			model.put("inactiveApartments", inactiveApartments);
			model.put("csrf", req.session().attribute("csrfToken"));
			return MustacheRenderer.getInstance().render("apartments/index.html", model);
		}
	};

	public static final Route activate = (Request req, Response res) -> {
		try (AutoCloseableDb db = new AutoCloseableDb())	{
			Apartment apartment = Apartment.findById(Integer.parseInt(req.params("id")));
			apartment.setIsActive(true);
			apartment.saveIt();
			res.redirect("/apartments/" + req.params("id"));
			return "";
		}
	};
	
	public static final Route deactivate = (Request req, Response res) -> {
		try (AutoCloseableDb db = new AutoCloseableDb())	{
			Apartment apartment = Apartment.findById(Integer.parseInt(req.params("id")));
			apartment.setIsActive(false);
			apartment.saveIt();
			res.redirect("/apartments/" + req.params("id"));
			return "";
		}
	};

}
