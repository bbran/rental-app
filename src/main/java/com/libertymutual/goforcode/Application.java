package com.libertymutual.goforcode;

import static spark.Spark.*;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.security.DefaultAuthenticatorFactory;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.libertymutual.goforcode.controllers.ApartmentController;
import com.libertymutual.goforcode.controllers.HomeController;
import com.libertymutual.goforcode.controllers.SessionController;
import com.libertymutual.goforcode.utilities.MustacheRenderer;

public class Application {
	
	public static void main(String[] args)	{
		
		get("/", HomeController.index);
		get("/apartments/:id", ApartmentController.details);
		get("/login", SessionController.newForm);
		post("/login", SessionController.create);

	}

}
