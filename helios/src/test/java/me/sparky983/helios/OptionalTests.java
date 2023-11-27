package me.sparky983.helios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.NoSuchElementException;
import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

  @Test
  void testFromNullable_WhenNull() {
    assertEquals(Optional.absent(), Optional.fromNullable(null));
  }

  @Test
  void testFromNullable_WhenNonNull() {
    assertEquals(Optional.present(VALUE), Optional.fromNullable(VALUE));
  }

  @Test
  void testFrom_WhenEmpty() {
    assertEquals(Optional.absent(), Optional.from(java.util.Optional.empty()));
  }

  @Test
  void testFrom_WhenPresent() {
    assertEquals(Optional.present(VALUE), Optional.from(java.util.Optional.of(VALUE)));
  }

  @Nested
  class PresentTests {
    @Test
    void testPresent() {
      assertEquals(VALUE, ((Present<?>) Optional.present(VALUE)).value());
    }

    @Test
    void testPresent_WhenNull() {
      var thrown = assertThrows(NullPointerException.class, () -> Optional.present(null));
      assertEquals("value cannot be null", thrown.getMessage());
    }

    @Test
    void testIsPresent() {
      assertTrue(Optional.present(VALUE).isPresent());
    }

    @Test
    void testIsAbsent() {
      assertFalse(Optional.present(VALUE).isAbsent());
    }

    @Test
    void testOr_Optional_WhenAbsent() {
      assertEquals(Optional.present(VALUE), Optional.present(VALUE).or(Optional.absent()));
    }

    @Test
    void testOr_Optional_WhenPresent() {
      assertEquals(Optional.present(VALUE), Optional.present(VALUE).or(Optional.present(VALUE2)));
    }

    @Test
    void testOr_Optional_WhenNull() {
      var present = Optional.present(VALUE);
      var thrown = assertThrows(NullPointerException.class, () -> present.or(NULL_OPTIONAL));
      assertEquals("other cannot be null", thrown.getMessage());
    }

    @Test
    void testOr_Supplier_WhenAbsent() {
      assertEquals(Optional.present(VALUE), Optional.present(VALUE).or(Optional::absent));
    }

    @Test
    void testOr_Supplier_WhenPresent() {
      assertEquals(
          Optional.present(VALUE), Optional.present(VALUE).or(() -> Optional.present(VALUE2)));
    }

    @Test
    void testOr_Supplier_WhenNull() {
      var present = Optional.present(VALUE);
      var thrown = assertThrows(NullPointerException.class, () -> present.or(NULL_SUPPLIER));
      assertEquals("otherSupplier cannot be null", thrown.getMessage());
    }

    @Test
    void testOrDefault() {
      assertEquals(VALUE, Optional.present(VALUE).orDefault(VALUE2));
    }

    @Test
    void testOrDefault_WhenNull() {
      var present = Optional.present(VALUE);
      var thrown = assertThrows(NullPointerException.class, () -> present.orDefault(null));
      assertEquals("defaultValue cannot be null", thrown.getMessage());
    }

    @Test
    void testOrGet() {
      assertEquals(VALUE, Optional.present(VALUE).orGet(Assertions::fail));
    }

    @Test
    void testOrGet_WhenNull() {
      var present = Optional.present(VALUE);
      var thrown = assertThrows(NullPointerException.class, () -> present.orGet(null));
      assertEquals("defaultValueSupplier cannot be null", thrown.getMessage());
    }

    @Test
    void testOrNull() {
      assertEquals(VALUE, Optional.present(VALUE).orNull());
    }

    @Test
    void testOrThrow() {
      var exception = new Exception();
      var value = assertDoesNotThrow(() -> Optional.present(VALUE).orThrow(() -> exception));
      assertEquals(VALUE, value);
    }

    @Test
    void testOrThrow_WhenNull() {
      var present = Optional.present(VALUE);
      var thrown = assertThrows(NullPointerException.class, () -> present.orThrow(null));
      assertEquals("exceptionSupplier cannot be null", thrown.getMessage());
    }

    @Test
    void testExpect() {
      var value = Optional.present(VALUE).expect("message");
      assertEquals(VALUE, value);
    }

    @Test
    void testExpect_WhenNull() {
      var present = Optional.present(VALUE);
      var thrown = assertThrows(NullPointerException.class, () -> present.expect(null));
      assertEquals("message cannot be null", thrown.getMessage());
    }

    @Test
    void testMap() {
      assertEquals(Optional.present(VALUE2), Optional.present(VALUE).map((o) -> {
        assertEquals(VALUE, o);
        return VALUE2;
      }));
    }

    @Test
    void testMap_WhenNull() {
      var present = Optional.present(VALUE);
      var thrown = assertThrows(NullPointerException.class, () -> present.map(null));
      assertEquals("mapper cannot be null", thrown.getMessage());
    }

    @Test
    void testMap_WhenMapperReturnsNull() {
      var present = Optional.present(VALUE);
      var thrown = assertThrows(NullPointerException.class, () -> present.map((o) -> null));
      assertEquals("mapper cannot return null", thrown.getMessage());
    }

    @Test
    void testFlatMap() {
      assertEquals(Optional.present(VALUE2), Optional.present(VALUE).flatMap((o) -> {
        assertEquals(VALUE, o);
        return Optional.present(VALUE2);
      }));
    }

    @Test
    void testFlatMap_WhenNull() {
      var present = Optional.present(VALUE);
      var thrown = assertThrows(NullPointerException.class, () -> present.flatMap(null));
      assertEquals("mapper cannot be null", thrown.getMessage());
    }

    @Test
    void testFlatMap_WhenMapperReturnsNull() {
      var present = Optional.present(VALUE);
      var thrown = assertThrows(NullPointerException.class, () -> present.flatMap((o) -> null));
      assertEquals("mapper cannot return null", thrown.getMessage());
    }

    @Test
    void testFilter_WhenPredicateMatches() {
      assertEquals(Optional.present(VALUE), Optional.present(VALUE).filter((o) -> {
        assertEquals(VALUE, o);
        return true;
      }));
    }

    @Test
    void testFilter_WhenPredicateDoesNotMatch() {
      assertEquals(Optional.absent(), Optional.present(VALUE).filter((o) -> {
        assertEquals(VALUE, o);
        return false;
      }));
    }

    @Test
    void testFilter_WhenNull() {
      var present = Optional.present(VALUE);
      var thrown = assertThrows(NullPointerException.class, () -> present.filter(null));
      assertEquals("predicate cannot be null", thrown.getMessage());
    }

    @Test
    void testHashCode() {
      var value = new Object();
      assertEquals(value.hashCode(), Optional.present(value).hashCode());
    }

    @Test
    void testToString() {
      assertEquals("Present(" + VALUE + ")", Optional.present(VALUE).toString());
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
      assertEquals(Optional.present(VALUE), Optional.absent().or(Optional.present(VALUE)));
    }

    @Test
    void testOr_Optional_WhenNull() {
      var absent = Optional.absent();
      var thrown = assertThrows(NullPointerException.class, () -> absent.or(NULL_OPTIONAL));
      assertEquals("other cannot be null", thrown.getMessage());
    }

    @Test
    void testOr_Supplier_WhenAbsent() {
      assertEquals(Optional.absent(), Optional.absent().or(Optional::absent));
    }

    @Test
    void testOr_Supplier_WhenPresent() {
      assertEquals(Optional.present(VALUE), Optional.absent().or(() -> Optional.present(VALUE)));
    }

    @Test
    void testOr_Supplier_WhenNull() {
      var absent = Optional.absent();
      var thrown = assertThrows(NullPointerException.class, () -> absent.or(NULL_SUPPLIER));
      assertEquals("otherSupplier cannot be null", thrown.getMessage());
    }

    @Test
    void testOr_Supplier_WhenSupplierReturnsNull() {
      var absent = Optional.absent();
      var thrown = assertThrows(NullPointerException.class, () -> absent.or(() -> null));
      assertEquals("otherSupplier cannot return null", thrown.getMessage());
    }

    @Test
    void testOrDefault() {
      assertEquals(VALUE, Optional.absent().orDefault(VALUE));
    }

    @Test
    void testOrDefault_WhenNull() {
      var absent = Optional.absent();
      var thrown = assertThrows(NullPointerException.class, () -> absent.orDefault(null));
      assertEquals("defaultValue cannot be null", thrown.getMessage());
    }

    @Test
    void testOrGet() {
      assertEquals(VALUE, Optional.absent().orGet(() -> VALUE));
    }

    @Test
    void testOrGet_WhenNull() {
      var absent = Optional.absent();
      var thrown = assertThrows(NullPointerException.class, () -> absent.orGet(null));
      assertEquals("defaultValueSupplier cannot be null", thrown.getMessage());
    }

    @Test
    void testOrGet_WhenSupplierReturnsNull() {
      var absent = Optional.absent();
      var thrown = assertThrows(NullPointerException.class, () -> absent.orGet(() -> null));
      assertEquals("defaultValueSupplier cannot return null", thrown.getMessage());
    }

    @Test
    void testOrNull() {
      assertNull(Optional.absent().orNull());
    }

    @Test
    void testOrThrow() {
      var absent = Optional.absent();
      var exception = new Exception();
      var thrown = assertThrows(Exception.class, () -> absent.orThrow(() -> exception));
      assertEquals(exception, thrown);
    }

    @Test
    void testOrThrow_WhenNull() {
      var absent = Optional.absent();
      var thrown = assertThrows(NullPointerException.class, () -> absent.orThrow(null));
      assertEquals("exceptionSupplier cannot be null", thrown.getMessage());
    }

    @Test
    void testOrThrow_WhenReturnsNull() {
      var absent = Optional.absent();
      var thrown = assertThrows(NullPointerException.class, () -> absent.orThrow(() -> null));
      assertEquals("exceptionSupplier cannot return null", thrown.getMessage());
    }

    @Test
    void testExpect() {
      var absent = Optional.absent();
      var thrown =
          assertThrows(NoSuchElementException.class, () -> absent.expect("optional to be present"));
      assertEquals("Expected optional to be present", thrown.getMessage());
    }

    @Test
    void testExpect_WhenNull() {
      var absent = Optional.absent();
      var thrown = assertThrows(NullPointerException.class, () -> absent.expect(null));
      assertEquals("message cannot be null", thrown.getMessage());
    }

    @Test
    void testMap() {
      assertEquals(Optional.absent(), Optional.absent().map((o) -> fail()));
    }

    @Test
    void testMap_WhenNull() {
      var absent = Optional.absent();
      var thrown = assertThrows(NullPointerException.class, () -> absent.map(null));
      assertEquals("mapper cannot be null", thrown.getMessage());
    }

    @Test
    void testFlatMap() {
      assertEquals(Optional.absent(), Optional.absent().flatMap((o) -> fail()));
    }

    @Test
    void testFlatMap_WhenNull() {
      var absent = Optional.absent();
      var thrown = assertThrows(NullPointerException.class, () -> absent.flatMap(null));
      assertEquals("mapper cannot be null", thrown.getMessage());
    }

    @Test
    void testFilter() {
      assertEquals(Optional.absent(), Optional.absent().filter((o) -> fail()));
    }

    @Test
    void testFilter_WhenNull() {
      var absent = Optional.absent();
      var thrown = assertThrows(NullPointerException.class, () -> absent.filter(null));
      assertEquals("predicate cannot be null", thrown.getMessage());
    }

    @Test
    void testHashCode() {
      assertEquals(0, Optional.absent().hashCode());
    }

    @Test
    void testToString() {
      assertEquals("Absent()", Optional.absent().toString());
    }
  }
}
