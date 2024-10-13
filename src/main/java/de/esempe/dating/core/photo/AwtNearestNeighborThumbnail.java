package de.esempe.dating.core.photo;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import javax.imageio.ImageIO;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class AwtNearestNeighborThumbnail implements Thumbnail
{
	private static BufferedImage create(final BufferedImage source, int width, int height)
	{
		final double thumbRatio = (double) width / height;
		final double imageRatio = (double) source.getWidth() / source.getHeight();
		if (thumbRatio < imageRatio)
		{
			height = (int) (width / imageRatio);
		}
		else
		{
			width = (int) (height * imageRatio);
		}
		final BufferedImage thumb = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2 = thumb.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g2.drawImage(source, 0, 0, width, height, null);
		g2.dispose();
		return thumb;
	}

	@Override
	public byte[] thumbnail(final byte[] imageBytes)
	{
		try (InputStream instream = new ByteArrayInputStream(imageBytes); ByteArrayOutputStream outstream = new ByteArrayOutputStream())
		{
			ImageIO.write(create(ImageIO.read(instream), 200, 200), "jpg", outstream);
			return outstream.toByteArray();
		}
		catch (final IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}
}
