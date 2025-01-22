package iut.nantes.exo20.controller

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import org.springframework.beans.factory.annotation.Value
import kotlin.reflect.KClass

class Range(val minAge: Int?, val maxAge: Int?)
