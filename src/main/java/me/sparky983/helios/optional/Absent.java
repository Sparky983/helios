package me.sparky983.helios.optional;


import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * An {@code Optional} that contains no value.
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
    public Optional<T> or(final Supplier<? extends Optional<? extends T>> otherGetter) {

        Objects.requireNonNull(otherGetter, "otherGetter cannot be null");

        final var other = Objects.requireNonNull(
                otherGetter.get(),
                "otherGetter cannot return null");

        return or(other);
    }

    @Override
    public T orDefault(final T defaultValue) {

        Objects.requireNonNull(defaultValue, "defaultValue cannot be null");

        return defaultValue;
    }

    @Override
    public T orGet(final Supplier<? extends T> defaultValueGetter) {

        Objects.requireNonNull(defaultValueGetter, "defaultValueGetter cannot be null");

        final var defaultValue = defaultValueGetter.get();
        Objects.requireNonNull(defaultValue, "defaultValueGetter cannot return null");

        return defaultValue;
    }

    @Override
    public @Nullable T orNull() {

        return null;
    }

    @Override
    public <M extends @NonNull Object> Optional<M> map(
            final Function<? super T, ? extends M> mapper) {

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
    public Optional<T> filter(final Predicate<? super T> predicate) {

        Objects.requireNonNull(predicate, "predicate cannot be null");

        return Optional.absent();
    }

    @Override
    public String toString() {

        return "Optional.absent()";
    }
}
