package com.pyr.app.engines;

public class EngineF {
	
	public static Engine getEngine(String type) {
		Engine engine = null;
		if(type.equalsIgnoreCase("python")) {
			engine = new PythonEngine();
		}
		
		if(type.equalsIgnoreCase("javascript")) {
			engine = new JavascriptEngine();
		}
		
		return engine;
	}
}
