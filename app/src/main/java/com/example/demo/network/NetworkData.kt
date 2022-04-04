package com.example.demo.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val IMAGE_BASE_URL = "https://wwwimage-us.pplusstatic.com/base/"

data class CurrentListing(
    val filePathThumb: String
) {
    fun resolveFilePathThumbUrl(): String {
        return IMAGE_BASE_URL + filePathThumb
    }
}

data class Channel(
    val id: Int,
    val channelName: String,
    val filePathLogo: String,
    val filePathLogoSelected: String,
    val filePathSmallLogo: String,
    val filePathSmallLogoSelected: String,
    val currentListing: List<CurrentListing>?
) {
    fun listingUrls() = listOf(
        IMAGE_BASE_URL + "files/listings/api-managed/assets/p21498120_i_h8_aa.jpg",
        IMAGE_BASE_URL + "files/listings/api-managed/assets/p19620924_b_h8_aa.jpg",
        IMAGE_BASE_URL + "files/listings/api-managed/assets/p19679156_b_h8_aa.jpg",
        IMAGE_BASE_URL + "files/listings/api-managed/assets/p19679153_b_h8_aa.jpg",
        IMAGE_BASE_URL + "files/listings/api-managed/assets/p21498120_i_h8_aa.jpg",
        IMAGE_BASE_URL + "files/listings/api-managed/assets/p21498120_i_h8_aa.jpg",
        IMAGE_BASE_URL + "files/listings/api-managed/assets/p19620924_b_h8_aa.jpg",
        IMAGE_BASE_URL + "files/listings/api-managed/assets/p19679156_b_h8_aa.jpg",
        IMAGE_BASE_URL + "files/listings/api-managed/assets/p19679153_b_h8_aa.jpg",
        IMAGE_BASE_URL + "files/listings/api-managed/assets/p21498120_i_h8_aa.jpg",
    )


    fun resolveFilePathSmallLogoUrl(): String {
        return IMAGE_BASE_URL + filePathSmallLogo
    }
}

data class ChannelsResponse(
    val channels: List<Channel>
)

interface ParamountService {
    @GET("channels.json?start=0&_clientRegion=US&dma=&showListing=true&stationId=16533&locale=en-us&at=ABCZBVMUfTDEb90Hd2Zr5DHgfd%2FDHM24XsLmjLgCyVdbU7RVhjQpjReMozt3Qbs2q6c%3D")
    suspend fun getChannels(): ChannelsResponse
}

object NetworkData {
    private val retrofit = Retrofit
        .Builder()
        .baseUrl("https://www.paramountplus.com/apps-api/v3.0/androidphone/live/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    var service: ParamountService = retrofit.create(ParamountService::class.java)
}