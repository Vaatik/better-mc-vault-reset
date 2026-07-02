plugins {
    kotlin("jvm") version "2.3.20"
    id("com.gradleup.shadow") version "9.4.3"
}

group = "com.bettermc"
version = "1.1.1"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.1.2.build.+")
    implementation(kotlin("stdlib"))

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testImplementation("io.mockk:mockk:1.14.3")
}

kotlin {
    jvmToolchain(25)
}

tasks {
    test {
        useJUnitPlatform()
    }

    shadowJar {
        archiveClassifier.set("")
        archiveFileName.set("${project.name}-${project.version}.jar")
    }

    build {
        dependsOn(shadowJar)
    }

    processResources {
        val props = mapOf(
            "version" to project.version,
            "name" to project.name
        )
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}
