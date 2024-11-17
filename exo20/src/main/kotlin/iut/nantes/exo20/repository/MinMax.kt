package iut.nantes.exo20.repository

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import jakarta.validation.constraints.Min
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.reflect.KClass


@Target(allowedTargets = [AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER])
@Retention(RUNTIME)
@Constraint(validatedBy = [MinMaxValidator::class])
//@Constraint(validatedBy = [])
//@Min(value = 0)
//@Max(value = 10)
annotation class MinMax(
    val min: Int,
    val max: Int,
    val message: String = "Value must be between min and max",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

class MinMaxValidator : ConstraintValidator<MinMax, Int> {
    private var min: Int = 0
    private var max: Int = 0

    override fun initialize(annotation: MinMax) {
        min = annotation.min
        max = annotation.max
    }

    override fun isValid(value: Int?, context: ConstraintValidatorContext?): Boolean {
        return value != null &&
                value in min..max
    }

}