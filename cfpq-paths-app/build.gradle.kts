plugins {
    kotlin("jvm") version "2.3.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":solver"))
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass = "org.ucfs.paths.MainKt"
}

tasks.register<JavaExec>("runSimpleExamples") {
    dependsOn("classes")
    mainClass.set("simple_examples.Example_1Kt")
    classpath = sourceSets["main"].runtimeClasspath
}