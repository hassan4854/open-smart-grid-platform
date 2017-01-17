/**
 * Copyright 2016 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.platform.dlms.cucumber.config.ws.smartmetering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.server.endpoint.adapter.method.MarshallingPayloadMethodProcessor;

import com.alliander.osgp.platform.cucumber.config.ws.BaseWebServiceConfig;
import com.alliander.osgp.platform.dlms.cucumber.config.ApplicationConfiguration;
import com.alliander.osgp.shared.infra.ws.WebServiceTemplateFactory;

@Configuration
public class SmartMeteringInstallationWebServiceConfig extends BaseWebServiceConfig {

    @Autowired
    private ApplicationConfiguration configuration;

    @Bean
    public WebServiceTemplateFactory smartMeteringInstallationManagementWstf() {
        return new WebServiceTemplateFactory(this.smartMeteringInstallationManagementMarshaller(), this.messageFactory(),
                this.baseUri.concat(this.configuration.webserviceTemplateDefaultUriSmartMeteringInstallationManagement), this.webserviceKeystoreType,
                this.webserviceKeystoreLocation, this.webserviceKeystorePassword, this.webServiceTrustStoreFactory(), this.applicationName);
    }

    /**
     * Method for creating the Marshaller for SmartMetering InstallationManagement.
     *
     * @return Jaxb2Marshaller
     */
    @Bean
    public Jaxb2Marshaller smartMeteringInstallationManagementMarshaller() {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        marshaller.setContextPath(this.configuration.contextPathSmartMeteringInstallationManagement);

        return marshaller;
    }

    /**
     * Method for creating the Marshalling Payload Method Processor for
     * SmartMetering InstallationManagement.
     *
     * @return MarshallingPayloadMethodProcessor
     */
    @Bean
    public MarshallingPayloadMethodProcessor smartMeteringInstallationManagementMarshallingPayloadMethodProcessor() {
        return new MarshallingPayloadMethodProcessor(this.smartMeteringInstallationManagementMarshaller(),
                this.smartMeteringInstallationManagementMarshaller());
    }

}
