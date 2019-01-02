import io.grpc.Status
import io.grpc.examples.helloworld.GreeterCoroutineGrpc
import io.grpc.examples.helloworld.HelloReply
import io.grpc.examples.helloworld.HelloRequest
import io.grpc.examples.helloworld.HelloWorldProtoBuilders
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.coroutineScope

class GreeterService : GreeterCoroutineGrpc.GreeterImplBase() {

    private val validNameRegex = Regex("[^0-9]*")

    override suspend fun sayHello(request: HelloRequest): HelloReply  = coroutineScope {

        if (request.name.matches(validNameRegex)) {
            HelloWorldProtoBuilders.HelloReply{
                message = "Hello there, ${request.name}!"
            }
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

        HelloWorldProtoBuilders.HelloReply{
            message = requestChannel.toList().joinToString()
        }
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