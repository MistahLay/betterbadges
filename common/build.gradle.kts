// All From the https://gitlab.com/cable-mc/cobblemon-mdks :D

plugins {
    id("com.gradleup.shadow")
    id("dev.architectury.loom")
    id("architectury-plugin")
}

architectury {
    common("neoforge", "fabric")
}

var generatedResources: File = file("src/main/generated")

sourceSets {
    main {
        resources.srcDir(generatedResources)
    }
}

loom {
    silentMojangMappingsLicense()
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings(loom.layered() {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-1.21.1:2024.11.17@zip")
    })

    modCompileOnly("net.fabricmc:fabric-loader:${property("fabric_loader_version")}")

    modImplementation("com.cobblemon:mod:${property("cobblemon_version")}") { isTransitive = false }
    modImplementation("dev.architectury:architectury:${property("architectury_api_version")}")

    modImplementation("io.wispforest:owo-lib:${property("owo_fabric_version")}")
    annotationProcessor("io.wispforest:owo-lib:${property("owo_fabric_version")}")
    include("io.wispforest:owo-sentinel:${property("owo_fabric_version")}")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${property("junit_version")}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${property("junit_version")}")
}

tasks.test {
    useJUnitPlatform()
}