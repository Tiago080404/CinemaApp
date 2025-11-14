plugins {
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.6"
    id("io.freefair.lombok") version "9.0.0"
    `java`
    `maven-publish`
}

group = "com.kinosoftware"
version = "0.0.1-SNAPSHOT"
description = "backend"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(libs.org.springframework.boot.spring.boot.starter.data.jpa)
    implementation(libs.org.springframework.boot.spring.boot.starter.web)
    implementation(libs.org.flywaydb.flyway.core)
    implementation(libs.org.flywaydb.flyway.database.postgresql)
    implementation(libs.org.springdoc.springdoc.openapi.starter.webmvc.ui)
    runtimeOnly(libs.org.postgresql.postgresql)
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.testcontainers:postgresql")
}


publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
