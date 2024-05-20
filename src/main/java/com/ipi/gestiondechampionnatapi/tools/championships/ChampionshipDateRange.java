package com.ipi.gestiondechampionnatapi.tools.championships;

import com.ipi.gestiondechampionnatapi.models.Championship;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ChampionshipDateRange  implements ConstraintValidator<DateRange, Championship> {

    /**
     * Vérifie que la date de début championnat est bien inférieure ou égale à la date de fin du championnat
     * @param championship championnat
     * @param context contexte de la validation -> startDate =< endDate
     * @return Boolean -> startDate =< endDate
     */
    @Override
    public boolean isValid(Championship championship , ConstraintValidatorContext context) {
        if (championship.getStartDate() == null || championship.getEndDate() == null) {
            return true;
        }
        return !championship.getStartDate().isAfter(championship.getEndDate());
    }
}
