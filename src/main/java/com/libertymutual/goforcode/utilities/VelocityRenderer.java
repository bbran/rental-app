package com.libertymutual.goforcode.utilities;

import java.util.Map;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class VelocityRenderer {
	
	private static final VelocityRenderer instance = new VelocityRenderer();
	private VelocityTemplateEngine engine;
	
	private VelocityRenderer()	{
		engine = new VelocityTemplateEngine();
	}
	
	public static VelocityRenderer getInstance()	{
		return instance;
	}
	
	public String render(Map<String, Object> model, String templatePath)	{
		return engine.render(new ModelAndView(model, "templates/" + templatePath));
	}

}
