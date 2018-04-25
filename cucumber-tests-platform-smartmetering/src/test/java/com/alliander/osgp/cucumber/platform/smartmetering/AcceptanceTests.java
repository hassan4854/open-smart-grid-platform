/**
 * Copyright 2017 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.cucumber.platform.smartmetering;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "classpath:features/functional-exceptions", "classpath:features/osgp-adapter-ws-core",
        "classpath:features/osgp-adapter-ws-smartmetering" }, tags = { "@test", "~@NightlyBuildOnly" }, glue = {
                "classpath:com.alliander.osgp.cucumber.platform.glue",
                "classpath:com.alliander.osgp.cucumber.platform.common.glue",
                "classpath:com.alliander.osgp.cucumber.platform.smartmetering.glue" }, plugin = { "pretty",
                        "html:target/output/Cucumber-report", "html:target/output/Cucumber-html-report.html",
                        "json:target/output/cucumber.json" }, snippets = SnippetType.CAMELCASE, dryRun = false)
public class AcceptanceTests {

}
