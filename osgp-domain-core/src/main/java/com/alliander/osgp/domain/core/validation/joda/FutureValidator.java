package com.alliander.osgp.domain.core.validation.joda;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Future;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadableInstant;

public class FutureValidator implements ConstraintValidator<Future, ReadableInstant> {

    @Override
    public void initialize(final Future constraintAnnotation) {
        // Empty Method
    }

    @Override
    public boolean isValid(final ReadableInstant value, final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        final DateTime checkDate = new DateMidnight(DateTimeZone.UTC).toDateTime();

        return value.isEqual(checkDate) || value.isAfter(checkDate);
    }

}
