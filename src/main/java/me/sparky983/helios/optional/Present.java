package me.sparky983.helios.optional;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An {@code Optional} that contains a value.
 *
 * @param value the value
 * @param <T> the type of the value
 * @since 0.1.0
 * @helios.apiNote This class should not be constructed directly and is only public for pattern
 * matching. Use {@link #of(Object)} instead.
 */
public record Present<T extends @NonNull Object>(T value) implements Optional<T> {

    /**
     * Constructs a new {@code Present} {@code Optional} with the specified value.
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
    public Optional<T> or(final Supplier<? extends Optional<? extends T>> otherGetter) {

        Objects.requireNonNull(otherGetter, "otherGetter cannot be null");

        return this;
    }

    @Override
    public T orDefault(final T defaultValue) {

        Objects.requireNonNull(defaultValue, "defaultValue cannot be null");

        return value;
    }

    @Override
    public <M extends @NonNull Object> Optional<M> map(
            final Function<? super T, ? extends M> mapper) {

        Objects.requireNonNull(mapper, "mapper cannot be null");

        final var mappedValue = Objects.requireNonNull(
                mapper.apply(value),
                "mapper cannot return null");

        return Optional.of(mappedValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <M extends @NonNull Object> Optional<M> flatMap(
            final Function<? super T, ? extends Optional<? extends M>> mapper) {

        Objects.requireNonNull(mapper, "mapper cannot be null");

        final var mappedValue = Objects.requireNonNull(
                mapper.apply(value),
                "mapper cannot return null");

        return (Optional<M>) mappedValue;
    }

    @Override
    public String toString() {

        return "Optional.of(" + value + ")";
    }
}