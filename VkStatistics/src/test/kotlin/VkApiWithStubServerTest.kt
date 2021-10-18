import com.xebialabs.restito.builder.stub.StubHttp.whenHttp
import com.xebialabs.restito.semantics.Action
import com.xebialabs.restito.semantics.Action.stringContent
import com.xebialabs.restito.semantics.Condition.method
import com.xebialabs.restito.semantics.Condition.startsWithUri
import com.xebialabs.restito.server.StubServer
import kotlinx.coroutines.runBlocking
import org.glassfish.grizzly.http.Method
import org.glassfish.grizzly.http.util.HttpStatus
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import java.io.FileNotFoundException
import java.util.function.Consumer


class VkApiWithStubServerTest {
    private val PORT = 30000

    @Test
    fun getStatisticsTest() {
        val curTime = 1634483499124
        val responseFromClient = """
            {
            "response": {
            "items": [{
            "id": 43,
            "date": ${curTime / 1000L - 0 * 3600L - 1},
            "owner_id": 225953081,
            "from_id": 225953081,
            },
            {
            "id": 44,
            "date": ${curTime / 1000L - 1 * 3600L - 1},
            "owner_id": 225953082,
            "from_id": 225953082,
            },
            {
            "id": 45,
            "date": ${curTime / 1000L - 2 * 3600L - 1},
            "owner_id": 225953083,
            "from_id": 225953083,
            }],
            "count": 1000,
            "total_count": 167184
            }
            }
        """.trimIndent()
        withStubServer(PORT) { s ->
            whenHttp(s)
                .match(
                    method(Method.GET),
                    startsWithUri("/request")
                )
                .then(stringContent(responseFromClient))
            val pathRequest = "http://localhost:$PORT/request"
            Assert.assertEquals(
                listOf(1, 1, 1),
                VkApiImpl(VkClientImpl(pathRequest)).getStatistics("хоккей", 3, curTime)
            )
        }
    }

    @Test(expected = FileNotFoundException::class)
    fun getStatisticsNotFoundErrorTest() {
        val curTime = 1634483499124
        withStubServer(PORT) { s: StubServer? ->
            whenHttp(s)
                .match(
                    method(Method.GET),
                    startsWithUri("/request")
                )
                .then(Action.status(HttpStatus.NOT_FOUND_404))
            val pathRequest = "http://localhost:$PORT/request"
            VkApiImpl(VkClientImpl(pathRequest)).getStatistics("хоккей", 3, curTime)
        }
    }


    private fun withStubServer(port: Int, callback: Consumer<StubServer?>) {
        var stubServer: StubServer? = null
        try {
            stubServer = StubServer(port).run()
            callback.accept(stubServer)
        } finally {
            stubServer?.stop()
        }
    }

}