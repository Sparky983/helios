/**
 * Traditionally in Java, we have used {@code null} to represent the absence of a value. However,
 * this has many shortcomings such as:
 * <ul>
 *     <li>{@code null} inherently leads to {@link java.lang.NullPointerException}s</li>
 *     <li>{@code null} is not type-safe</li>
 *     <li>{@code null} is not nestable</li>
 * </ul>
 * <p>
 * We can solve these problems by using {@link me.sparky983.helios.optional.Optional} instead of
 * {@code null}. {@code Optional} provides a safe way for dealing with and representing possibly
 * absent values. An {@code Optional} that contains a value is called "present" and ones that don't
 * are called "absent".
 * <pre>{@code  Optional<Integer> present = Optional.of(5);
 * assert optional.isPresent();
 *
 * Optional<Integer> absent = Optional.absent();
 * assert absent.isAbsent();}</pre>
 * <p>
 * Unlike {@link java.util.Optional java.util.Optional}, {@code Optional} is intended to fully
 * replace {@code null} so it is fine to use this type on parameters and fields.
 * <p>
 * {@code Optional} provides a set of methods to query the value of the {@code Optional}:
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
 *         <td>{@link me.sparky983.helios.optional.Optional#orDefault(java.lang.Object)}</td>
 *         <td>{@code Optional.of(value)}</td>
 *         <td>{@code other}</td>
 *         <td>{@code value}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link me.sparky983.helios.optional.Optional#orDefault(java.lang.Object)}</td>
 *         <td>{@code Optional.absent()}</td>
 *         <td>{@code other}</td>
 *         <td>{@code other}</td>
 *     </tr>
 * </table>
 * If you want to get the value out of an {@code Optional}, you can destructure it using record
 * patterns:
 * <pre>{@code  Optional<Integer> present = ...;
 * switch (present) {
 *     case Present(Integer value) -> System.out.println(value);
 *     case Absent() -> System.out.println("No value");
 * }
 *
 * if (present instanceof Present(Integer value)) {
 *     System.out.println(value);
 * }}</pre>
 * or if you know the {@code Optional} is present you can use
 * {@link me.sparky983.helios.optional.Present#value()}:
 * <pre>{@code  Present<Integer> present = Optional.of(10);
 * System.out.println(present.value());}</pre>
 * <p>
 * In addition to the methods above, {@code Optional} provides an
 * {@link me.sparky983.helios.optional.Optional#map(java.util.function.Function)} method that allow
 * you to transform the value inside the {@code Optional}:
 * <pre>{@code  Optional<Integer> present = Optional.of(5);
 * assert present.map(n -> n * 2).equals(Optional.of(10));
 *
 * Optional<Integer> absent = Optional.absent();
 * assert absent.map(n -> n * 2).isAbsent();
 * }</pre>
 * And an {@link me.sparky983.helios.optional.Optional#flatMap(java.util.function.Function)} method:
 * <pre>{@code  Optional<Integer> present = Optional.of(5);
 * assert present.flatMap(n -> Optional.of(n * 2)).equals(Optional.of(10));
 * assert present.flatMap(n -> Optional.absent()).isAbsent();
 *
 * Optional<Integer> absent = Optional.absent();
 * assert absent.flatMap(n -> Optional.of(n * 2)).isAbsent();
 * assert absent.flatMap(n -> Optional.absent()).isAbsent();
 * }</pre>
 * <p>
 * It is encouraged to use {@code Optional} instead of {@code null} whenever possible. However,
 * there are some APIs that require {@code null}. In these cases you can use
 * {@link me.sparky983.helios.optional.Optional#fromNullable(java.lang.Object)} or
 * {@link me.sparky983.helios.optional.Optional#orNull()}
 *
 */
package me.sparky983.helios.optional;
