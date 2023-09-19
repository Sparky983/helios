# Helios

Helios is a core library that improves upon several shortcomings of the Java core libraries.

## Quick Links

- [📔 Javadocs](https://repo.sparky983.me/javadoc/releases/me/sparky983/helios/0.1.0/raw/me.sparky983.helios/module-summary.html)

## Warnings

- Helios is unstable, and as such, is subject to change at any time.
- The APIs are not designed with performance in mind, and as such, should not be used in 
  performance-critical code.

## Installation

### pom.xml

```xml
<repository>
    <id>sparky983</id>
    <url>https://repo.sparky983.me/releases</url>
</repository>

<dependency>
    <groupId>me.sparky983</groupId>
    <artifactId>helios</artifactId>
    <version>0.1.0</version>
</dependency>
```

### build.gradle

```groovy
repositories {
    maven { url 'https://repo.sparky983.me/releases' }
}

dependencies {
    implementation 'me.sparky983:helios:0.1.0'
}
```

### build.gradle.kts

```kts
repositories {
    maven("https://repo.sparky983.me/releases")
}

dependencies {
    implementation("me.sparky983:helios:0.1.0")
}
```
