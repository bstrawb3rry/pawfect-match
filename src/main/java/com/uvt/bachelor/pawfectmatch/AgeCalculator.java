package com.uvt.bachelor.pawfectmatch;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculator {

    public static Integer calculateAge(LocalDate birthdate) {
        if (birthdate == null) {
            return null;
        }
        return Period.between(birthdate, LocalDate.now()).getYears();
    }
}
