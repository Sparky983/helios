package me.sparky983.helios;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jspecify.annotations.Nullable;

/**
 * An {@code Optional} that contains no value.
 *
 * @param <T> the type of the value
 * @since 0.1.0
 * @helios.apiNote This class should not be constructed directly. Use {@link #absent()} instead.
 */
public record Absent<T>() implements Optional<T> {
  static final Absent<?> ABSENT = new Absent<>();

  @Override
  public boolean isPresent() {
    return false;
  }

  @Override
  public boolean isAbsent() {
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Optional<T> or(final Optional<? extends T> other) {
    Objects.requireNonNull(other, "other cannot be null");

    return (Optional<T>) other;
  }

  @Override
  public Optional<T> or(final Supplier<? extends Optional<? extends T>> otherSupplier) {
    Objects.requireNonNull(otherSupplier, "otherSupplier cannot be null");

    final var other =
        Objects.requireNonNull(otherSupplier.get(), "otherSupplier cannot return null");

    return or(other);
  }

  @Override
  public T orDefault(final T defaultValue) {
    Objects.requireNonNull(defaultValue, "defaultValue cannot be null");

    return defaultValue;
  }

  @Override
  public T orGet(final Supplier<? extends T> defaultValueSupplier) {
    Objects.requireNonNull(defaultValueSupplier, "defaultValueSupplier cannot be null");

    final var defaultValue = defaultValueSupplier.get();
    Objects.requireNonNull(defaultValue, "defaultValueSupplier cannot return null");

    return defaultValue;
  }

  @Override
  public @Nullable T orNull() {
    return null;
  }

  @Override
  public <E extends Throwable> T orThrow(final Supplier<? extends E> exceptionSupplier) throws E {
    Objects.requireNonNull(exceptionSupplier, "exceptionSupplier cannot be null");

    final var exception = exceptionSupplier.get();
    Objects.requireNonNull(exception, "exceptionSupplier cannot return null");

    throw exception;
  }

  @Override
  public T expect(final String message) {
    Objects.requireNonNull(message, "message cannot be null");

    throw new NoSuchElementException("Expected " + message);
  }

  @Override
  public <M> Optional<M> map(final Function<? super T, ? extends M> mapper) {
    Objects.requireNonNull(mapper, "mapper cannot be null");

    return Optional.absent();
  }

  @Override
  public <M> Optional<M> flatMap(
      final Function<? super T, ? extends Optional<? extends M>> mapper) {
    Objects.requireNonNull(mapper, "mapper cannot be null");

    return Optional.absent();
  }

  @Override
  public Optional<T> filter(final Predicate<? super T> predicate) {
    Objects.requireNonNull(predicate, "predicate cannot be null");

    return Optional.absent();
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public String toString() {
    return "Absent()";
  }
}
