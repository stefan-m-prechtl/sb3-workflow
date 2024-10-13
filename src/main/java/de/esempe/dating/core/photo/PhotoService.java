package de.esempe.dating.core.photo;

import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import de.esempe.dating.core.FileSystem;

@Service
public class PhotoService
{
	private FileSystem fs;

	private Thumbnail thumbnail;

	PhotoService(final FileSystem fs, final Thumbnail thumbnail)
	{
		this.fs = fs;
		this.thumbnail = thumbnail;
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

	public String upload(final byte[] imageBytes)
	{
		final String imageName = UUID.randomUUID().toString();

		// First: store original image
		this.fs.store(imageName + ".jpg", imageBytes);
		// Second: store thumbnail
		final byte[] thumbnailBytes = this.thumbnail.thumbnail(imageBytes);
		this.fs.store(imageName + "thumb.jpg", thumbnailBytes);

		return imageName;
	}
}
