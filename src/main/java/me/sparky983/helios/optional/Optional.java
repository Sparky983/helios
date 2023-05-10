package me.sparky983.helios.optional;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Function;

/**
 * An immutable container which may contain a non-null value.
 * <p>
 * The container may be in one of two states:
 * <ul>
 *     <li>{@link Present}</li>
 *     <li>{@link Absent}</li>
 * </ul>
 * <p>
 * Present {@code Optional}s contain a non-null value, whereas absent {@code Optional}s do not.
 *
 * @param <T> the type of the value
 * @since 0.1.0
 * @helios.apiNote Unlike {@link java.util.Optional}, this {@code Optional} type is intended to be
 * an option type meaning it is a replacement for {@code null}. Therefore, it is fine to use this
 * type on parameters and fields.
 * <p>
 * This interface is sealed to prevent the creation of additional implementations, but also to allow
 * pattern matching. When combined with record deconstruction, this allows for safe and concise
 * querying of {@code Optional}s.
 * <pre>{@code Optional<String> optional = ...;
 * switch (optional) {
 *     case Present(String value) -> System.out.println("Present: " + value);
 *     case Absent() -> System.out.println("Absent");
 * }
 * }</pre>
 */
public sealed interface Optional<T extends @NonNull Object>
        permits Present, Absent {

    /**
     * Returns an absent {@code Optional}.
     *
     * @param <T> the type of the value
     * @return an absent {@code Optional}
     * @helios.implNote This method returns a singleton instance of {@link Absent}.
     */
    @SuppressWarnings("unchecked")
    static <T extends @NonNull Object> Absent<T> absent() {

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
    static <T extends @NonNull Object> Present<T> of(final T value) {

        return new Present<>(value);
    }

    /**
     * Checks whether this {@code Optional} is present.
     *
     * @return {@code true} if this {@code Optional} is present, otherwise {@code false}
     * @helios.examples <pre>{@code Optional<Integer> present = Optional.of(5);
     * assert optional.isPresent();
     *
     * Optional<Integer> absent = Optional.absent();
     * assert !absent.isPresent();
     * }</pre>
     */
    boolean isPresent();

    /**
     * Checks whether this {@code Optional} is absent.
     *
     * @return {@code true} if this {@code Optional} is absent, otherwise {@code false}
     * @helios.examples <pre>{@code Optional<Integer> absent = Optional.absent();
     * assert absent.isAbsent();
     *
     * Optional<Integer> present = Optional.of(5);
     * assert !optional.isAbsent();
     * }</pre>
     */
    boolean isAbsent();

    /**
     * Returns this {@code Optional} if it is present, otherwise the specified {@code Optional}.
     *
     * @param other the {@code Optional} to fall back to
     * @return this {@code Optional} if it is present, otherwise the specified {@code Optional}
     * @throws NullPointerException if the specified optional is {@code null}.
     * @helios.examples <pre>{@code Optional<Integer> present = Optional.of(5);
     * assert present.or(Optional.of(10)).equals(optional);
     *
     * Optional<Integer> absent = Optional.absent();
     * assert absent.or(Optional.of(10)).equals(Optional.of(10));
     * }</pre>
     */
    Optional<T> or(Optional<? extends T> other);

    /**
     * Returns the value of this {@code Optional} if it is present, otherwise the specified default
     * value.
     *
     * @param defaultValue the default value to fall back to
     * @return the value of this {@code Optional} if it is present, otherwise the specified
     * default value
     * @throws NullPointerException if the default value is {@code null}.
     * @helios.examples <pre>{@code Optional<Integer> present = Optional.of(5);
     * assert present.or(10) == 5;
     *
     * Optional<Integer> absent = Optional.absent();
     * assert absent.or(10) == 10;
     * }</pre>
     */
    T orDefault(T defaultValue);

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
     * @helios.examples <pre>{@code Optional<Integer> present = Optional.of(5);
     * assert present.map(n -> n * 2).equals(Optional.of(10));
     *
     * Optional<Integer> absent = Optional.absent();
     * assert absent.map(n -> n * 2).equals(Optional.absent());
     * }</pre>
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
     * @helios.examples <pre>{@code Optional<User> findUser(String username) { ... }
     *
     * Optional<Repository> repository = findUser("Sparky983")
     *        .flatMap(user -> user.findRepository("helios"));
     * }</pre>
     */
    <M extends @NonNull Object> Optional<M> flatMap(
            Function<? super T, ? extends Optional<? extends M>> mapper);

    /**
     * Checks whether this {@code Optional} is equal to the specified object.
     *
     * @param obj the object to compare to
     * @return {@code true} if this {@code Optional}'s value is {@link Object#equals(Object) equal}
     * to the specified object or both are absent, otherwise {@code false}
     */
    @Override
    boolean equals(Object obj);

    /**
     * Returns the hash code of this {@code Optional}'s value, or {@code 0} if it is absent.
     *
     * @return the hash code of this {@code Optional}'s value, or {@code 0} if it is absent
     */
    @Override
    int hashCode();
}
