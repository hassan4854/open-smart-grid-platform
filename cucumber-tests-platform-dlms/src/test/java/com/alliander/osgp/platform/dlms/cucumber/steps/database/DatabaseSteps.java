/**
 * Copyright 2016 Smart Society Services B.V.
 */
package com.alliander.osgp.platform.dlms.cucumber.steps.database;

import static com.alliander.osgp.platform.cucumber.core.Helpers.cleanRepoAbstractEntity;

import org.osgp.adapter.protocol.dlms.domain.repositories.DlmsDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 */
public class DatabaseSteps {
	
	@Autowired
	private DlmsDeviceRepository dlmsDeviceRepo;

	/**
	 * Before each scenario dlms related stuff needs to be removed. 
	 */
	public void prepareDatabaseForScenario() {
		// Remove all data from previous scenario.
        cleanRepoAbstractEntity(this.dlmsDeviceRepo);
        
        insertDefaultData();
	}

	/**
	 * This method is used to create default data not directly related to the specific tests.
	 * For example: A default dlms gateway device.
	 */
	private void insertDefaultData() {
		// TODO insert here default devices.
	}

}
