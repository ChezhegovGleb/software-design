import org.json.JSONObject

open class VkApiImpl(private val vkClientImpl: VkClientImpl) : VkAPI {

     override fun getStatistics(query: String, lastNumberOfHours: Int, countDownTime: Long) : List<Int> {
          val endUnixTimeInSeconds = countDownTime / 1000L
          val startUnixTimeInSeconds = endUnixTimeInSeconds - lastNumberOfHours * 3600L
          val method = "newsfeed.search"
          val params = hashMapOf("q" to query,
                                 "count" to "3",
                                 "start_time" to startUnixTimeInSeconds.toString(),
                                 "end_time" to endUnixTimeInSeconds.toString())
          val request = vkClientImpl.createRequest(method, params)
          val response = JSONObject(vkClientImpl.sendRequest(request))
          val countQueriesList = MutableList(lastNumberOfHours){0}
          val newsfeed = response.getJSONObject("response").getJSONArray("items")
          for (news in newsfeed) {
               countQueriesList[((JSONObject(news.toString()).get("date").toString().toLong() - startUnixTimeInSeconds) / 3600L).toInt()]++
          }
          return countQueriesList.reversed()
     }
}