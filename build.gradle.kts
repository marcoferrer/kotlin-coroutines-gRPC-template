import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    id("com.google.protobuf") version "0.8.9"
    id("org.jetbrains.kotlin.jvm") version "1.3.40"
}

application {
    mainClassName = "MainKt"
}

group = "com.github.marcoferrer.krotoplus"
version = "1.0-SNAPSHOT"

val coroutines_version: String by project
val grpc_version: String by project
val krotoplus_version: String by project
val protobuf_version: String by project

allprojects {
    repositories {
        mavenCentral()
        if(krotoplus_version.endsWith("SNAPSHOT")){
            maven("https://oss.jfrog.org/artifactory/oss-snapshot-local")
        }
    }    
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    
    implementation("io.grpc:grpc-protobuf:$grpc_version")
    implementation("io.grpc:grpc-stub:$grpc_version")
    implementation("io.grpc:grpc-netty:$grpc_version")

    implementation("com.github.marcoferrer.krotoplus:kroto-plus-coroutines:$krotoplus_version")
    implementation("com.google.protobuf:protobuf-java:$protobuf_version")
    
    implementation(project(":api"))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs += listOf("-Xuse-experimental=kotlinx.coroutines.ObsoleteCoroutinesApi")
    }
}
