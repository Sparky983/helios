package me.sparky983.helios.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated type is may contain {@code null}.
 * <h2><a href="http://jspecify.org/">JSpecify</a></h2>
 * This annotation generally has the same meaning as {@code org.jspecify.annotations.Nullable}
 * in JSpecify 0.3, however, it is not well-defined as the meaning may change as JSpecify evolves.
 * Additionally, this entire module (me.sparky983.helios) is considered to be in a null-marked
 * scope.
 *
 * <p>This annotation is also temporary, and will be replaced with
 * {@code org.jspecify.annotations.Nullable} once is fully released.
 */
@Experimental
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE_USE)
public @interface Nullable {}
