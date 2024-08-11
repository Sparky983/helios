package me.sparky983.helios;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * An {@code Optional} that contains a value.
 *
 * @param value the value
 * @param <T> the type of the value
 * @since 0.1.0
 * @helios.apiNote This class should not be constructed directly and is only public for pattern
 * matching. Use {@link #present(Object)} instead.
 */
public record Present<T>(T value) implements Optional<T> {
  /**
   * Constructs a new {@code Present} {@code Optional} with the given value.
   *
   * @param value the value
   * @throws NullPointerException if the value is {@code null}.
   */
  public Present {
    Objects.requireNonNull(value, "value cannot be null");
  }

  @Override
  public boolean isPresent() {
    return true;
  }

  @Override
  public boolean isAbsent() {
    return false;
  }

  @Override
  public Optional<T> or(final Optional<? extends T> other) {
    Objects.requireNonNull(other, "other cannot be null");

    return this;
  }

  @Override
  public Optional<T> or(final Supplier<? extends Optional<? extends T>> otherSupplier) {
    Objects.requireNonNull(otherSupplier, "otherSupplier cannot be null");

    return this;
  }

  @Override
  public T orDefault(final T defaultValue) {
    Objects.requireNonNull(defaultValue, "defaultValue cannot be null");

    return value;
  }

  @Override
  public T orGet(final Supplier<? extends T> defaultValueSupplier) {
    Objects.requireNonNull(defaultValueSupplier, "defaultValueSupplier cannot be null");

    return value;
  }

  @Override
  public T orNull() {
    return value;
  }

  @Override
  public <E extends Throwable> T orThrow(final Supplier<? extends E> exceptionSupplier) throws E {
    Objects.requireNonNull(exceptionSupplier, "exceptionSupplier cannot be null");

    return value;
  }

  @Override
  public T expect(final String message) {
    Objects.requireNonNull(message, "message cannot be null");

    return value;
  }

  @Override
  public <M> Optional<M> map(final Function<? super T, ? extends M> mapper) {
    Objects.requireNonNull(mapper, "mapper cannot be null");

    final var mappedValue =
        Objects.requireNonNull(mapper.apply(value), "mapper cannot return null");

    return Optional.present(mappedValue);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <M> Optional<M> flatMap(
      final Function<? super T, ? extends Optional<? extends M>> mapper) {
    Objects.requireNonNull(mapper, "mapper cannot be null");

    final var mappedValue =
        Objects.requireNonNull(mapper.apply(value), "mapper cannot return null");

    return (Optional<M>) mappedValue;
  }

  @Override
  public Optional<T> filter(final Predicate<? super T> predicate) {
    Objects.requireNonNull(predicate, "predicate cannot be null");

    if (predicate.test(value)) {
      return this;
    } else {
      return Optional.absent();
    }
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return "Present(" + value + ")";
  }
}
