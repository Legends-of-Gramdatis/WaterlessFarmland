plugins {
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
    id("com.gtnewhorizons.retrofuturagradle") version "1.4.1"
}

group = "com.slava_110.waterlessfarmland"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

minecraft {
    mcVersion.set("1.12.2")

    mcpMappingChannel.set("stable")
    mcpMappingVersion.set("39")

    username.set("Developer")
}

repositories {
    maven {
        name = "CleanroomMC Maven"
        url = uri("https://maven.cleanroommc.com")
    }
    maven {
        name = "SpongePowered Maven"
        url = uri("https://repo.spongepowered.org/maven")
    }
}

dependencies {
    val mixin = modUtils.enableMixins("zone.rong:mixinbooter:8.9") as String

    annotationProcessor(mixin) {
        isTransitive = false
    }

    annotationProcessor("org.ow2.asm:asm-debug-all:5.2")
    annotationProcessor("com.google.guava:guava:32.1.2-jre")
    annotationProcessor("com.google.code.gson:gson:2.8.9")

    api(mixin) {
        isTransitive = false
    }
}

tasks.named<JavaExec>("runObfClient") {
    workingDir("run-client-obf")
}

tasks.named<JavaExec>("runObfServer") {
    mainClass.set("net.minecraftforge.fml.relauncher.ServerLaunchWrapper")
    workingDir("run-server-obf")
}

tasks.jar {
    manifest {
        attributes(
            "FMLCorePlugin" to "com.slava_110.waterlessfarmland.WFLoadingPlugin",
            "FMLCorePluginContainsFMLMod" to "true"
        )
    }
}

tasks.processResources {
    inputs.property("version", project.version)

    filesMatching("mcmod.info") {
        expand(
            "modVersion" to project.version
        )
    }
}

// IDE Settings
idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
        inheritOutputDirs = true // Fix resources in IJ-Native runs
    }
}

tasks.processIdeaSettings {
    dependsOn(tasks.injectTags)
}