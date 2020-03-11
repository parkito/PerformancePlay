import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import org.gradle.api.JavaVersion.VERSION_11
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

group = "ru.siksmfp.server.blocking"
version = "0.1.1"

val springBootVersion = "2.2.5.RELEASE"

plugins {
    kotlin("jvm") version "1.3.70"
    kotlin("plugin.spring") version "1.3.61"
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("com.bmuschko.docker-remote-api") version "6.1.4"
}

java.sourceCompatibility = VERSION_11

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc:$springBootVersion")
    implementation("org.jsmart:zerocode-tdd-jupiter:1.3.17")
    implementation("org.postgresql:postgresql:42.2.10")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testCompile("org.junit.jupiter:junit-jupiter-engine:5.4.1")
}
repositories {
    jcenter()
    mavenCentral()
    gradlePluginPortal()
}

tasks.getByName<BootJar>("bootJar") {
    archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
    copy {
        from(archiveFileName)
        into("docker/blocking-server.jar")
    }
}

tasks.create("copyJar", Copy::class) {
    from("${projectDir}/build/libs/blocking_server.jar")
    into("docker/")
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.create("buildImage", DockerBuildImage::class) {
    inputDir.set(file("docker/"))
    images.add("parkito/blocking-server")
}