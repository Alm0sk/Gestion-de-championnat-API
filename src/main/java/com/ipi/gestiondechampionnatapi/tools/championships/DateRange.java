package com.ipi.gestiondechampionnatapi.tools.championships;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation pour valider que la date de début est inférieure ou égale à la date de fin.
 */
@Constraint(validatedBy = ChampionshipDateRange.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateRange {
    String message() default "La date de début doit être inférieur ou égale à la date de fin";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}