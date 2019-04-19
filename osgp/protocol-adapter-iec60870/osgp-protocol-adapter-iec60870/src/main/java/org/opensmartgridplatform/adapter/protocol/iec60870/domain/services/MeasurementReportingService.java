/**
 * Copyright 2019 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package org.opensmartgridplatform.adapter.protocol.iec60870.domain.services;

import org.opensmartgridplatform.adapter.protocol.iec60870.domain.valueobjects.ResponseInfo;
import org.opensmartgridplatform.dto.da.measurements.MeasurementReportDto;

@FunctionalInterface
public interface MeasurementReportingService {
    /**
     * Send a measurement report.
     *
     * @param measurementReportDto
     *            The {@link MeasurementReportDto} instance to send.
     * @param responseInfo
     *            The {@link ResponseInfo} instance.
     */
    void send(MeasurementReportDto measurementReportDto, ResponseInfo responseInfo);
}
