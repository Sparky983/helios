/**
 * Classes for representing possibly absent values.
 * <h2>Shortcomings of null</h2>
 * Traditionally in Java, {@code null} was used to represent the absence of a value. However,
 * this has many shortcomings:
 * <ul>
 *     <li>{@code null} inherently leads to {@link java.lang.NullPointerException}s</li>
 *     <li>{@code null} is not type-safe</li>
 *     <li>{@code null} is not nestable</li>
 * </ul>
 * <h2>Optional</h2>
 * These problems are solved by using {@link me.sparky983.helios.optional.Optional}.
 * {@link me.sparky983.helios.optional.Optional} provides a type-safe way of handling and
 * representing possibly absent values.
 * <p>
 * An {@link me.sparky983.helios.optional.Optional} can be in one of two states:
 * <ul>
 *     <li>{@link me.sparky983.helios.optional.Present}</li>
 *     <li>and {@link me.sparky983.helios.optional.Absent}</li>
 * </ul>
 * {@snippet :
 * Optional<Integer> present = Optional.present(5);
 * assert optional.isPresent();
 *
 * Optional<Integer> absent = Optional.absent();
 * assert absent.isAbsent();
 *}
 * <p>
 * Unlike {@code null}, the type system enforces that a references wrapped in
 * {@link me.sparky983.helios.optional.Optional} can only be accessed if it is present.
 * <p>
 * The methods for querying the value are provided by {@link me.sparky983.helios.optional.Optional}:
 * <table border="1">
 *     <caption>Querying Methods</caption>
 *     <tr>
 *         <th>Method</th>
 *         <th>Receiver</th>
 *         <th>Input</th>
 *         <th>Output</th>
 *     </tr>
 *     <tr>
 *         <td>
 *             {@link
 *             me.sparky983.helios.optional.Optional#or(me.sparky983.helios.optional.Optional)}
 *         </td>
 *         <td>{@code Optional.present(value)}</td>
 *         <td>{@code Optional.present(other)}</td>
 *         <td>{@code Optional.present(value)}</td>
 *     </tr>
 *     <tr>
 *         <td>
 *             {@link
 *             me.sparky983.helios.optional.Optional#or(me.sparky983.helios.optional.Optional)}
 *         </td>
 *         <td>{@code Optional.present(value)}</td>
 *         <td>{@code Optional.absent()}</td>
 *         <td>{@code Optional.present(value)}</td>
 *     </tr>
 *     <tr>
 *         <td>
 *             {@link
 *             me.sparky983.helios.optional.Optional#or(me.sparky983.helios.optional.Optional)}
 *         </td>
 *         <td>{@code Optional.absent()}</td>
 *         <td>{@code Optional.present(other)}</td>
 *         <td>{@code Optional.present(other)}</td>
 *     </tr>
 *     <tr>
 *         <td>
 *             {@link
 *             me.sparky983.helios.optional.Optional#or(me.sparky983.helios.optional.Optional)}
 *         </td>
 *         <td>{@code Optional.absent()}</td>
 *         <td>{@code Optional.absent()}</td>
 *         <td>{@code Optional.absent()}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#orDefault(Object)}</td>
 *         <td>{@code Optional.present(value)}</td>
 *         <td>{@code other}</td>
 *         <td>{@code value}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#orDefault(Object)}</td>
 *         <td>{@code Optional.absent()}</td>
 *         <td>{@code other}</td>
 *         <td>{@code other}</td>
 *     </tr>
 * </table>
 * <p>
 * Additionally, {@link me.sparky983.helios.optional.Optional} provides several methods to transform
 * its value. The simplest being
 * {@link me.sparky983.helios.optional.Optional#map(java.util.function.Function)} which simply
 * applies the given {@link java.util.function.Function} to its value:
 * {@snippet :
 * Optional<Integer> present = Optional.present(5);
 * assert present.map(n -> n * 2).equals(Optional.present(10));
 *
 * Optional<Integer> absent = Optional.absent();
 * assert absent.map(n -> n * 2).isAbsent();
 *}
 * If the {@link java.util.function.Function} returns an
 * {@link me.sparky983.helios.optional.Optional},
 * {@link me.sparky983.helios.optional.Optional#flatMap(java.util.function.Function)} can be used to
 * automatically flatten the return value:
 * {@snippet :
 * Optional<Integer> present = Optional.present(5);
 * assert present.flatMap(n -> Optional.present(n * 2)).equals(Optional.present(10));
 * assert present.flatMap(n -> Optional.absent()).isAbsent();
 *
 * Optional<Integer> absent = Optional.absent();
 * assert absent.flatMap(n -> Optional.present(n * 2)).isAbsent();
 * assert absent.flatMap(n -> Optional.absent()).isAbsent();
 *}
 * <h2><a id="idioms">Idioms</a></h2>
 * <h3>Pattern Matching</h3>
 * {@link me.sparky983.helios.optional.Optional} supports pattern matching. This
 * can be taken advantage of to make a type-safe call to
 * {@link me.sparky983.helios.optional.Present#value()}:
 * {@snippet :
 * Optional<Integer> optional = Optional.present(5);
 * if (optional instanceof Present<Integer> present) {
 *     System.out.println(present.value());
 * }
 *}
 * <h2>Null Interoperability</h2>
 * The use of {@link me.sparky983.helios.optional.Optional} over {@code null} is highly encouraged,
 * however there are times when this is not possible, such as when interacting with legacy APIs.
 * {@link me.sparky983.helios.optional.Optional} provides two main methods for dealing with these
 * cases:
 * <ul>
 *     <li>{@link me.sparky983.helios.optional.Optional#fromNullable(java.lang.Object)} for
 *     converting a possibly-{@code null} reference to an {@link me.sparky983.helios.optional.Optional}</li>
 *     <li>{@link me.sparky983.helios.optional.Optional#orNull()} for converting an
 *     {@link me.sparky983.helios.optional.Optional} to a possibly-{@code null} reference</li>
 * </ul>
 * <h2>Comparison with java.util.Optional</h2>
 * There are several subtle differences between {@link java.util.Optional java.util.Optional} and
 * {@link me.sparky983.helios.optional.Optional}, however the most significant is that
 * {@link me.sparky983.helios.optional.Optional} is an implementation of the
 * <a href="https://en.wikipedia.org/wiki/Option_type">option type</a>, meaning its use on fields
 * and methods is highly encouraged.
 * <p>
 * A table that lists the differences is below.
 * <table border="1">
 *     <caption>{@link java.util.Optional java.util.Optional} comparison</caption>
 *     <tr>
 *         <th>
 *             {@link me.sparky983.helios.optional.Optional me.sparky983.helios.optoinal.Optional}
 *         </th>
 *         <th>{@link java.util.Optional java.util.Optional}</th>
 *     </tr>
 *      <tr>
 *          <td>{@link me.sparky983.helios.optional.Optional#absent()}</td>
 *          <td>{@link java.util.Optional#empty()}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#present(Object)}</td>
 *         <td>{@link java.util.Optional#of(Object)}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#fromNullable(Object)}</td>
 *         <td>{@link java.util.Optional#ofNullable(Object)}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#isPresent()}</td>
 *         <td>{@link java.util.Optional#isPresent()}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#isAbsent()}</td>
 *         <td>{@link java.util.Optional#isEmpty()}</td>
 *     </tr>
 *     <tr>
 *         <td>
 *             {@link
 *             me.sparky983.helios.optional.Optional#or(me.sparky983.helios.optional.Optional)}
 *         </td>
 *         <td>N/A</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#or(java.util.function.Supplier)}</td>
 *         <td>{@link java.util.Optional#or(java.util.function.Supplier)}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#orDefault(Object)}</td>
 *         <td>{@link java.util.Optional#orElse(Object)}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#orGet(java.util.function.Supplier)}</td>
 *         <td>{@link java.util.Optional#orElseGet(java.util.function.Supplier)}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#orNull()}</td>
 *         <td>{@code java.util.Optional.orElse(null)}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#map(java.util.function.Function)}</td>
 *         <td>{@link java.util.Optional#map(java.util.function.Function)}</td>
 *     </tr>
 *     <tr>
 *         <td>
 *             {@link me.sparky983.helios.optional.Optional#flatMap(java.util.function.Function)}
 *         </td>
 *         <td>{@link java.util.Optional#flatMap(java.util.function.Function)}</td>
 *     </tr>
 *     <tr>
 *         <td>
 *             {@link me.sparky983.helios.optional.Optional#filter(java.util.function.Predicate)}
 *         </td>
 *         <td>{@link java.util.Optional#filter(java.util.function.Predicate)}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Present#value()}</td>
 *         <td>{@link java.util.Optional#get()}</td>
 *     </tr>
 *     <tr>
 *         <td>See <a href="#idioms">Idioms</a></td>
 *         <td>{@link java.util.Optional#ifPresent(java.util.function.Consumer)}</td>
 *    </tr>
 *    <tr>
 *        <td>See <a href="#idioms">Idioms</a></td>
 *        <td>
 *            {@link
 *            java.util.Optional#ifPresentOrElse(java.util.function.Consumer, java.lang.Runnable)}
 *        </td>
 *    </tr>
 *    <tr>
 *        <td>{@code Optional.map(Stream::of).orElse(Stream.of())}</td>
 *        <td>{@link java.util.Optional#stream()}</td>
 *    </tr>
 * </table>
 */
package me.sparky983.helios.optional;
