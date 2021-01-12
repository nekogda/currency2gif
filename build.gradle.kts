plugins {
    java
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
}

repositories {
    mavenCentral()
}

java {
    group = "com.bloody-corp"
    version = "0.0.1-SNAPSHOT"
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

ext.set("springCloudVersion", "2020.0.0")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.github.openfeign:feign-core:11.0")
    implementation("io.github.openfeign:feign-jackson:11.0")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${ext.get("springCloudVersion")}")
    }
}

tasks.test {
    testLogging {
        events("passed", "skipped", "failed")
    }
    useJUnitPlatform()
}
