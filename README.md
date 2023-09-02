# Helios

Helios is a core library that improves upon several shortcomings of the Java standard library.

## Warnings

- This library is still in development, and as such, is subject to change at any time.
- The APIs are not designed with performance in mind, and as such, should not be used in 
  performance-critical code.

## Installation

### pom.xml

```xml
<repository>
    <id>sparky983-snapshots</id>
    <url>https://repo.sparky983.me/snapshots</url>
</repository>

<dependency>
    <groupId>me.sparky983</groupId>
    <artifactId>helios</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

### build.gradle

```groovy
repositories {
    maven { url 'https://repo.sparky983.me/snapshots' }
}

dependencies {
    implementation 'me.sparky983:helios:0.1.0-SNAPSHOT'
}
```

### build.gradle.kts

```kts
repositories {
    maven("https://repo.sparky983.me/snapshots")
}

dependencies {
    implementation("me.sparky983:helios:0.1.0-SNAPSHOT")
}
```
