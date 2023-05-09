package me.sparky983.helios.optional;


import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * An absent {@code Optional}.
 *
 * @param <T> the type of the value
 * @since 0.1.0
 * @helios.apiNote This class should not be constructed directly. Use {@link #absent()} instead.
 */
public record Absent<T extends @NonNull Object>() implements Optional<T> {

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
    public T orDefault(final T defaultValue) {

        Objects.requireNonNull(defaultValue, "defaultValue cannot be null");

        return defaultValue;
    }

    @Override
    public <M extends @NonNull Object> Optional<M> map(final Function<? super T, ? extends M> mapper) {

        Objects.requireNonNull(mapper, "mapper cannot be null");

        return Optional.absent();
    }

    @Override
    public <M extends @NonNull Object> Optional<M> flatMap(
            final Function<? super T, ? extends Optional<? extends M>> mapper) {

        Objects.requireNonNull(mapper, "mapper cannot be null");

        return Optional.absent();
    }

    @Override
    public String toString() {

        return "Optional.absent()";
    }
}
