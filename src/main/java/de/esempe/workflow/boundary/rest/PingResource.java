package de.esempe.workflow.boundary.rest;

import java.time.LocalDateTime;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingResource
{
	private ScriptEngine groovyEngine;

	public PingResource()
	{
		final ScriptEngineManager manager = new ScriptEngineManager();
		this.groovyEngine = manager.getEngineByName("groovy");
	}

	@GetMapping(path = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PingResultRecord> getPingAdmin()
	{
		final var result = this.getPingWithMsg("Ping vom Server für ADMIN");
		return result;

	}

	@GetMapping(path = "/writer", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN') || hasRole('WRITER')")
	public ResponseEntity<PingResultRecord> getPingWriter()
	{
		final var result = this.getPingWithMsg("Ping vom Server für WRITER");
		return result;

	}

	@GetMapping(path = "/reader", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN') || hasRole('WRITER') || hasRole('READER')")
	public ResponseEntity<PingResultRecord> getPingReader()
	{
		final var result = this.getPingWithMsg("Ping vom Server für READER");
		return result;
	}

	private ResponseEntity<PingResultRecord> getPingWithMsg(final String msg)
	{

		final LocalDateTime now = LocalDateTime.now();
		final var body = new PingResultRecord(now, msg);

		final var status = HttpStatus.OK;

		// final var result = ResponseEntity.ok(body);
		final var result = ResponseEntity.status(status).body(body);
		return result;

	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PingResultRecord> getPing()
	{
		try
		{
//			final Document doc = new Document();
//			doc.put("user", "prs");
//			doc.put("pc", "workstation1");
//			doc.put("duration", "4");
//			doc.put("reason", "SW-Installation");
//
//			final String issue = doc.toJson();

			// final String groovyScript = "return new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').format(new java.util.Date())";
			// final String timestamp = (String) this.groovyEngine.eval(groovyScript);
//			final String groovyScript = """
//					import groovy.json.JsonSlurper
//
//					def verifyCustomer(issue)
//					{
//					  	def jsonSlurper = new JsonSlurper()
//					  	def parsedIssue = jsonSlurper.parseText(issue)
//
//					  	// Example verification logic (this can be more complex)
//					  	if (parsedIssue.duration == "4")
//					  	{
//					  		return true
//					  	}
//					  	else
//					  	{
//					  		return false
//					  	}
//					}
//
//					// Call the verification function
//					return verifyCustomer(issue) """;
//
//			final Bindings bindings = new SimpleBindings();
//			bindings.put("issue", issue);

//			final var valid = this.groovyEngine.eval(groovyScript, bindings);

//			final LocalDateTime now = LocalDateTime.now();
//			final var body = new PingResultRecord(now, "Ping vom Server: " + valid.toString());

			final LocalDateTime now = LocalDateTime.now();
			final var body = new PingResultRecord(now, "Ping vom Server");

			final var status = HttpStatus.OK;

			// final var result = ResponseEntity.ok(body);
			final var result = ResponseEntity.status(status).body(body);
			return result;

		}
		catch (final Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

}
