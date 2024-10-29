package de.esempe.workflow;

import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("de.esempe.workflow")
@IncludeTags("integration-test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTestSuite
{
	// This class remains empty. The annotations define the suite's behavior.
}
