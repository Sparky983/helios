plugins {
    id("helios.java")
    id("helios.publishing")
    id("helios.junit-platform")
    id("helios.formatting")
}

dependencies {
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
