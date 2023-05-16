package me.sparky983.helios.optional;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * An immutable container which may contain a non-null value.
 * <p>
 * For a more user-friendly guide instead of API specification, see the
 * <a href="package-summary">package summary</a>.
 * <p>
 * If the {@code Optional} contains a value, it is represented by a {@link Present} containing the
 * value, otherwise it is represented by an {@link Absent}.
 *
 * @param <T> the type of the value
 * @since 0.1.0
 * @helios.apiNote Unlike {@link java.util.Optional java.util.Optional}, this {@code Optional} type
 * is intended to be an <a href="https://en.wikipedia.org/wiki/Option_type">option type</a> meaning
 * it is a replacement for {@code null}. Therefore, it is fine to use this type on parameters and
 * fields.
 * <p>
 * This interface is sealed and the implementations are records, so you can easily combine switch
 * pattern matching and record patterns (Java 21+ features) to handle the different cases:
 * <pre>{@code  Optional<String> optional = ...;
 * switch (optional) {
 *     case Present(String value) -> System.out.println("Present: " + value);
 *     case Absent() -> System.out.println("Absent");
 * }}</pre>
 */
public sealed interface Optional<T extends @NonNull Object> permits Present, Absent {

    /**
     * Returns an absent {@code Optional}.
     *
     * @param <T> the type of the value
     * @return an absent {@code Optional}
     * @helios.implNote This method returns a singleton instance of {@link Absent}.
     */
    @SuppressWarnings("unchecked")
    static <T extends @NonNull Object> Optional<T> absent() {

        return (Absent<T>) Absent.ABSENT;
    }

    /**
     * Returns a present {@code Optional} containing the given value.
     *
     * @param value the value
     * @return a present {@code Optional} containing the specified value
     * @param <T> the type of the value
     * @throws NullPointerException if the value is {@code null}.
     */
    static <T extends @NonNull Object> Optional<T> of(final T value) {

        return new Present<>(value);
    }

    /**
     * Returns an {@code Optional} containing the given value if it is non-null, otherwise returns
     * an absent {@code Optional}.
     * <p>
     * This method is equivalent to {@code value == null ? Optional.absent() : Optional.of(value)}.
     *
     * @param value the value
     * @return an {@code Optional} containing the given value if it is non-null, otherwise an absent
     * {@code Optional}
     * @param <T> the type of the value
     * @helios.apiNote This method is intended to improve interoperability with null-based APIs.
     * @helios.examples
     * <pre>{@code  Map<String, String> map = ...;
     * Optional<String> optional = Optional.fromNullable(map.get("key"));}</pre>
     */
    static <T extends @NonNull Object> Optional<T> fromNullable(final @Nullable T value) {

        if (value != null) {
            return of(value);
        } else {
            return absent();
        }
    }

    /**
     * Returns an {@code Optional} converted from a {@link java.util.Optional java.util.Optional}.
     *
     * @param optional the {@code java.util.Optional}
     * @return an {@code Optional} converted from the specified {@code java.util.Optional}
     * @param <T> the type of the value
     * @throws NullPointerException if the {@code java.util.Optional} is {@code null}.
     * @helios.apiNote This method is intended to improve interoperability with
     * {@code java.util.Optional}.
     * @helios.examples
     * <pre>{@code  java.util.Optional<String> javaOptional = ...;
     * Optional<String> optional = Optional.fromJavaOptional(optional);}</pre>
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    static <T extends @NonNull Object> Optional<T> fromJavaOptional(
            final java.util.Optional<T> optional) {

        return optional.<Optional<T>>map(Optional::of).orElse(Optional.absent());
    }

    /**
     * Checks whether this {@code Optional} is present.
     *
     * @return {@code true} if this {@code Optional} is present, otherwise {@code false}
     * @helios.examples
     * <pre>{@code  Optional<Integer> present = Optional.of(5);
     * assert optional.isPresent();
     *
     * Optional<Integer> absent = Optional.absent();
     * assert !absent.isPresent();}</pre>
     */
    boolean isPresent();

    /**
     * Checks whether this {@code Optional} is absent.
     *
     * @return {@code true} if this {@code Optional} is absent, otherwise {@code false}
     * @helios.examples
     * <pre>{@code  Optional<Integer> absent = Optional.absent();
     * assert absent.isAbsent();
     *
     * Optional<Integer> present = Optional.of(5);
     * assert !optional.isAbsent();}</pre>
     */
    boolean isAbsent();

    /**
     * Returns this {@code Optional} if it is present, otherwise the specified {@code Optional}.
     *
     * @param other the {@code Optional} to fall back to
     * @return this {@code Optional} if it is present, otherwise the specified {@code Optional}
     * @throws NullPointerException if the specified optional is {@code null}.
     * @helios.examples
     * <pre>{@code  Optional<Integer> present = Optional.of(5);
     * assert present.or(Optional.of(10)).equals(optional);
     *
     * Optional<Integer> absent = Optional.absent();
     * assert absent.or(Optional.of(10)).equals(Optional.of(10));}</pre>
     */
    Optional<T> or(Optional<? extends T> other);

    /**
     * Returns this {@code Optional} if it is present, otherwise the return value of the specified
     * {@code Optional} getter.
     *
     * @param otherGetter the {@code Optional} getter to fall back to
     * @return this {@code Optional} if it is present, otherwise the return value of the specified
     * {@code Optional} getter
     * @throws NullPointerException if the optional getter is {@code null} or returns {@code null}.
     * @helios.examples
     * <pre>{@code  Optional<String> findCachedUser(String username) { ... }
     * Optional<String> findUser(String username) { ... }
     *
     * Optional<String> user = findCachedUser("sparky983")
     *         .or(() -> findUser("sparky983"));}</pre>
     */
    Optional<T> or(Supplier<? extends Optional<? extends T>> otherGetter);

    /**
     * Returns the value of this {@code Optional} if it is present, otherwise the specified default
     * value.
     *
     * @param defaultValue the default value to fall back to
     * @return the value of this {@code Optional} if it is present, otherwise the specified
     * default value
     * @throws NullPointerException if the default value is {@code null}.
     * @helios.examples
     * <pre>{@code  Optional<Integer> present = Optional.of(5);
     * assert present.orDefault(10) == 5;
     *
     * Optional<Integer> absent = Optional.absent();
     * assert absent.orDefault(10) == 10;}</pre>
     */
    T orDefault(T defaultValue);

    /**
     * Returns the value of this {@code Optional} if it is present, otherwise the value returned by
     * the default value getter.
     *
     * @param defaultValueGetter the default value getter to fall back to
     * @return the value of this {@code Optional} if it is present, otherwise the value returned by
     * the default value getter
     * @throws NullPointerException if the default value getter is {@code null} or returns
     * {@code null}.
     * @helios.examples
     * <pre>{@code  Optional<Integer> present = Optional.of(5);
     * assert present.orGet(() -> 10) == 5;
     *
     * Optional<Integer> absent = Optional.absent();
     * assert absent.orGet(() -> 10) == 10;}</pre>
     */
    T orGet(Supplier<? extends T> defaultValueGetter);

    /**
     * Returns the value of this {@code Optional} if it is present, otherwise {@code null}.
     *
     * @return the value of this {@code Optional} if it is present, otherwise {@code null}
     * @helios.apiNote This method is intended to improve interoperability with null-based APIs.
     * @helios.examples
     * <pre>{@code  String number = Optional.absent()
     *     .map(Object::toString)
     *     .orNull();}</pre>
     */
    @Nullable T orNull();

    /**
     * If this {@code Optional} is present, returns an {@code Optional} containing the value of this
     * {@code Optional} after applying the mapping function to it, otherwise returns an absent
     * {@code Optional}.
     *
     * @param mapper the mapping function
     * @return an {@code Optional} containing the mapped value if this {@code Optional} is present,
     * otherwise an absent {@code Optional}
     * @param <M> the result of the mapping function
     * @throws NullPointerException if the mapping function is {@code null} or returns {@code null}.
     * @helios.examples
     * <pre>{@code  Optional<Integer> present = Optional.of(5);
     * assert present.map(n -> n * 2).equals(Optional.of(10));
     *
     * Optional<Integer> absent = Optional.absent();
     * assert absent.map(n -> n * 2).isAbsent();}</pre>
     */
    <M extends @NonNull Object> Optional<M> map(Function<? super T, ? extends M> mapper);

    /**
     * If the value of this {@code Optional} is present, returns the result of applying the mapping
     * function to the value of this {@code Optional}, otherwise returns an absent {@code Optional}.
     *
     * @param mapper the mapping function
     * @return an {@code Optional} containing the result of applying the mapping function to the
     * value of this {@code Optional} if it is present, otherwise an absent {@code Optional}.
     * @param <M> the result of the mapping function
     * @throws NullPointerException if the mapping function is {@code null} or returns {@code null}.
     * @helios.examples
     * <pre>{@code  Optional<User> findUser(String username) { ... }
     *
     * Optional<Repository> repository = findUser("Sparky983")
     *        .flatMap(user -> user.findRepository("helios"));}</pre>
     */
    <M extends @NonNull Object> Optional<M> flatMap(
            Function<? super T, ? extends Optional<? extends M>> mapper);

    /**
     * If this {@code Optional} is present, returns this {@code Optional} if the value matches the
     * given predicate, otherwise returns an absent {@code Optional}.
     *
     * @param predicate the predicate to apply to the value
     * @return this {@code Optional} if the value matches the given predicate, otherwise an absent
     * {@code Optional}
     * @throws NullPointerException if the predicate is {@code null}.
     * @helios.examples
     * <pre>{@code  Optional<Integer> present = Optional.of(5);
     * assert present.filter(n -> n % 2 == 1).equals(present);
     * assert present.filter(n -> n % 2 == 0).isAbsent();}</pre>
     */
    Optional<T> filter(Predicate<? super T> predicate);

    /**
     * Checks whether this {@code Optional} is equal to the specified object.
     *
     * @param obj the object to compare to
     * @return {@code true} if this {@code Optional}'s value is {@link Object#equals(Object) equal}
     * to the specified object or both are absent, otherwise {@code false}
     */
    @Override
    boolean equals(@Nullable Object obj);

    /**
     * Returns the hash code of this {@code Optional}'s value, or {@code 0} if it is absent.
     *
     * @return the hash code of this {@code Optional}'s value, or {@code 0} if it is absent
     */
    @Override
    int hashCode();
}
