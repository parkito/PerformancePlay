import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

group = "ru.siksmfp.server.blocking"
version = "0.1.1"

plugins {
    application
//    id("com.palantir.docker") version "0.25.0"
//    id("com.palantir.docker-run") version "0.25.0"
    kotlin("jvm") version "1.3.70"
    id("org.springframework.boot") version "2.2.5.RELEASE"
    kotlin("plugin.spring") version "1.3.61"
}

java.sourceCompatibility = JavaVersion.VERSION_11

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.jsmart:zerocode-tdd-jupiter:1.3.17")
    implementation("org.postgresql:postgresql:42.2.10")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testCompile("org.junit.jupiter:junit-jupiter-engine:5.4.1")
}
repositories {
    jcenter()
    mavenCentral()
}

tasks.withType<BootJar>().configureEach {
    mainClassName = "ru.siksmfp.server.blocking.MainKt"
    launchScript()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
//apply(plugin = "com.palantir.docker")

//docker {
//    name = "mrkulli/ktdemo:".plus(version)
//    uri("mrkulli/ktdemo:".plus(version))
//    tag("name", "ktdemo")
//    buildArgs(ImmutableMap.of("name", "ktdemo"))
//    copySpec.from("build").into("build")
//    pull(true)
//    setDockerfile(file("Dockerfile"))
//}
//
//dockerRun {
//    name = "ktdemo"
//    image = "mrkulli/ktdemo:".plus(version)
//    ports("8080:8080")
//}
