plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.serialization") version "1.9.25"
    id("org.jetbrains.dokka") version "1.9.20"
    id("jacoco")
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
    application
    `maven-publish`
}

group = "org.kotlin.tools.verbs"
version = "1.0.0"

application {
    mainClass.set("org.kotlin.tools.verbs.CLIKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.6")
    
    testImplementation(kotlin("test"))
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("net.jqwik:jqwik:1.9.3")
    testImplementation("net.jqwik:jqwik-kotlin:1.9.3")
    testImplementation("org.assertj:assertj-core:3.27.7")
}

tasks.test {
    useJUnit()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.register<Test>("propertyTest") {
    description = "Runs property-based tests"
    group = "verification"
    useJUnitPlatform {
        includeEngines("jqwik")
    }
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.8".toBigDecimal()
            }
        }
    }
}

kotlin {
    jvmToolchain(11)
}



// Detekt configuration
detekt {
    config = files("$projectDir/config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    allRules = false
    ignoreFailures = true
}

// Dokka configuration
tasks.dokkaHtml {
    outputDirectory.set(file("$buildDir/dokka"))
    dokkaSourceSets {
        named("main") {
            moduleName.set("Verbs")
            includes.from("Module.md")
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
} 