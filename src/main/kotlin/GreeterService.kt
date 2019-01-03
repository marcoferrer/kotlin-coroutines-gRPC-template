import io.grpc.Status
import io.grpc.examples.helloworld.GreeterCoroutineGrpc
import io.grpc.examples.helloworld.HelloReply
import io.grpc.examples.helloworld.HelloRequest
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.coroutineScope

class GreeterService : GreeterCoroutineGrpc.GreeterImplBase() {

    private val validNameRegex = Regex("[^0-9]*")

    override suspend fun sayHello(request: HelloRequest): HelloReply  = coroutineScope {

        if (request.name.matches(validNameRegex)) {
            HelloReply.newBuilder()
                .setMessage("Hello there, ${request.name}!")
                .build()
        } else {
            throw Status.INVALID_ARGUMENT.asRuntimeException()
        }
    }

    override suspend fun sayHelloStreaming(
        requestChannel: ReceiveChannel<HelloRequest>,
        responseChannel: SendChannel<HelloReply>
    ) {
        coroutineScope {

            requestChannel.consumeEach { request ->

                responseChannel
                    .send { message = "Hello there, ${request.name}!" }
            }
        }
    }

    override suspend fun sayHelloClientStreaming(
        requestChannel: ReceiveChannel<HelloRequest>
    ): HelloReply = coroutineScope {

        HelloReply.newBuilder()
            .setMessage(requestChannel.toList().joinToString())
            .build()
    }

    override suspend fun sayHelloServerStreaming(request: HelloRequest, responseChannel: SendChannel<HelloReply>) {
        coroutineScope {
            for(char in request.name) {

                responseChannel.send {
                    message = "Hello $char!"
                }
            }
        }
    }
}