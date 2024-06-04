plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7"
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
    id("com.diffplug.spotless") version "6.25.0"
}

group = "fi.fabianadrian"
version = "1.2.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")

    implementation("org.spongepowered:configurate-yaml:4.1.2") {
        exclude("org.yaml")
    }

    implementation("org.incendo:cloud-paper:2.0.0-beta.7")
    implementation("org.incendo:cloud-minecraft-extras:2.0.0-beta.6")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        minimize()
        sequenceOf(
            "org.incendo.cloud",
            "org.spongepowered.configurate",
            "io.leangen.geantyref"
        ).forEach { pkg ->
            relocate(pkg, "fi.fabianadrian.fawarp.dependency.$pkg")
        }
    }
}

paper {
    main = "fi.fabianadrian.fawarp.FAWarp"
    apiVersion = "1.19"
    authors = listOf("FabianAdrian")
}

spotless {
    java {
        endWithNewline()
        formatAnnotations()
        indentWithTabs()
        removeUnusedImports()
        trimTrailingWhitespace()
    }
}