plugins {
    id("io.micronaut.build.internal.aws-module")
}

dependencies {
    compileOnly(mn.micronaut.runtime)
    testImplementation(mn.micronaut.runtime)
}
