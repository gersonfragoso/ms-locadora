package com.solutis.locadora.customer_service.driver;

import com.solutis.locadora.customer_service.person.PersonRecordDto;

public record DriverRecordDto(String licenseNumber,
                              PersonRecordDto person) {
}
