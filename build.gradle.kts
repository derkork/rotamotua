description = "Sample Kotlin JSR 223 scripting jar with local (in-process) compilation and evaluation"

val kotlinVersion = "1.2.60"

plugins {
    application
    kotlin("jvm") version "1.2.60"
}

application {
    mainClassName = "com.ancientlightstudios.rotamotua.MainKt"
}

dependencies {
    compile(kotlin("stdlib-jdk8", kotlinVersion))
    compile("org.luaj:luaj-jse:3.0.1")
    compile("ch.qos.logback:logback-classic:1.2.3")
    compile("io.github.microutils:kotlin-logging:1.5.9")

}


repositories {
    mavenCentral()
}