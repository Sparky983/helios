import me.sparky983.helios.annotations.Experimental;

@SuppressWarnings("JavaModuleNaming")
@Experimental // 0.x.y
module me.sparky983.helios {
  requires static java.compiler; // for Javadocs

  exports me.sparky983.helios.optional;
}
