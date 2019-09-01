## Kotlin Coroutines gRPC Template

This is a template project for getting started building __[gRPC](https://github.com/grpc/grpc-java)__ clients and services using __[Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines)__ and __[kroto-plus](https://github.com/marcoferrer/kroto-plus)__ code generation.

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



Task :run FAILED 
Unary Response: Hello there, John! 
Bidi Response: Hello there, person #0! 
Bidi Response: Hello there, person #1! 
Bidi Response: Hello there, person #2! 
Bidi Response: Hello there, person #3! 
Bidi Response: Hello there, person #4! 
Exception in thread "main" io.grpc.StatusRuntimeException: UNKNOWN: Receiver class com.github.marcoferrer.krotoplus.coroutines.server.ServerRequestStreamChannel does not define or inherit an implementation of the resolved method abstract cancel(Ljava/util/concurrent/CancellationException;)V of interface kotlinx.coroutines.channels.ReceiveChannel.