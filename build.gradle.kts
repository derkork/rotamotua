description = "Sample Kotlin JSR 223 scripting jar with local (in-process) compilation and evaluation"

val kotlinVersion = "1.2.60"
val jacksonVersion = "2.9.6"
val junitPlatformVersion = "1.1.0"
val spekVersion = "1.1.5"

plugins {
    application
    kotlin("jvm") version "1.2.60"

}

application {
    mainClassName = "com.ancientlightstudios.rotamotua.MainKt"
}

dependencies {
    implementation(kotlin("stdlib-jdk8", kotlinVersion))
    implementation("org.luaj:luaj-jse:3.0.1")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.github.microutils:kotlin-logging:1.5.9")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    testImplementation(kotlin("reflect", kotlinVersion))
    testImplementation(kotlin("test", kotlinVersion))
    testImplementation("org.amshove.kluent:kluent:1.40")

    testImplementation("org.jetbrains.spek:spek-api:$spekVersion") {
        exclude(group = "org.jetbrains.kotlin")
    }

    testRuntimeOnly("org.jetbrains.spek:spek-junit-platform-engine:$spekVersion") {
        exclude(group = "org.jetbrains.kotlin")
        exclude(group = "org.junit.platform")
    }

    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformVersion") {
        because("Needed to run tests IDEs that bundle an older version")
    }
}


tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek")
    }
}

repositories {
    jcenter()
}