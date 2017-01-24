/**
 * Copyright 2016 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.automatictests.platform.glue.steps.ws.microgrids.adhocmanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alliander.osgp.adapter.ws.schema.microgrids.adhocmanagement.SetDataAsyncRequest;
import com.alliander.osgp.adapter.ws.schema.microgrids.adhocmanagement.SetDataAsyncResponse;
import com.alliander.osgp.adapter.ws.schema.microgrids.adhocmanagement.SetDataRequest;
import com.alliander.osgp.adapter.ws.schema.microgrids.adhocmanagement.SetDataResponse;
import com.alliander.osgp.automatictests.platform.Defaults;
import com.alliander.osgp.automatictests.platform.Keys;
import com.alliander.osgp.automatictests.platform.StepsBase;
import com.alliander.osgp.automatictests.platform.core.ScenarioContext;
import com.alliander.osgp.automatictests.platform.helpers.SettingsHelper;
import com.alliander.osgp.automatictests.platform.support.ws.microgrids.adhocmanagement.AdHocManagementClient;
import com.alliander.osgp.automatictests.platform.support.ws.microgrids.adhocmanagement.SetDataRequestBuilder;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SetDataSteps extends StepsBase {

    @Autowired
    private AdHocManagementClient client;

    @When("^a set data request is received$")
    public void aSetDataRequestIsReceived(final Map<String, String> requestParameters) throws Throwable {
        final String organizationIdentification = (String) ScenarioContext.Current()
                .get(Keys.ORGANIZATION_IDENTIFICATION, Defaults.ORGANIZATION_IDENTIFICATION);
        ScenarioContext.Current().put(Keys.ORGANIZATION_IDENTIFICATION, organizationIdentification);
        final String userName = (String) ScenarioContext.Current().get(Keys.USER_NAME, Defaults.USER_NAME);
        ScenarioContext.Current().put(Keys.USER_NAME, userName);

        final SetDataRequest setDataRequest = SetDataRequestBuilder.fromParameterMap(requestParameters);
        final SetDataAsyncResponse response = this.client.setDataAsync(setDataRequest);

        ScenarioContext.Current().put(Keys.CORRELATION_UID, response.getAsyncResponse().getCorrelationUid());
    }

    @Then("^the set data response should be returned$")
    public void theSetDataResponseShouldBeReturned(final Map<String, String> responseParameters) throws Throwable {

        final String correlationUid = (String) ScenarioContext.Current().get(Keys.CORRELATION_UID);
        final Map<String, String> extendedParameters = SettingsHelper.addDefault(responseParameters,
                Keys.CORRELATION_UID, correlationUid);

        final SetDataAsyncRequest setDataAsyncRequest = SetDataRequestBuilder.fromParameterMapAsync(extendedParameters);
        final SetDataResponse response = this.client.setData(setDataAsyncRequest);

        final String expectedResult = responseParameters.get(Keys.RESULT);
        assertNotNull("Result", response.getResult());
        assertEquals("Result", expectedResult, response.getResult().name());
    }
}
