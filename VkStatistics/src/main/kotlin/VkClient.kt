interface VkClient {
    fun sendRequest(request: String): String?
    fun createRequest(method: String, params: MutableMap<String, String>) : String
}