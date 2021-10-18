import java.net.URL

open class VkClientImpl() : VkClient {
    private var requestPath: String? = null
    private val prefixUrl = "https://api.vk.com/method/"
    private val VERSION = "5.131"
    private val ACCESS_TOKEN: String = "4b0e49b34b0e49b34b0e49b3ef4b77d7d044b0e4b0e49b32a6e0b312294406926852a4d"

    constructor(requestPath: String) : this() {
        this.requestPath = requestPath
    }

    override fun sendRequest(request: String): String? {
        try {
            return URL(request).readText()
        } catch (e: Exception) {
            throw e
        }
    }

    override fun createRequest(method: String, params: MutableMap<String, String>) : String {
        return requestPath ?: (prefixUrl +
                method +
                "?" +
                params.entries.map { it.key + "=" + it.value + "&" }.joinToString("") +
                "access_token=$ACCESS_TOKEN&" +
                "v=$VERSION")
    }
}