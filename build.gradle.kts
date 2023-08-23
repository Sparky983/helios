plugins {
    `java-library`

    id("com.diffplug.spotless") version "6.20.0"
    id("org.checkerframework") version "0.6.27"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(20))
    }
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

checkerFramework {
    checkers = listOf(
        "org.checkerframework.checker.nullness.NullnessChecker",
    )
    excludeTests = true
}

tasks {
    javadoc {
        options {
            (this as StandardJavadocDocletOptions).run {
                tags("helios.implNote:a:Implementation Note:")
                tags("helios.apiNote:a:API Note:")
                tags("helios.examples:a:Examples:")
            }
        }
    }
    test {
        useJUnitPlatform()
    }
}
