import io.grpc.Status
import io.grpc.examples.helloworld.GreeterCoroutineGrpc
import io.grpc.examples.helloworld.HelloReply
import io.grpc.examples.helloworld.HelloRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asContextElement
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.toList
import kotlin.coroutines.CoroutineContext

class GreeterService : GreeterCoroutineGrpc.GreeterImplBase() {

    val myThreadLocal = ThreadLocal.withInitial { "value" }.asContextElement()

    override val initialContext: CoroutineContext
        get() = Dispatchers.Default + myThreadLocal


    private val validNameRegex = Regex("[^0-9]*")

    override suspend fun sayHello(request: HelloRequest): HelloReply  {

        if (request.name.matches(validNameRegex)) {
            return HelloReply.newBuilder()
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
        requestChannel.consumeEach { request ->

            responseChannel
                .send { message = "Hello there, ${request.name}!" }
        }
    }

    override suspend fun sayHelloClientStreaming(
        requestChannel: ReceiveChannel<HelloRequest>
    ): HelloReply {

        return HelloReply.newBuilder()
            .setMessage(requestChannel.toList().joinToString())
            .build()
    }

    override suspend fun sayHelloServerStreaming(request: HelloRequest, responseChannel: SendChannel<HelloReply>) {

        for(char in request.name) {
            responseChannel.send {
                message = "Hello $char!"
            }
        }
    }
}