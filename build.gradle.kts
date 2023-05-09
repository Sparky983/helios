import org.checkerframework.gradle.plugin.CheckerFrameworkExtension

plugins {
    `java-library`

    id("org.checkerframework") version "0.6.27"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
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

configure<CheckerFrameworkExtension> {
    checkers = listOf(
        "org.checkerframework.checker.nullness.NullnessChecker"
    )
}
