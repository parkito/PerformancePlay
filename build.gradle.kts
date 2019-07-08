group = "ru.siksmfp.kotlin.areon"
version = "0.1.1"

val appArchiveName = "areon"

plugins {
    application
    kotlin("jvm") version "1.3.21"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

application {
    mainClassName = "samples.HelloWorldKt"
}

dependencies {
    compile(kotlin("stdlib"))
    compile("io.aeron:aeron-all:1.19.1")
    testCompile("org.junit.jupiter:junit-jupiter-engine:5.4.1")
}

repositories {
    jcenter()
    mavenCentral()
}

val jar by tasks.getting(Jar::class) {
    archiveName = "$appArchiveName.jar"
    into("META-INF") {
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }
