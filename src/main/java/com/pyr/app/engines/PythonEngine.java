package com.pyr.app.engines;

import java.io.FileReader;
import java.io.StringWriter;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PythonEngine implements Engine {

	ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("python");
    
	private Logger logger = LogManager.getLogger(PythonEngine.class);

	

	public void executeScript(String jobId, String execCode) throws Exception {
		
		StringWriter writer = new StringWriter();
	    ScriptContext context = new SimpleScriptContext();
	    context.setWriter(writer);
	    
	    
		
	    engine.eval(new FileReader(execCode), context);

	    logger.info("Output for Job Id " + jobId + " : " + writer.toString());
	}

}
