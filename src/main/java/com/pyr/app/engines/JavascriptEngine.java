package com.pyr.app.engines;

import java.io.StringWriter;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JavascriptEngine implements Engine {

	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine engine = manager.getEngineByName("JavaScript");
	
	private Logger logger = LogManager.getLogger(JavascriptEngine.class);


	public void executeScript(String jobId, String execCode) throws ScriptException {
		StringWriter writer = new StringWriter();
	    ScriptContext context = new SimpleScriptContext();
	    context.setWriter(writer);
		engine.eval(execCode,context);
		
		logger.info("Output for Job Id " + jobId + " : " + writer.toString());
		
	}

}
