package de.esempe.dating.core.photo;

import java.io.UncheckedIOException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.esempe.dating.core.FileSystem;

@Service
public class PhotoService
{
	private FileSystem fs;

	PhotoService(final FileSystem fs)
	{
		this.fs = fs;
	}

	public Optional<byte[]> download(final String name)
	{
		try
		{
			return Optional.of(this.fs.load(name + ".jpg"));
		}
		catch (final UncheckedIOException e)
		{
			return Optional.empty();
		}
	}
}
