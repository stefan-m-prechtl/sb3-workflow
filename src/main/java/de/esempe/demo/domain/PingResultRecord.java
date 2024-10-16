package de.esempe.demo.domain;

import java.time.LocalDateTime;

public record PingResultRecord(LocalDateTime ts, String msg)
{
}
