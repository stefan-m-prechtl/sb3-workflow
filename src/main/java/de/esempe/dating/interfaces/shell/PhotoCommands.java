package de.esempe.dating.interfaces.shell;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import de.esempe.dating.core.photo.PhotoService;

@ShellComponent
class PhotoCommands
{
	private final PhotoService service;

	PhotoCommands(final PhotoService service)
	{
		this.service = service;
	}

	@ShellMethod(value = "Bild anzeigen")
	String showPhoto(final String name)
	{
		return this.service.download(name).map(bytes -> {
			try
			{
				final BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
				return "Breite x Höhe: " + image.getWidth() + " x " + image.getHeight();
			}
			catch (final IOException e)
			{
				return "Photo nicht lesbar";
			}
		}).orElse("Photo nicht verfügbar");
	}
}
