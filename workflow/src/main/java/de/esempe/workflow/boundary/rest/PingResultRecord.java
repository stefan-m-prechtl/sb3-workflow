package de.esempe.workflow.boundary.rest;

import java.time.LocalDateTime;

public record PingResultRecord(LocalDateTime ts, String msg)
{
}
