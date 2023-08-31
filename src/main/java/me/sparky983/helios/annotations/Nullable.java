package me.sparky983.helios.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated type is may contain {@code null}.
 *
 * <h2>JSpecify</h2>
 * This annotation is temporary, and will be replaced with
 * {@code org.jspecify.annotations.Nullable} once <a href="http://jspecify.org/">JSpecify</a>
 * is fully released.
 *
 * @since 0.1
 */
@Experimental
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE_USE)
public @interface Nullable {

}
