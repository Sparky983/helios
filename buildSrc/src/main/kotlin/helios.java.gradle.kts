import org.gradle.external.javadoc.StandardJavadocDocletOptions

plugins {
    id("helios.base")
    `java-library`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(20))
    }
    withJavadocJar()
    withSourcesJar()
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
}