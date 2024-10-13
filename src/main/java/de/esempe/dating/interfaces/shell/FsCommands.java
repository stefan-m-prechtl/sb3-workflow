package de.esempe.dating.interfaces.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.unit.DataSize;

import de.esempe.dating.core.FileSystem;

@ShellComponent
class FsCommands
{
	// @Autowired
	// private FileSystem fs;
	private FileSystem fs;

	// @Autowired
	FsCommands(final FileSystem fs)
	{
		this.fs = fs;
	}

	@ShellMethod(value = "Minimal notwendiger Plattenplatz")
	String minimumFreeDiskSpace()
	{
		return 100 + " GB";
	}

	@ShellMethod(value = "Freier Plattenplatz")
	String freeDiskSpace()
	{
		final long freeSpaceGB = DataSize.ofBytes(this.fs.getFreeDiskSpace()).toGigabytes();
		return freeSpaceGB + " GB";
	}
}
