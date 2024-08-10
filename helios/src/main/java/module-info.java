import me.sparky983.helios.annotations.Experimental;

/**
 * Contains the Helios API.
 * <h2>Warning</h2>
 * Helios has not been fully released, and as such, APIs may break between minor versions
 *
 * @since 0.1
 */
@SuppressWarnings("module") // suppress terminal digits warning
@Experimental // 0.x.y
module me.sparky983.helios {
  requires static java.compiler; // for Javadocs

  exports me.sparky983.helios;
}
