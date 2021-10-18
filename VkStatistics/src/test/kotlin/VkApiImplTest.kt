import org.json.JSONException
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.kotlin.*
import kotlin.test.assertNotNull

class VkApiImplTest {
    private val mockVkClientImpl = mock<VkClientImpl>()

    @Test
    fun getStatisticsTest() {
        assertNotNull(VkApiImpl(VkClientImpl()).getStatistics("спорт", 3, System.currentTimeMillis() - 1))
    }

    @Test
    fun vkClientMockTest() {
        val curTime = 1600000000L * 1000L
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
        whenever(mockVkClientImpl.createRequest(anyString(), anyMap())).thenReturn("request")
        whenever(mockVkClientImpl.sendRequest("request")).thenReturn(responseFromClient)
        val vkAPIImpl = VkApiImpl(mockVkClientImpl)
        assertEquals(vkAPIImpl.getStatistics("спорт", 3, curTime), listOf(1, 1, 1))
        verify(mockVkClientImpl).createRequest(anyString(), anyMap())
        verify(mockVkClientImpl).sendRequest(anyString())
    }

    @Test(expected = NullPointerException::class)
    fun vkClientIncorrectRequestMockTest() {
        val curTime = 1600000000L * 1000L
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
        whenever(mockVkClientImpl.createRequest(anyString(), anyMap())).thenReturn(null)
        whenever(mockVkClientImpl.sendRequest(anyString())).thenReturn(responseFromClient)
        val vkAPIImpl = VkApiImpl(mockVkClientImpl)
        assertEquals(vkAPIImpl.getStatistics("спорт", 3, curTime), listOf(1, 1, 1))
        verify(mockVkClientImpl).createRequest(anyString(), anyMap())
        verify(mockVkClientImpl).sendRequest(anyString())
    }

    @Test(expected = JSONException::class)
    fun vkClientWithoutDataMockTest() {
        val curTime = 1600000000L * 1000L
        val responseFromClient = """
            {
            "response": {
            "items": [{
            "id": 43,
            "owner_id": 225953081,
            "from_id": 225953081,
            },
            {
            "id": 44,
            "owner_id": 225953082,
            "from_id": 225953082,
            },
            {
            "id": 45,
            "owner_id": 225953083,
            "from_id": 225953083,
            }],
            "count": 1000,
            "total_count": 167184
            }
            }
        """.trimIndent()
        whenever(mockVkClientImpl.createRequest(anyString(), anyMap())).thenReturn("request")
        whenever(mockVkClientImpl.sendRequest("request")).thenReturn(responseFromClient)
        val vkAPIImpl = VkApiImpl(mockVkClientImpl)
        assertEquals(vkAPIImpl.getStatistics("спорт", 3, curTime), listOf(1, 1, 1))
        verify(mockVkClientImpl).createRequest(anyString(), anyMap())
        verify(mockVkClientImpl).sendRequest(anyString())
    }
}