
[![Kroto+](https://raw.githubusercontent.com/marcoferrer/kroto-plus/master/kp-logo.svg?sanitize=true)](https://github.com/marcoferrer/kroto-plus)
## Kotlin Coroutines gRPC Template

This is a template project for getting started building __[gRPC](https://github.com/grpc/grpc-java)__ clients and services using __[Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines)__ and __[kroto-plus](https://github.com/marcoferrer/kroto-plus)__ code generation.

* Template Variations
  * [Using protoc-gen-grpc-coroutines](https://github.com/marcoferrer/kotlin-coroutines-gRPC-template) (Basic)
  * [Using protoc-gen-kroto-plus](https://github.com/marcoferrer/kotlin-coroutines-gRPC-template/tree/kroto-plus-template ) (Advanced Features)
  * [Using protobuf-lite](https://github.com/marcoferrer/kotlin-coroutines-gRPC-template/tree/protobuf-lite)

The template can be built and ran using the following command:
```bash
git clone https://github.com/marcoferrer/kotlin-coroutines-gRPC-template && \
cd kotlin-coroutines-gRPC-template && \
./gradlew run 
```

This template covers:
* Recommeded Build Configuration
* Implementing A Service
* Instantiating A Client Stub
  * Attaching a ```coroutineContext```
* Client and Server Rpc Method Examples:
  * Unary
  * Client Streaming
  * Server Streaming
  * BiDirectional Streaming
