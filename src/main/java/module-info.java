import me.sparky983.helios.annotations.Experimental;

@SuppressWarnings("JavaModuleNaming")
@Experimental // 0.x.y
module me.sparky983.helios {
  requires static org.checkerframework.checker.qual;
  requires static java.compiler; // for Javadocs

  exports me.sparky983.helios.annotations;
  exports me.sparky983.helios.optional;
}
