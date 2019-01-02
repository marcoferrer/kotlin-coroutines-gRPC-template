import com.github.marcoferrer.krotoplus.coroutines.launchProducerJob
import com.github.marcoferrer.krotoplus.coroutines.withCoroutineContext
import io.grpc.Channel
import io.grpc.Server
import io.grpc.examples.helloworld.GreeterCoroutineGrpc
import io.grpc.examples.helloworld.send
import io.grpc.inprocess.InProcessChannelBuilder
import io.grpc.inprocess.InProcessServerBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach

val server: Server = InProcessServerBuilder
    .forName("helloworld")
    .addService(GreeterService())
    .directExecutor()
    .build()
    .start()

val channel: Channel = InProcessChannelBuilder
    .forName("helloworld")
    .directExecutor()
    .build()

suspend fun main(){
    coroutineScope {

        // Optional coroutineContext. Default is Dispatchers.Unconfined
        val stub = GreeterCoroutineGrpc
            .newStub(channel)
            .withCoroutineContext()

        performUnaryCall(stub)

        performBidiCall(stub)

        performClientStreamingCall(stub)

        performServerStreamingCall(stub)
    }

    server.shutdown()
}

suspend fun performUnaryCall(stub: GreeterCoroutineGrpc.GreeterCoroutineStub){

    val unaryResponse = stub.sayHello { name = "John" }

    println("Unary Response: ${unaryResponse.message}")
}

suspend fun performServerStreamingCall(stub: GreeterCoroutineGrpc.GreeterCoroutineStub){

    val responseChannel = stub.sayHelloServerStreaming { name = "John" }

    responseChannel.consumeEach {
        println("Server Streaming Response: ${it.message}")
    }
}

suspend fun CoroutineScope.performClientStreamingCall(stub: GreeterCoroutineGrpc.GreeterCoroutineStub){

    // Client Streaming RPC
    val (requestChannel, response) = stub.sayHelloClientStreaming()

    launchProducerJob(requestChannel){
        repeat(5){
            send { name = "person #$it" }
        }
    }

    println("Client Streaming Response: ${response.await().toString().trim()}")
}

suspend fun CoroutineScope.performBidiCall(stub: GreeterCoroutineGrpc.GreeterCoroutineStub){

    val (requestChannel, responseChannel) = stub.sayHelloStreaming()

    launchProducerJob(requestChannel){
        repeat(5){
            send { name = "person #$it" }
        }
    }

    responseChannel.consumeEach {
        println("Bidi Response: ${it.message}")
    }
}