package de.esempe.workflow.boundary;

import java.time.LocalDateTime;

public record PingResultRecord(LocalDateTime ts, String msg)
{
}
