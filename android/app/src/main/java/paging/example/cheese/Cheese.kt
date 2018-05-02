package paging.example.cheese

import com.squareup.moshi.Json

data class Cheese(
    @Json(name = "id") val id: Int,
    @Json(name = "like") val like: Int,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String) {

    data class Array(
        @Json(name = "cheeses") val cheeses: List<Cheese>,
        @Json(name = "total") val total: Int)
}