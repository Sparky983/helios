// @formatter:off
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
 * These problems are solved by using {@link me.sparky983.helios.optional.Optional} instead of
 * {@code null}. {@code Optional} provides a safe way of handling and representing possibly
 * absent values. An {@code Optional} that contains a value is called "present" and ones that don't
 * are called "absent".
 * <pre>{@code  Optional<Integer> present = Optional.of(5);
 * assert optional.isPresent();
 *
 * Optional<Integer> absent = Optional.absent();
 * assert absent.isAbsent();}</pre>
 * <p>
 * A set of methods for querying the value are provided by {@code Optional}:
 * <table border="1">
 *     <caption>Querying Methods</caption>
 *     <tr>
 *         <th>Method</th>
 *         <th>Receiver</th>
 *         <th>Input</th>
 *         <th>Output</th>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#or(me.sparky983.helios.optional.Optional)}</td>
 *         <td>{@code Optional.of(value)}</td>
 *         <td>{@code Optional.of(other)}</td>
 *         <td>{@code Optional.of(value)}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#or(me.sparky983.helios.optional.Optional)}</td>
 *         <td>{@code Optional.of(value)}</td>
 *         <td>{@code Optional.absent()}</td>
 *         <td>{@code Optional.of(value)}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#or(me.sparky983.helios.optional.Optional)}</td>
 *         <td>{@code Optional.absent()}</td>
 *         <td>{@code Optional.of(other)}</td>
 *         <td>{@code Optional.of(other)}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#or(me.sparky983.helios.optional.Optional)}</td>
 *         <td>{@code Optional.absent()}</td>
 *         <td>{@code Optional.absent()}</td>
 *         <td>{@code Optional.absent()}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#orDefault(Object)}</td>
 *         <td>{@code Optional.of(value)}</td>
 *         <td>{@code other}</td>
 *         <td>{@code value}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#orDefault(Object)}</td>
 *         <td>{@code Optional.absent()}</td>
 *         <td>{@code other}</td>
 *         <td>{@code other}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#orNull()}</td>
 *         <td>{@code Optional.of(value)}</td>
 *         <td>N/A</td>
 *         <td>{@code value}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#orNull()}</td>
 *         <td>{@code Optional.absent()}</td>
 *         <td>N/A</td>
 *         <td>{@code null}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Present#value()}</td>
 *         <td>{@code Optional.of(value)}</td>
 *         <td>N/A</td>
 *         <td>{@code value}</td>
 *     </tr>
 * </table>
 * <p>
 * Additionally, {@code Optional} provides an
 * {@link me.sparky983.helios.optional.Optional#map(java.util.function.Function)} method that
 * transforms an {@code Optional}'s value:
 * <pre>{@code  Optional<Integer> present = Optional.of(5);
 * assert present.map(n -> n * 2).equals(Optional.of(10));
 *
 * Optional<Integer> absent = Optional.absent();
 * assert absent.map(n -> n * 2).isAbsent();}</pre>
 * ... and an {@link me.sparky983.helios.optional.Optional#flatMap(java.util.function.Function)}
 * method if the transformed value is another {@code Optional}:
 * <pre>{@code  Optional<Integer> present = Optional.of(5);
 * assert present.flatMap(n -> Optional.of(n * 2)).equals(Optional.of(10));
 * assert present.flatMap(n -> Optional.absent()).isAbsent();
 *
 * Optional<Integer> absent = Optional.absent();
 * assert absent.flatMap(n -> Optional.of(n * 2)).isAbsent();
 * assert absent.flatMap(n -> Optional.absent()).isAbsent();}</pre>
 * <h2><a id="idioms">Idioms</a></h2>
 * <h3>Pattern Matching</h3>
 * {@code Optional} uses subtyping to represent the two states. This can be taken advantage of by
 * using Java 15's pattern to ensure the safety of a
 * {@link me.sparky983.helios.optional.Present#value()} call:
 * <pre>{@code  Optional<Integer> optional = Optional.of(5);
 * if (optional instanceof Present<Integer> present) {
 *     System.out.println(present.value());
 * }}</pre>
 * ... and since {@code Optional}s are {@code sealed}, the {@code default} branch in switch
 * statements (that use pattern matching for switch which is in preview since Java 27) can be
 * omitted:
 * <pre>{@code  switch (optional) {
 *     case Present<Integer> present -> System.out.println(present.value());
 *     case Absent -> System.out.println("Absent");
 * }}</pre>
 * <h3>Record Deconstructing</h3>
 * Both the {@code Present} and {@code Absent} classes are records, which allows for the use of
 * record patterns (which are in preview since Java 20):
 * <pre>{@code  Optional<Integer> optional = Optional.of(5);
 * if (optional instanceof Present(Integer value)) {
 *     System.out.println(value);
 * }
 * // or with switch
 * switch (optional) {
 *     case Present(Integer value) -> System.out.println(value());
 *     case Absent -> System.out.println("Absent");
 * }}</pre>
 * <h2>Null Interoperability</h2>
 * The use of {@code Optional} over {@code nll} is encouraged, however there are times when this is
 * not possible such as when interacting with legacy APIs. {@code Optional} provides several methods
 * for dealing with these cases:
 * <ul>
 *     <li>{@link me.sparky983.helios.optional.Optional#fromNullable(java.lang.Object)} converts a
 *     nullable reference to an {@code Optional}</li>
 *     <li>{@link me.sparky983.helios.optional.Optional#orNull()} convert an {@code Optional} to a
 *     nullable reference</li>
 * </ul>
 * <h2>Comparison with java.util.Optional</h2>
 * There are several subtle differences between {@link java.util.Optional java.util.Optional} and
 * {@code Optional}, however the most significant is that {@code Optional} is an implementation of
 * the <a href="https://en.wikipedia.org/wiki/Option_type">option type</a>, meaning its use on
 * fields and methods is highly encouraged.
 * <p>
 * An exhaustive list of the differences is listed below.
 * <table border="1">
 *     <caption>{@code java.util.Optional} comparison</caption>
 *     <tr>
 *         <th>{@code Optional}</th>
 *         <th>{@code java.util.Optional}</th>
 *         <th>Notes</th>
 *     </tr>
 *      <tr>
 *          <td>{@link me.sparky983.helios.optional.Optional#absent()}</td>
 *          <td>{@link java.util.Optional#empty()}</td>
 *          <td></td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#of(Object)}</td>
 *         <td>{@link java.util.Optional#of(Object)}</td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#fromNullable(Object)}</td>
 *         <td>{@link java.util.Optional#ofNullable(Object)}</td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#isPresent()}</td>
 *         <td>{@link java.util.Optional#isPresent()}</td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#isAbsent()}</td>
 *         <td>{@link java.util.Optional#isEmpty()}</td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#or(me.sparky983.helios.optional.Optional)}</td>
 *         <td>N/A</td>
 *         <td>{@code java.util.Optional} uses only a supplier based method (see {@link java.util.Optional#or(java.util.function.Supplier)}</td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#or(java.util.function.Supplier)}</td>
 *         <td>{@link java.util.Optional#or(java.util.function.Supplier)}</td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#orDefault(Object)}</td>
 *         <td>{@link java.util.Optional#orElse(Object)}</td>
 *         <td>{@code java.util.Optional.orElse()} permits {@code null}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#orGet(java.util.function.Supplier)}</td>
 *         <td>{@link java.util.Optional#orElseGet(java.util.function.Supplier)}</td>
 *         <td>{@code java.util.Optional.orGet()} permits returning {@code null}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#orNull()}</td>
 *         <td>N/A</td>
 *         <td>You have to use {@code java.util.Optional.orElse(null)}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#map(java.util.function.Function)}</td>
 *         <td>{@link java.util.Optional#map(java.util.function.Function)}</td>
 *         <td>{@code java.util.Optional.map()} permits returning {@code null}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#flatMap(java.util.function.Function)}</td>
 *         <td>{@link java.util.Optional#flatMap(java.util.function.Function)}</td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#filter(java.util.function.Predicate)}</td>
 *         <td>{@link java.util.Optional#filter(java.util.function.Predicate)}</td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Present#value()}</td>
 *         <td>{@link java.util.Optional#get()}</td>
 *         <td>{@code Present.value()} is guaranteed to be safe at compile-time (unless casted) as
 *         opposed to {@code java.util.Optional.get()} which is not safe and also has a deceptive
 *         name</td>
 *     </tr>
 *     <tr>
 *         <td>N/A</td>
 *         <td>{@link java.util.Optional#ifPresent(java.util.function.Consumer)}</td>
 *         <td>See <a href="#idioms">Idioms</a></td>
 *    </tr>
 *    <tr>
 *        <td>N/A</td>
 *        <td>{@link java.util.Optional#ifPresentOrElse(java.util.function.Consumer, java.lang.Runnable)}</td>
 *        <td>See <a href="#idioms">Idioms</a></td>
 *    </tr>
 *    <tr>
 *        <td>N/A</td>
 *        <td>{@link java.util.Optional#stream()}</td>
 *        <td>You have to do {@code Optional.map(Stream::of).orElse(Stream.of())}</td>
 *    </tr>
 *    <tr>
 *        <td>N/A</td>
 *        <td>{@link java.util.Optional#orElseThrow()}</td>
 *        <td></td>
 *    </tr>
 *    <tr>
 *        <td>N/A</td>
 *        <td>{@link java.util.Optional#orElseThrow(java.util.function.Supplier)}</td>
 *        <td></td>
 *    </tr>
 * </table>
 */
package me.sparky983.helios.optional;
// @formatter:on
