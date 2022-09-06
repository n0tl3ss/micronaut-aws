plugins {
    id("io.micronaut.build.internal.module")
}

dependencies {
    api(project(":aws-common"))
    api("io.micronaut.discovery:micronaut-discovery-client")
    testImplementation(mn.micronaut.http.server.netty)
}
