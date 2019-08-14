import com.google.protobuf.gradle.*

val coroutines_version: String by project
val grpc_version: String by project
val krotoplus_version: String by project
val protobuf_version: String by project



apply(plugin = "kotlin")
apply(plugin = "com.google.protobuf")
apply(plugin = "idea")

plugins {
    kotlin("jvm")
}

dependencies {

    // For jdk 9+ you need to include javax.annotations
    // The reason is outlined in this grpc issue
    // https://github.com/grpc/grpc-java/issues/4725
    compileOnly("javax.annotation:javax.annotation-api:1.2")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    implementation("com.github.marcoferrer.krotoplus:kroto-plus-coroutines:$krotoplus_version")

    implementation("com.google.protobuf:protobuf-java:$protobuf_version")
    implementation("io.grpc:grpc-protobuf:$grpc_version")
    implementation("io.grpc:grpc-stub:$grpc_version")
}

protobuf {
    protoc { 
        artifact = "com.google.protobuf:protoc:$protobuf_version"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpc_version"
        }
        id("coroutines") {
            artifact = "com.github.marcoferrer.krotoplus:protoc-gen-grpc-coroutines:$krotoplus_version:jvm8@jar"
        }
    }

    generateProtoTasks {

        all().forEach {
            it.builtins {
                remove("java")
            }
            it.plugins {
                id("grpc") {
                    option("lite")
                }
                id("coroutines")
                id("java") {
                    option("lite")
                }
            }
        }
    }
}