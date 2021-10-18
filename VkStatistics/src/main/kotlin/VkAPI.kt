interface VkAPI {
    fun getStatistics(query: String, lastNumberOfHours: Int, countDownTime: Long) : List<Int>
}