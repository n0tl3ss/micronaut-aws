plugins {
    id("java-library")
    id("io.micronaut.build.internal.aws-tests")
}

repositories {
    mavenCentral()
}

val micronautVersion: String by project

dependencies {
    testAnnotationProcessor(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    testAnnotationProcessor("io.micronaut:micronaut-inject-java")

    testImplementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    testImplementation(project(":function-aws"))
    testImplementation(project(":function-client-aws"))
}

tasks {
    named("test", Test::class) {
        useJUnitPlatform()

        systemProperty("aws.accessKeyId", "XXX")
        systemProperty("aws.secretKey", "YYY")
        systemProperty("aws.region", "us-east-1")
    }
}

java {
    sourceCompatibility = JavaVersion.toVersion("1.8")
    targetCompatibility = JavaVersion.toVersion("1.8")
}
