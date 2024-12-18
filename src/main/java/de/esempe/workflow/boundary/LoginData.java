package de.esempe.workflow.boundary;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Datenklasse für Login-Daten. Konvertierung von/zu Json ohne Json-Adapter! --> Änderung der Attribute ändert REST-Schnittstelle!
 *
 * @author Stefan Prechtl
 *
 */
public final class LoginData
{
	@NotEmpty
	@Size(min = 3, max = 10)
	private String user;
	@NotEmpty
	private String passwd;

	public String getUser()
	{
		return this.user;
	}

	public void setUser(final String user)
	{
		this.user = user;
	}

	public String getPasswd()
	{
		return this.passwd;
	}

	public void setPasswd(final String passwd)
	{
		this.passwd = passwd;
	}

}
