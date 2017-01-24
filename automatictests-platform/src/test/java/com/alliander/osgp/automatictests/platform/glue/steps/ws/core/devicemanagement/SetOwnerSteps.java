/**
 * Copyright 2017 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.automatictests.platform.glue.steps.ws.core.devicemanagement;

import static com.alliander.osgp.automatictests.platform.core.Helpers.getString;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.alliander.osgp.adapter.ws.schema.admin.devicemanagement.SetOwnerRequest;
import com.alliander.osgp.adapter.ws.schema.admin.devicemanagement.SetOwnerResponse;
import com.alliander.osgp.automatictests.platform.Defaults;
import com.alliander.osgp.automatictests.platform.Keys;
import com.alliander.osgp.automatictests.platform.StepsBase;
import com.alliander.osgp.automatictests.platform.core.ScenarioContext;
import com.alliander.osgp.automatictests.platform.support.ws.admin.AdminDeviceManagementClient;
import com.alliander.osgp.domain.core.entities.DeviceAuthorization;
import com.alliander.osgp.domain.core.repositories.DeviceAuthorizationRepository;
import com.alliander.osgp.domain.core.repositories.DeviceRepository;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SetOwnerSteps extends StepsBase {

    @Autowired
    private DeviceAuthorizationRepository deviceAuthorizationRepo;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private AdminDeviceManagementClient client;

    @When("^receiving a set owner request(?: over OSGP)?$")
    public void receivingAFindDevicesRequest(final Map<String, String> requestParameters) throws Throwable {
        final SetOwnerRequest request = new SetOwnerRequest();

        request.setDeviceIdentification(
                getString(requestParameters, Keys.DEVICE_IDENTIFICATION, Defaults.DEVICE_IDENTIFICATION));
        request.setOrganisationIdentification(getString(requestParameters, Keys.ORGANIZATION_IDENTIFICATION,
                Defaults.ORGANIZATION_IDENTIFICATION));

        ScenarioContext.Current().put(Keys.RESPONSE, this.client.setOwner(request));
    }

    @Then("^the set owner async response contains$")
    public void theFindDevicesResponseContainsDevices(final Map<String, String> expectedDevice) throws Throwable {
        Assert.assertTrue(ScenarioContext.Current().get(Keys.RESPONSE) instanceof SetOwnerResponse);
        final SetOwnerResponse response = (SetOwnerResponse) ScenarioContext.Current().get(Keys.RESPONSE);
        Assert.assertNotNull(response);
    }

    @Then("^the owner of device \"([^\"]*)\" has been changed$")
    public void theFindDevicesResponseContainsAtIndex(final String deviceIdentification,
            final Map<String, String> expectedOrganization) throws Throwable {

        final List<DeviceAuthorization> deviceAuthorization = this.deviceAuthorizationRepo
                .findByDevice(this.deviceRepository.findByDeviceIdentification(deviceIdentification));

        Assert.assertEquals(
                getString(expectedOrganization, Keys.ORGANIZATION_IDENTIFICATION,
                        Defaults.ORGANIZATION_IDENTIFICATION),
                deviceAuthorization.get(0).getOrganisation().getOrganisationIdentification());

    }
}
