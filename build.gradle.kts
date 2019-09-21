plugins {
    java
    application
}

repositories {
    jcenter()
}

application {
    mainClassName = "com.viseo.fes.java.ApplicationViseo"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation("com.rabbitmq:amqp-client:5.7.3")
    implementation("org.javassist:javassist:3.25.0-GA")

    implementation("org.apache.logging.log4j:log4j-core:2.12.1")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.12.1")
    implementation("log4j:log4j:1.2.17")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
    testImplementation("org.junit.platform:junit-platform-launcher:1.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.5.2")
    testImplementation("org.assertj:assertj-core:3.13.2")
}
