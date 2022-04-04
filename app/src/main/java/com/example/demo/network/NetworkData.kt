package com.example.demo.network

import com.squareup.moshi.Json
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val IMAGE_BASE_URL = "https://wwwimage-us.pplusstatic.com/base/"

data class Listing(
    val id: Int,
    val title: String,
    val description: String,
    val filePathThumb: String
) {
    fun resolveFilePathThumbUrl(): String {
        return IMAGE_BASE_URL + filePathThumb
    }
}

data class Channel(
    val id: Int,
    val slug: String,
    val channelName: String,
    val filePathLogo: String,
    val filePathLogoSelected: String,
    val filePathSmallLogo: String,
    val filePathSmallLogoSelected: String,

    @Transient
    val listings: List<Listing>
) {
    fun resolveFilePathSmallLogoUrl(): String {
        return IMAGE_BASE_URL + filePathSmallLogo
    }

    fun resolveFilePathLogoSelectedUrl(): String {
        return IMAGE_BASE_URL + filePathLogoSelected
    }
}

data class ListingsResponse(
    val listing: List<Listing>
)

data class ChannelsResponse(
    val channels: List<Channel>
)

interface ParamountService {
    @GET("channels.json?start=0&_clientRegion=US&dma=&showListing=true&stationId=16533&locale=en-us&at=ABCZBVMUfTDEb90Hd2Zr5DHgfd%2FDHM24XsLmjLgCyVdbU7RVhjQpjReMozt3Qbs2q6c%3D")
    suspend fun getChannels(): ChannelsResponse

    @GET("channels/{channelSlug}/listings.json?platformType=apps&start=0&rows=10&locale=en-us&at=ABDyUft4Qomn7voHvc3fnl3rM5HGXzi%2BY8fFbIgyOTM7e4Mkkorag3G2imsB9aEUAkI%3D")
    suspend fun getListings(@Path("channelSlug") channelSlug: String): ListingsResponse
}

object NetworkData {
    private val retrofit = Retrofit
        .Builder()
        .baseUrl("https://www.paramountplus.com/apps-api/v3.0/androidphone/live/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    var service: ParamountService = retrofit.create(ParamountService::class.java)
}