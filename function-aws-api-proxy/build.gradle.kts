plugins {
    id("io.micronaut.build.internal.aws-module")
}

dependencies {
    annotationProcessor(mn.micronaut.graal)
    compileOnly(mn.micronaut.security)
    implementation(mn.reactor)
    api(mn.micronaut.http.server)
    api(libs.managed.aws.serverless.core) {
        exclude(group = "org.slf4j", module = "slf4j-api")
        exclude(group = "javax.servlet", module = "javax.servlet-api")
        exclude(group = "com.fasterxml.jackson.module", module = "jackson-module-afterburner")
        exclude(group = "commons-logging")
    }
    api(libs.managed.jcl.over.slf4j)
    api(projects.functionAws)
    api(projects.awsCommon)
    testAnnotationProcessor(mn.micronaut.validation)
    testImplementation(mn.micronaut.validation)
    testImplementation(mn.micronaut.inject.java)
    testImplementation(mn.micronaut.http.client)
    testImplementation(mn.micronaut.security)
    testImplementation(mn.micronaut.views.handlebars)
    testImplementation(libs.jackson.afterburner)
    testImplementation(libs.servlet.api)
    testImplementation(libs.fileupload)
}

spotless {
    java {
        targetExclude("**/io/micronaut/function/aws/proxy/QueryStringDecoder.java","**/io/micronaut/function/aws/proxy/cookie/*.java")
    }
}
