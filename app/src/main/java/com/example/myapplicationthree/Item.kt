package com.example.myapplicationthree

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item (
    @SerialName("id")
    var id: Int?,
    @SerialName("title")
    var title: String?,
    @SerialName("description")
    var description: String?,
    @SerialName("priority")
    var priority: Int?,
    @SerialName("type")
    var type: String?,
    @SerialName("count")
    var count: Int?,
    @SerialName("period")
    var period: Int?
)
