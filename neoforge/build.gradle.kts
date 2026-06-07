// All From the https://gitlab.com/cable-mc/cobblemon-mdks :D

plugins {
    id("com.gradleup.shadow")
    id("dev.architectury.loom")
    id("architectury-plugin")
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

loom {
    enableTransitiveAccessWideners.set(true)
    silentMojangMappingsLicense()
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    maven("https://thedarkcolour.github.io/KotlinForForge/")
    maven("https://maven.neoforged.net/releases/")
    maven("https://maven.su5ed.dev/releases")
}

val shadowBundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    minecraft("net.minecraft:minecraft:${property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    neoForge("net.neoforged:neoforge:${property("neoforge_version")}")

    modImplementation("dev.architectury:architectury-neoforge:${property("architectury_api_version")}")
    modImplementation("com.cobblemon:neoforge:${property("cobblemon_version")}") { isTransitive = false }

    modApi("org.sinytra.forgified-fabric-api:fabric-api-base:0.4.42+d1308dedd1") { exclude(group = "fabric-api") }

    modImplementation("io.wispforest:endec:${property("endec_version")}")
    modImplementation("io.wispforest.endec:gson:${property("endec_gson_version")}")
    modImplementation("io.wispforest.endec:jankson:${property("endec_jankson_version")}")
    modImplementation("io.wispforest.endec:netty:${property("endec_netty_version")}")

    modImplementation("io.wispforest:owo-lib-neoforge:${property("owo_neoforge_version")}")
    annotationProcessor("io.wispforest:owo-lib-neoforge:${property("owo_neoforge_version")}")
    include("io.wispforest:owo-sentinel:${property("owo_neoforge_version")}")

    forgeRuntimeLibrary("thedarkcolour:kotlinforforge-neoforge:${property("kotlin_for_forge_version")}") {
        exclude("net.neoforged.fancymodloader", "loader")
    }

    implementation(project(":common", configuration = "namedElements"))
    "developmentNeoForge"(project(":common", configuration = "namedElements")) {
        isTransitive = false
    }
    shadowBundle(project(":common", configuration = "transformProductionFabric"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:${property("junit_version")}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${property("junit_version")}")
}

tasks {
    test {
        useJUnitPlatform()
    }

    processResources {
        inputs.property("version", project.version)

        filesMatching("META-INF/neoforge.mods.toml") {
            expand(project.properties)
        }
    }

    jar {
        archiveBaseName.set("${rootProject.property("archives_base_name")}-${project.name}")
        archiveClassifier.set("dev-slim")
    }

    shadowJar {
        exclude("fabric.mod.json")
        archiveClassifier.set("dev-shadow")
        archiveBaseName.set("${rootProject.property("archives_base_name")}-${project.name}")
        configurations = listOf(shadowBundle)
    }

    remapJar {
        dependsOn(shadowJar)
        inputFile.set(shadowJar.flatMap { it.archiveFile })
        archiveBaseName.set("${rootProject.property("archives_base_name")}-${project.name}")
        archiveVersion.set("${rootProject.version}")
    }
}
