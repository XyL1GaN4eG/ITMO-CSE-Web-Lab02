plugins {
    id("java")
    id("io.freefair.lombok") version "8.10"
}

group = "xyl1gan4eg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("com.google.code.gson:gson:2.11.0")

}

tasks.test {
    useJUnitPlatform()
}