package com.pyr.app.executors;

import org.springframework.stereotype.Component;

import com.pyr.app.engines.Engine;
import com.pyr.app.engines.EngineF;
import com.pyr.app.exceptions.EngineNotFoundException;
import com.pyr.app.model.ExecutableJob;

@Component
public class Executor {
	
	
	public void execute(ExecutableJob job) throws EngineNotFoundException, Exception {
		
		Engine engine = EngineF.getEngine(job.getJobEngine());
		if(engine == null) {
			throw new EngineNotFoundException("Not supported script engine");
		}	
		engine.executeScript(job.getJobId(), job.getJobAction().trim());
		
	}

}
