package com.example.testapp.apidata

import com.google.gson.annotations.SerializedName

//data class BusStation(
//    @SerializedName("results")
//    var StationInfo: BusStation
//)

data class BusStation(
    @SerializedName("stationId")
    val stationId: Int,
    @SerializedName("stationNm")
    val stationName: String,
    @SerializedName("gpsX")
    val positionX: Double,
    @SerializedName("gpsY")
    val positionY: Double
)