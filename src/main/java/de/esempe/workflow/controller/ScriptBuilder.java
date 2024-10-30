package de.esempe.workflow.controller;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngineManager;

import com.google.common.base.Preconditions;

public class ScriptBuilder
{
	ScriptEngineManager manager;
	Compilable engine;

	public ScriptBuilder()
	{
		this.manager = new ScriptEngineManager();
		this.engine = (Compilable) this.manager.getEngineByName("groovy");
	}

	public CompiledScript compileScript(final String groovyScript)
	{
		Preconditions.checkState(null != this.engine, "Keine Groovy-Engine vorhanden");
		try
		{
			final CompiledScript result = this.engine.compile(groovyScript);
			return result;
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
