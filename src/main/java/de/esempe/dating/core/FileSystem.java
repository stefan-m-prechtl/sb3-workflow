package de.esempe.dating.core;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class FileSystem
{
	private final Path root = Paths.get(System.getProperty("user.home")).resolve("fs");

	public FileSystem()
	{
		try
		{
			if (!Files.isDirectory(this.root))
			{
				Files.createDirectories(this.root);
			}
		}
		catch (final IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	public long getFreeDiskSpace()
	{
		return this.root.toFile().getFreeSpace();
	}

	public byte[] load(final String filename)
	{
		try
		{
			return Files.readAllBytes(this.root.resolve(filename));
		}
		catch (final IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	public void store(final String filename, final byte[] bytes)
	{
		try
		{
			Files.write(this.root.resolve(filename), bytes);
		}
		catch (final IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}
}
