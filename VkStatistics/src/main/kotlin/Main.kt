suspend fun main(args: Array<String>) {
    val result = VkApiImpl(VkClientImpl()).getStatistics("хоккей", 6, System.currentTimeMillis())
    println(result)
}