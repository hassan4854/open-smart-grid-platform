package com.alliander.osgp.platform.cucumber.steps.ws.core.deviceinstallation;

import static com.alliander.osgp.platform.cucumber.core.Helpers.getString;
import static com.alliander.osgp.platform.cucumber.core.Helpers.saveCorrelationUidInScenarioContext;

import java.util.Map;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.alliander.osgp.adapter.ws.schema.core.common.AsyncRequest;
import com.alliander.osgp.adapter.ws.schema.core.deviceinstallation.StartDeviceTestAsyncRequest;
import com.alliander.osgp.adapter.ws.schema.core.deviceinstallation.StartDeviceTestAsyncResponse;
import com.alliander.osgp.adapter.ws.schema.core.deviceinstallation.StartDeviceTestRequest;
import com.alliander.osgp.adapter.ws.schema.core.deviceinstallation.StartDeviceTestResponse;
import com.alliander.osgp.platform.cucumber.config.CorePersistenceConfig;
import com.alliander.osgp.platform.cucumber.core.ScenarioContext;
import com.alliander.osgp.platform.cucumber.steps.Defaults;
import com.alliander.osgp.platform.cucumber.steps.Keys;
import com.alliander.osgp.platform.cucumber.support.ws.core.CoreDeviceInstallationClient;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StartDeviceSteps {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(StartDeviceSteps.class);

	@Autowired
	private CorePersistenceConfig configuration;

	@Autowired
    private CoreDeviceInstallationClient client;
	
    /**
     * 
     * @param requestParameters
     * @throws Throwable
     */
    @When("receiving a start device test request")
    public void receiving_a_start_device_test_request(final Map<String, String> requestParameters) throws Throwable
    {
    	StartDeviceTestRequest request = new StartDeviceTestRequest();
    	request.setDeviceIdentification(getString(requestParameters, Keys.KEY_DEVICE_IDENTIFICATION, Defaults.DEFAULT_DEVICE_IDENTIFICATION));
    	
    	try {
    		ScenarioContext.Current().put(Keys.RESPONSE, client.startDeviceTest(request));
    	} catch(SoapFaultClientException ex) {
    		ScenarioContext.Current().put(Keys.RESPONSE, ex);
    	}
    }
    
    /**
     * 
     * @param expectedResponseData
     * @throws Throwable
     */
    @Then("the start device async response contains")
    public void the_start_device_async_response_contains(final Map<String, String> expectedResponseData) throws Throwable
    {
    	StartDeviceTestAsyncResponse response = (StartDeviceTestAsyncResponse)ScenarioContext.Current().get(Keys.RESPONSE);
    	
    	Assert.assertNotNull(response.getAsyncResponse().getCorrelationUid());
    	Assert.assertEquals(getString(expectedResponseData,  Keys.KEY_DEVICE_IDENTIFICATION), response.getAsyncResponse().getDeviceId());

        // Save the returned CorrelationUid in the Scenario related context for further use.
        saveCorrelationUidInScenarioContext(response.getAsyncResponse().getCorrelationUid(),
                getString(expectedResponseData, Keys.KEY_ORGANIZATION_IDENTIFICATION, Defaults.DEFAULT_ORGANIZATION_IDENTIFICATION));

        LOGGER.info("Got CorrelationUid: [" + ScenarioContext.Current().get(Keys.KEY_CORRELATION_UID) + "]");
    }
    
    /**
     * 
     * @param deviceIdentification
     * @throws Throwable
     */
    @Then("the platform buffers a start device response message for device \"([^\"]*)\"")
    public void the_platform_buffers_a_start_device_response_message_for_device(final String deviceIdentification, final Map<String, String> expectedResult) throws Throwable
    {
    	StartDeviceTestAsyncRequest request = new StartDeviceTestAsyncRequest();
    	AsyncRequest asyncRequest = new AsyncRequest();
    	asyncRequest.setDeviceId(deviceIdentification);
    	asyncRequest.setCorrelationUid((String) ScenarioContext.Current().get(Keys.KEY_CORRELATION_UID));
    	request.setAsyncRequest(asyncRequest);
    	
    	boolean success = false;
    	int count = 0;
    	while (!success) {
    		if (count > configuration.getDefaultTimeout()) {
    			Assert.fail("Timeout");
    		}
    		
    		count++;
    		
    		try {
    			StartDeviceTestResponse response = client.getStartDeviceTestResponse(request);
    			
    			Assert.assertEquals(expectedResult.get(Keys.KEY_RESULT), response.getResult());
    			
    			success = true; 
    		}
    		catch(Exception ex) {
    			// Do nothing
    		}
    	}
    }
}
