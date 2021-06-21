package com.pyr.app.engines;

import javax.script.ScriptException;

public interface Engine {
	public void executeScript(String jobId, String execCode) throws Exception;
}
