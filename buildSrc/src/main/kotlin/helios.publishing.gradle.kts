import org.gradle.kotlin.dsl.`maven-publish`

plugins {
    id("helios.base")
    `maven-publish`
}

publishing {
    repositories {
        maven {
            name = "sparky983"
            url =
                uri(
                    if (version.toString().endsWith("-SNAPSHOT")) {
                        "https://repo.sparky983.me/snapshots"
                    } else {
                        "https://repo.sparky983.me/releases"
                    },
                )
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
