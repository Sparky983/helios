package me.sparky983.helios.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.lang.model.element.TypeElement;

/**
 * Indicates that the annotated element may be subject to backwards-incompatible changes without
 * notice. A backwards-incompatible change refers to any change that may cause existing code,
 * whether that be source code or bytecode to stop working.
 *
 * <p>If a {@link ElementType#MODULE}, {@link ElementType#PACKAGE} or {@link ElementType#TYPE} are
 * annotated with this annotation, all {@linkplain TypeElement#getEnclosedElements() enclosed
 * elements} are additionally considered experimental.
 * <h2>Conventions</h2>
 * When an element is marked as experimental, but also enclosed by at least one experimental
 * element, the annotation indicates that the annotated element will most likely remain
 * experimental even after the enclosing element is stabilised (this annotation is removed
 * from the enclosing element).
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({
  ElementType.CONSTRUCTOR,
  ElementType.METHOD,
  ElementType.MODULE,
  ElementType.PACKAGE,
  ElementType.TYPE
})
public @interface Experimental {}
