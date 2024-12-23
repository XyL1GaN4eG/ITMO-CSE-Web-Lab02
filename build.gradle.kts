plugins {
    id("java")
    id("io.freefair.lombok") version "8.10"
    id("war")
}

group = "web"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("jakarta.servlet:jakarta.servlet-api:6.1.0")
}

tasks.withType<War> {
    manifest {
        attributes["Main-Class"] = "Main"
    }

    from("src/main/webapp") {
        into("/")
    }

    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree)
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(arrayOf("--release", "17"))
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.javadoc {
    options.encoding = "UTF-8"
}

tasks.create("deploy") {
    dependsOn("war")

    doLast {
        exec {
            workingDir(".")
            commandLine("bash", "deploy.sh")
        }
    }
}