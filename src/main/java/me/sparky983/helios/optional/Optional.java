package me.sparky983.helios.optional;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import me.sparky983.helios.annotations.Nullable;

/**
 * An immutable container which may contain a non-null value.
 * <p>
 * For a more user-friendly guide instead of API specification, see the
 * <a href="package-summary">package summary</a>.
 * <p>
 * {@code Optional}s are represented by two variants:
 * <ul>
 *     <li>{@link Present}</li>
 *     <li>and {@link Absent}</li>
 * </ul>
 * ... which represent a "present" and "absent" value respectively.
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
 * {@snippet :
 * Optional<String> optional = Optional.present("value");
 * switch (optional) {
 *     case Present(String value) -> System.out.println("Present: " + value);
 *     case Absent() -> System.out.println("Absent");
 * }
 *}
 */
public sealed interface Optional<T extends Object> permits Present, Absent {
  /**
   * Returns a present {@code Optional} containing the given value.
   *
   * @param value the value
   * @return a present {@code Optional} containing the given value
   * @param <T> the type of the value
   * @throws NullPointerException if the value is {@code null}.
   */
  static <T extends Object> Optional<T> present(final T value) {
    return new Present<>(value);
  }

  /**
   * Returns an absent {@code Optional}.
   *
   * @param <T> the type of the value
   * @return an absent {@code Optional}
   * @helios.apiNote Although the default implementation returns a singleton, identity-sensitive
   * operations should be avoided since instances can still be instantiated directly.
   * @helios.implNote This method returns a singleton instance of {@link Absent}.
   */
  @SuppressWarnings("unchecked")
  static <T extends Object> Optional<T> absent() {
    return (Absent<T>) Absent.ABSENT;
  }

  /**
   * Returns an {@code Optional} containing the given value if it is non-null, otherwise an absent
   * {@code Optional}.
   * <p>
   * This method is equivalent to {@code value == null ? Optional.absent() : Optional.present(value)}.
   *
   * @param value the value
   * @return an {@code Optional} containing the given value if it is non-null, otherwise an absent
   * {@code Optional}
   * @param <T> the type of the value
   * @helios.apiNote This method is intended to improve interoperability with null-based APIs.
   * @helios.examples
   * {@snippet :
   * Map<String, String> map = Map.of("key", "value");
   * Optional<String> optional = Optional.fromNullable(map.get("key"));
   * }
   */
  static <T extends Object> Optional<T> fromNullable(final @Nullable T value) {
    if (value != null) {
      return present(value);
    } else {
      return absent();
    }
  }

  /**
   * Returns an {@code Optional} converted from a {@link java.util.Optional java.util.Optional}.
   * <p>
   * If the given {@link java.util.Optional java.util.Optional} is present, this returns an
   * {@code Optional} containing the value of the {@link java.util.Optional java.util.Optional}, otherwise
   * an absent {@code Optional}.
   *
   * @param optional the {@code java.util.Optional}
   * @return an {@code Optional} converted from the given {@code java.util.Optional}
   * @param <T> the type of the value
   * @throws NullPointerException if the {@code java.util.Optional} is {@code null}.
   * @helios.apiNote This method is intended to improve interoperability with
   * {@code java.util.Optional}.
   * @helios.examples
   * {@snippet :
   * java.util.Optional<String> javaUtilOptional = java.util.Optional.of("Java!");
   * Optional<String> optional = Optional.from(javaUtilOptional);
   *}
   */
  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  static <T extends Object> Optional<T> from(final java.util.Optional<T> optional) {
    return optional.map(Optional::present).orElse(Optional.absent());
  }

  /**
   * Checks whether this {@code Optional} is present.
   *
   * @return {@code true} if this {@code Optional} is present, otherwise {@code false}
   * @helios.examples
   * {@snippet :
   * Optional<Integer> present = Optional.present(5);
   * assert optional.isPresent();
   *
   * Optional<Integer> absent = Optional.absent();
   * assert !absent.isPresent();
   *}
   */
  boolean isPresent();

  /**
   * Checks whether this {@code Optional} is absent.
   *
   * @return {@code true} if this {@code Optional} is absent, otherwise {@code false}
   * @helios.examples
   * {@snippet :
   * Optional<Integer> absent = Optional.absent();
   * assert absent.isAbsent();
   *
   * Optional<Integer> present = Optional.present(5);
   * assert !optional.isAbsent();
   *}
   */
  boolean isAbsent();

  /**
   * Returns this {@code Optional} if present, otherwise the given {@code Optional}.
   *
   * @param other the fallback {@code Optional}
   * @return this {@code Optional} if present, otherwise the given {@code Optional}
   * @throws NullPointerException if the given {@code Optional} is {@code null}.
   * @helios.examples
   * {@snippet :
   * Optional<Integer> present = Optional.present(5);
   * assert present.or(Optional.present(10)).equals(optional);
   *
   * Optional<Integer> absent = Optional.absent();
   * assert absent.or(Optional.present(10)).equals(Optional.present(10));
   *}
   */
  Optional<T> or(Optional<? extends T> other);

  /**
   * Returns this {@code Optional} if present, otherwise the return value of the given supplier.
   *
   * @param otherGetter the fallback value supplier
   * @return this {@code Optional} if present, otherwise the return value of the given supplier
   * @throws NullPointerException if the supplier is {@code null} or returns {@code null}.
   * @helios.examples
   * {@snippet :
   * Optional<String> findCachedUser(String username) {
   *     return Optional.absent(); // cache miss
   * }
   * Optional<String> findUser(String username) {
   *     return Optional.present(username);
   * }
   *
   * Optional<String> user = findCachedUser("sparky983")
   *         .or(() -> findUser("sparky983"));
   *}
   */
  Optional<T> or(Supplier<? extends Optional<? extends T>> otherGetter);

  /**
   * Returns the value of this {@code Optional} if present, otherwise the given value.
   *
   * @param defaultValue the fallback value
   * @return the value of this {@code Optional} if present, otherwise the given value
   * @throws NullPointerException if the fallback value is {@code null}.
   * @helios.examples
   * {@snippet :
   * Optional<Integer> present = Optional.present(5);
   * assert present.orDefault(10) == 5;
   *
   * Optional<Integer> absent = Optional.absent();
   * assert absent.orDefault(10) == 10;
   *}
   */
  T orDefault(T defaultValue);

  /**
   * Returns the value of this {@code Optional} if present, otherwise the return value of the
   * given supplier.
   *
   * @param defaultValueGetter the fallback value supplier
   * @return the value of this {@code Optional} if present, otherwise the return value of the
   * given supplier
   * @throws NullPointerException if the given supplier is {@code null} or returns {@code null}.
   * @helios.examples
   * {@snippet :
   * Optional<Integer> present = Optional.present(5);
   * assert present.orGet(() -> 10) == 5;
   *
   * Optional<Integer> absent = Optional.absent();
   * assert absent.orGet(() -> 10) == 10;
   *}
   */
  T orGet(Supplier<? extends T> defaultValueGetter);

  /**
   * Returns the value of this {@code Optional} if present, otherwise {@code null}.
   *
   * @return the value of this {@code Optional} if present, otherwise {@code null}
   * @helios.apiNote This method is intended to improve interoperability with null-based APIs.
   * @helios.examples
   * {@snippet :
   * String number = Optional.absent()
   *         .map(Object::toString)
   *         .orNull();
   * }
   */
  @Nullable T orNull();

  /**
   * If this {@code Optional} is present, returns an {@code Optional} containing the value after
   * applying the given mapper to it, otherwise returns an absent {@code Optional}.
   *
   * @param mapper the mapper function
   * @return an {@code Optional} containing the mapped value if this {@code Optional} is present,
   * otherwise an absent {@code Optional}
   * @param <M> the result of the mapper
   * @throws NullPointerException if the given mapper is {@code null} or returns {@code null}.
   * @helios.examples
   * {@snippet :
   * Optional<Integer> present = Optional.present(5);
   * assert present.map(n -> n * 2).equals(Optional.present(10));
   *
   * Optional<Integer> absent = Optional.absent();
   * assert absent.map(n -> n * 2).isAbsent();
   *}
   */
  <M extends Object> Optional<M> map(Function<? super T, ? extends M> mapper);

  /**
   * If this {@code Optional} is present, returns the result of applying the given mapper to the
   * value of this {@code Optional}, otherwise returns an absent {@code Optional}.
   *
   * @param mapper the mapper function
   * @return an {@code Optional} containing the result of applying the mapping function to the
   * value of this {@code Optional} if present, otherwise an absent {@code Optional}.
   * @param <M> the result of the mapper
   * @throws NullPointerException if the mapper is {@code null} or returns {@code null}.
   * @helios.examples
   * {@snippet :
   * Optional<User> findUser(String username) {
   *     return Optional.present(User.builder()
   *             .repository(Repository.named("helios"))
   *             .build());
   * }
   *
   * Optional<Repository> repository = findUser("Sparky983")
   *        .flatMap(user -> user.findRepository("helios"));
   *}
   */
  <M extends Object> Optional<M> flatMap(
      Function<? super T, ? extends Optional<? extends M>> mapper);

  /**
   * If this {@code Optional} is present and the value matches the given predicate, returns this
   * {@code Optional}, otherwise returns an absent {@code Optional}.
   *
   * @param predicate the predicate
   * @return this {@code Optional} if the value matches the given predicate, otherwise an absent
   * {@code Optional}
   * @throws NullPointerException if the predicate is {@code null}.
   * @helios.examples
   * {@snippet :
   * Optional<Integer> present = Optional.present(5);
   * assert present.filter(n -> n % 2 == 1).equals(present);
   * assert present.filter(n -> n % 2 == 0).isAbsent();
   *}
   */
  Optional<T> filter(Predicate<? super T> predicate);

  /**
   * Checks whether this {@code Optional} is equal to the given object.
   * <p>
   * The two are equal if any of the following are true:
   * <ul>
   *     <li>This {@code Optional} and the given object are both present and contain the same
   *     value</li>
   *     <li>This {@code Optional} and the given object are both absent</li>
   * </ul>
   *
   * @param obj the object to compare to
   * @return {@code true} if they are equal, otherwise {@code false}
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
