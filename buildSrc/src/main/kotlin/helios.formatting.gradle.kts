plugins {
    id("helios.base")
    id("com.diffplug.spotless")
}

spotless {
    java {
        palantirJavaFormat("2.35.0").style("GOOGLE")
        formatAnnotations()
    }
    kotlinGradle {
        ktlint()
    }
}
