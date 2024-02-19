package me.sparky983.helios;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import me.sparky983.helios.annotations.Experimental;
import me.sparky983.helios.annotations.Nullable;

/**
 * An immutable container which may contain a non-null value.
 * <h2>Shortcomings of null</h2>
 * Traditionally in Java, {@code null} was used to represent the absence of a value. However, this
 * has many shortcomings:
 * <ul>
 *   <li>{@code null} inherently leads to {@link java.lang.NullPointerException}s</li>
 *   <li>{@code null} is not type-safe</li>
 *   <li>{@code null} is not nestable</li>
 * </ul>
 * <h2>Optional</h2>
 * These problems are solved by using {@code Optional}. {@code Optional} provides a type-safe way of
 * handling and representing possibly absent values.
 * <p>
 * An {@code Optional} can be in one of two states:
 * <ul>
 *   <li>{@link Present}</li>
 *   <li>and {@link Absent}</li>
 * </ul>
 * {@snippet :
 * Optional<Integer> present = Optional.present(5);
 * assert optional.isPresent();
 *
 * Optional<Integer> absent = Optional.absent();
 * assert absent.isAbsent();
 * }
 * <p>
 * Unlike {@code null}, the type system enforces that a references wrapped in {@code Optional}
 * can only be accessed if it is present.
 * <p>
 * The methods for querying the value are provided by {@code Optional}:
 * <table border="1">
 *   <caption>Querying Methods</caption>
 *   <tr>
 *     <th>Method</th>
 *     <th>Receiver</th>
 *     <th>Input</th>
 *     <th>Output</th>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#or(Optional)}</td>
 *     <td>{@code Optional.present(value)}</td>
 *     <td>{@code Optional.present(other)}</td>
 *     <td>{@code Optional.present(value)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#or(Optional)}</td>
 *     <td>{@code Optional.present(value)}</td>
 *     <td>{@code Optional.absent()}</td>
 *     <td>{@code Optional.present(value)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#or(Optional)}</td>
 *     <td>{@code Optional.absent()}</td>
 *     <td>{@code Optional.present(other)}</td>
 *     <td>{@code Optional.present(other)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#or(Optional)}</td>
 *     <td>{@code Optional.absent()}</td>
 *     <td>{@code Optional.absent()}</td>
 *     <td>{@code Optional.absent()}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#orDefault(Object)}</td>
 *     <td>{@code Optional.present(value)}</td>
 *     <td>{@code other}</td>
 *     <td>{@code value}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#orDefault(Object)}</td>
 *     <td>{@code Optional.absent()}</td>
 *     <td>{@code other}</td>
 *     <td>{@code other}</td>
 *   </tr>
 * </table>
 * <p>
 * Additionally, {@code Optional} provides several methods to transform its value. The simplest
 * being {@link Optional#map(java.util.function.Function)} which simply
 * applies the given {@link java.util.function.Function} to its value:
 * {@snippet :
 * Optional<Integer> present = Optional.present(5);
 * assert present.map(n -> n * 2).equals(Optional.present(10));
 *
 * Optional<Integer> absent = Optional.absent();
 * assert absent.map(n -> n * 2).isAbsent();
 * }
 * If the {@link java.util.function.Function} returns an {@code Optional},
 * {@link Optional#flatMap(java.util.function.Function)} can be used to
 * automatically flatten the return value:
 * {@snippet :
 * Optional<Integer> present = Optional.present(5);
 * assert present.flatMap(n -> Optional.present(n * 2)).equals(Optional.present(10));
 * assert present.flatMap(n -> Optional.absent()).isAbsent();
 *
 * Optional<Integer> absent = Optional.absent();
 * assert absent.flatMap(n -> Optional.present(n * 2)).isAbsent();
 * assert absent.flatMap(n -> Optional.absent()).isAbsent();
 * }
 * <h2><a id="idioms">Idioms</a></h2>
 * <h3>Pattern Matching</h3>
 * {@code Optional} supports pattern matching. This
 * can be taken advantage of to make a type-safe call to
 * {@link Present#value()}:
 * {@snippet :
 * Optional<Integer> optional = Optional.present(5);
 * if (optional instanceof Present<Integer> present) {
 *   System.out.println(present.value());
 * }
 * }
 * <h2>Null Interoperability</h2>
 * The use of {@code Optional} over {@code null} is highly encouraged, however there are times when
 * this is not possible, such as when interacting with legacy APIs. {@code Optional} provides two
 * main methods for dealing with these cases:
 * <ul>
 *     <li>{@link Optional#fromNullable(java.lang.Object)} for
 *     converting a possibly-{@code null} reference to an {@code Optional}</li>
 *     <li>{@link Optional#orNull()} for converting an
 *     {@code Optional} to a possibly-{@code null} reference</li>
 * </ul>
 * <h2>Comparison with java.util.Optional</h2>
 * There are several subtle differences between {@link java.util.Optional java.util.Optional} and
 * {@code Optional}, however the most significant is that {@code Optional} is an implementation of
 * the <a href="https://en.wikipedia.org/wiki/Option_type">option type</a>, meaning its use on
 * fields and methods is highly encouraged.
 * <p>
 * A table that lists the differences is below.
 * <table border="1">
 *   <caption>{@link java.util.Optional java.util.Optional} comparison</caption>
 *   <tr>
 *     <th>{@code me.sparky983.helios.Optional}</th>
 *     <th>{@link java.util.Optional java.util.Optional}</th>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#absent()}</td>
 *     <td>{@link java.util.Optional#empty()}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#present(Object)}</td>
 *     <td>{@link java.util.Optional#of(Object)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#fromNullable(Object)}</td>
 *     <td>{@link java.util.Optional#ofNullable(Object)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#isPresent()}</td>
 *     <td>{@link java.util.Optional#isPresent()}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#isAbsent()}</td>
 *     <td>{@link java.util.Optional#isEmpty()}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#or(Optional)}</td>
 *     <td>N/A</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#or(java.util.function.Supplier)}</td>
 *     <td>{@link java.util.Optional#or(java.util.function.Supplier)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#orDefault(Object)}</td>
 *     <td>{@link java.util.Optional#orElse(Object)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#orGet(java.util.function.Supplier)}</td>
 *     <td>{@link java.util.Optional#orElseGet(java.util.function.Supplier)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#orNull()}</td>
 *     <td>{@code java.util.Optional.orElse(null)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#orThrow(java.util.function.Supplier)}</td>
 *     <td>{@link java.util.Optional#orElseThrow(java.util.function.Supplier)}</td>
 *   <tr>
 *     <td>{@link Optional#map(java.util.function.Function)}</td>
 *     <td>{@link java.util.Optional#map(java.util.function.Function)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#flatMap(java.util.function.Function)}</td>
 *     <td>{@link java.util.Optional#flatMap(java.util.function.Function)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Optional#filter(java.util.function.Predicate)}</td>
 *     <td>{@link java.util.Optional#filter(java.util.function.Predicate)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Present#value()}</td>
 *     <td>{@link java.util.Optional#get()}</td>
 *   </tr>
 *   <tr>
 *     <td>See <a href="#idioms">Idioms</a></td>
 *     <td>{@link java.util.Optional#ifPresent(java.util.function.Consumer)}</td>
 *   </tr>
 *   <tr>
 *     <td>See <a href="#idioms">Idioms</a></td>
 *     <td>
 *       {@link java.util.Optional#ifPresentOrElse(java.util.function.Consumer, java.lang.Runnable)}
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>{@code Optional.map(Stream::of).orElse(Stream.of())}</td>
 *     <td>{@link java.util.Optional#stream()}</td>
 *   </tr>
 * </table>
 * @param <T> the type of the value
 * @since 0.1.0
 * @helios.apiNote Unlike {@link java.util.Optional java.util.Optional}, this {@code Optional} type
 * is intended to be an <a href="https://en.wikipedia.org/wiki/Option_type">option type</a> meaning
 * it is a replacement for {@code null}. Therefore, it is fine to use this type on parameters and
 * fields.
 * <p>
 * Java 21 introduces pattern matching for switch and record patterns, allowing for more concise
 * handling of {@code Optional} values. For example:
 * {@snippet :
 * Optional<String> optional = Optional.present("value");
 * switch (optional) {
 *   case Present(String value) -> System.out.println("Present: " + value);
 *   case Absent() -> System.out.println("Absent");
 * }
 * }
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
   * }
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
   * }
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
   * }
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
   * }
   */
  Optional<T> or(Optional<? extends T> other);

  /**
   * Returns this {@code Optional} if present, otherwise the return value of the given supplier.
   *
   * @param otherSupplier the fallback value supplier
   * @return this {@code Optional} if present, otherwise the return value of the given supplier
   * @throws NullPointerException if the supplier is {@code null} or returns {@code null}.
   * @helios.examples
   * {@snippet :
   * Optional<String> findCachedUser(String username) {
   *   return Optional.absent(); // cache miss
   * }
   * Optional<String> findUser(String username) {
   *   return Optional.present(username);
   * }
   *
   * Optional<String> user = findCachedUser("sparky983")
   *     .or(() -> findUser("sparky983"));
   * }
   */
  Optional<T> or(Supplier<? extends Optional<? extends T>> otherSupplier);

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
   * }
   */
  T orDefault(T defaultValue);

  /**
   * Returns the value of this {@code Optional} if present, otherwise the return value of the
   * given supplier.
   *
   * @param defaultValueSupplier the fallback value supplier
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
   * }
   */
  T orGet(Supplier<? extends T> defaultValueSupplier);

  /**
   * Returns the value of this {@code Optional} if present, otherwise {@code null}.
   *
   * @return the value of this {@code Optional} if present, otherwise {@code null}
   * @helios.apiNote This method is intended to improve interoperability with null-based APIs.
   * @helios.examples
   * {@snippet :
   * String number = Optional.absent()
   *     .map(Object::toString)
   *     .orNull();
   * }
   */
  @Nullable T orNull();

  /**
   * Returns the value of this {@code Optional} if present, otherwise throws the exception provided
   * by the given supplier.
   *
   * @param exceptionSupplier the supplier of the exception to be thrown
   * @param <E> the type of the exception to be thrown
   * @return the value of this {@code Optional} if present
   * @throws NullPointerException if the given supplier is {@code null} or returns {@code null}.
   * @throws E if this {@code Optional} is absent
   * @helios.examples
   * {@snippet :
   * Optional<Integer> present = Optional.present(5);
   * assert present.orThrow(IllegalStateException::new) == 5;
   *
   * Optional<Integer> absent = Optional.absent();
   * absent.orThrow(IllegalStateException::new); // throws IllegalStateException
   * assert false; // unreachable
   * }
   */
  <E extends Throwable> T orThrow(Supplier<? extends E> exceptionSupplier) throws E;

  /**
   * Returns the value of this {@code Optional} if present, otherwise throws an
   * {@link NoSuchElementException}.
   * <p><strong>Warning:</strong> this method is experimental, and such, is subject to backwards
   * incompatible changes between minor versions.
   *
   * @param message a message that is used to create the exception
   * @return the value of this {@code Optional} if present
   * @throws NoSuchElementException if this {@code Optional} is absent.
   * @throws NullPointerException if the message is {@code null}.
   * @helios.apiNote The message should describe the expectation of the value.
   * @helios.examples
   * {@snippet :
   * Optional<Integer> present = Optional.present(5)
   *     .filter(n -> n % 2 == 1);
   * assert present.expect("the optional to be odd") == 5;
   * }
   */
  @Experimental
  T expect(String message);

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
   * }
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
   *   return Optional.present(User.builder()
   *       .repository(Repository.named("helios"))
   *       .build());
   * }
   *
   * Optional<Repository> repository = findUser("Sparky983")
   *     .flatMap(user -> user.findRepository("helios"));
   * }
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
   * }
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
