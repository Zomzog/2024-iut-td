package iut.nantes.exo20.controller

import jakarta.validation.Constraint
import jakarta.validation.Payload
import jakarta.validation.constraints.Min
import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [])
@Min(1)
annotation class PetId(
    val message: String = "Value must be between min and max",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {
}
