package me.sparky983.helios.base;

import me.sparky983.helios.optional.Absent;
import me.sparky983.helios.optional.Optional;
import me.sparky983.helios.optional.Present;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class OptionalTests {

    /**
     * A dummy value to test with.
     */
    static final Object VALUE = new Object();

    /**
     * A second dummy value to test with.
     */
    static final Object VALUE2 = new Object();

    /**
     * A {@code null} {@code Optional} to test with.
     * <p>
     * To avoid ugly {@code (Optional<Object>) null} casts.
     */
    static final Optional<Object> NULL_OPTIONAL = null;

    /**
     * A {@code null} {@code Supplier} to test with.
     * <p>
     * To avoid ugly {@code (Supplier<Optional<Object>>) null} casts.
     */
    static final Supplier<Optional<Object>> NULL_SUPPLIER = null;

    @Nested
    class PresentTests {

        @Test
        void testOf() {

            assertInstanceOf(Present.class, Optional.of(VALUE));
        }

        @Test
        void testOf_WhenNull() {

            var exception = assertThrows(NullPointerException.class, () -> Optional.of(null));
            assertEquals("value cannot be null", exception.getMessage());
        }

        @Test
        void testIsPresent() {

            assertTrue(Optional.of(VALUE).isPresent());
        }

        @Test
        void testIsAbsent() {

            assertFalse(Optional.of(VALUE).isAbsent());
        }

        @Test
        void testOr_Optional_WhenAbsent() {

            assertEquals(Optional.of(VALUE), Optional.of(VALUE).or(Optional.absent()));
        }

        @Test
        void testOr_Optional_WhenPresent() {

            assertEquals(Optional.of(VALUE), Optional.of(VALUE).or(Optional.of(VALUE2)));
        }

        @Test
        void testOr_Optional_WhenNull() {

            var present = Optional.of(VALUE);
            var exception = assertThrows(NullPointerException.class, () ->
                    present.or(NULL_OPTIONAL));
            assertEquals("other cannot be null", exception.getMessage());
        }

        @Test
        void testOr_Supplier_WhenAbsent() {

            assertEquals(Optional.of(VALUE), Optional.of(VALUE).or(Optional::absent));
        }

        @Test
        void testOr_Supplier_WhenPresent() {

            assertEquals(Optional.of(VALUE), Optional.of(VALUE).or(() -> Optional.of(VALUE2)));
        }

        @Test
        void testOr_Supplier_WhenNull() {

            var present = Optional.of(VALUE);
            var exception = assertThrows(NullPointerException.class, () ->
                    present.or(NULL_SUPPLIER));
            assertEquals("otherGetter cannot be null", exception.getMessage());
        }

        @Test
        void testOr_Supplier_WhenSupplierReturnsNull() {

            var present = Optional.of(VALUE);
            var exception = assertThrows(NullPointerException.class, () -> present.or(() -> null));
            assertEquals("otherGetter cannot return null", exception.getMessage());
        }

        @Test
        void testOrDefault() {

            assertEquals(VALUE, Optional.of(VALUE).orDefault(VALUE2));
        }

        @Test
        void testOrDefault_WhenNull() {

            var present = Optional.of(VALUE);
            var exception = assertThrows(NullPointerException.class, () -> present.orDefault(null));
            assertEquals("defaultValue cannot be null", exception.getMessage());
        }

        @Test
        void testMap() {

            assertEquals(Optional.of(VALUE2), Optional.of(VALUE).map((o) -> {
                assertEquals(VALUE, o);
                return VALUE2;
            }));
        }

        @Test
        void testMap_WhenNull() {

            var present = Optional.of(VALUE);
            var exception = assertThrows(NullPointerException.class, () -> present.map(null));
            assertEquals("mapper cannot be null", exception.getMessage());
        }

        @Test
        void testMap_WhenMapperReturnsNull() {

            var present = Optional.of(VALUE);
            var exception = assertThrows(NullPointerException.class, () ->
                    present.map((o) -> null));
            assertEquals("mapper cannot return null", exception.getMessage());
        }

        @Test
        void testFlatMap() {

            assertEquals(Optional.of(VALUE2), Optional.of(VALUE).flatMap((o) -> {
                assertEquals(VALUE, o);
                return Optional.of(VALUE2);
            }));
        }

        @Test
        void testFlatMap_WhenNull() {

            var present = Optional.of(VALUE);
            var exception = assertThrows(NullPointerException.class, () -> present.flatMap(null));
            assertEquals("mapper cannot be null", exception.getMessage());
        }

        @Test
        void testFlatMap_WhenMapperReturnsNull() {

            var present = Optional.of(VALUE);
            var exception = assertThrows(NullPointerException.class, () ->
                    present.flatMap((o) -> null));
            assertEquals("mapper cannot return null", exception.getMessage());
        }

        @Test
        void testToString() {

            assertEquals("Optional.of(" + VALUE + ")", Optional.of(VALUE).toString());
        }
    }

    @Nested
    class AbsentTests {

        @Test
        void testAbsent() {

            assertInstanceOf(Absent.class, Optional.absent());
        }

        @Test
        void testIsPresent() {

            assertFalse(Optional.absent().isPresent());
        }

        @Test
        void testIsAbsent() {

            assertTrue(Optional.absent().isAbsent());
        }

        @Test
        void testOr_Optional_WhenAbsent() {

            assertEquals(Optional.absent(), Optional.absent().or(Optional.absent()));
        }

        @Test
        void testOr_Optional_WhenPresent() {

            assertEquals(Optional.of(VALUE), Optional.absent().or(Optional.of(VALUE)));
        }

        @Test
        void testOr_Optional_WhenNull() {

            var absent = Optional.absent();
            var exception = assertThrows(NullPointerException.class, () ->
                    absent.or(NULL_OPTIONAL));
            assertEquals("other cannot be null", exception.getMessage());
        }

        @Test
        void testOr_Supplier_WhenAbsent() {

            assertEquals(Optional.absent(), Optional.absent().or(Optional::absent));
        }

        @Test
        void testOr_Supplier_WhenPresent() {

            assertEquals(Optional.of(VALUE), Optional.absent().or(() -> Optional.of(VALUE)));
        }

        @Test
        void testOr_Supplier_WhenNull() {

            var absent = Optional.absent();
            var exception = assertThrows(NullPointerException.class, () ->
                    absent.or(NULL_SUPPLIER));
            assertEquals("otherGetter cannot be null", exception.getMessage());
        }

        @Test
        void testOr_Supplier_WhenSupplierReturnsNull() {

            var absent = Optional.absent();
            var exception = assertThrows(NullPointerException.class, () -> absent.or(() -> null));
            assertEquals("otherGetter cannot return null", exception.getMessage());
        }

        @Test
        void testOrDefault() {

            assertEquals(VALUE, Optional.absent().orDefault(VALUE));
        }

        @Test
        void testOrDefault_WhenNull() {

            var absent = Optional.absent();
            var exception = assertThrows(NullPointerException.class, () -> absent.orDefault(null));
            assertEquals("defaultValue cannot be null", exception.getMessage());
        }

        @Test
        void testMap() {

            assertEquals(Optional.absent(), Optional.absent().map((o) -> fail()));
        }

        @Test
        void testMap_WhenNull() {

            var absent = Optional.absent();
            var exception = assertThrows(NullPointerException.class, () -> absent.map(null));
            assertEquals("mapper cannot be null", exception.getMessage());
        }

        @Test
        void testFlatMap() {

            assertEquals(Optional.absent(), Optional.absent().flatMap((o) -> fail()));
        }

        @Test
        void testFlatMap_WhenNull() {

            var absent = Optional.absent();
            var exception = assertThrows(NullPointerException.class, () -> absent.flatMap(null));
            assertEquals("mapper cannot be null", exception.getMessage());
        }

        @Test
        void testToString() {

            assertEquals("Optional.absent()", Optional.absent().toString());
        }
    }
}
