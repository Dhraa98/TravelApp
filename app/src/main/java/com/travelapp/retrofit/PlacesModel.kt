package com.travelapp.retrofit

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class PlacesModel : Serializable {
    @SerializedName("apiStatus")
    @Expose
    var apiStatus: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("last")
    @Expose
    var last = false

    @SerializedName("first")
    @Expose
    var first = false

    @SerializedName("totalPages")
    @Expose
    var totalPages = 0

    @SerializedName("size")
    @Expose
    var size = 0

    @SerializedName("index")
    @Expose
    var index = 0

    @SerializedName("totalRecords")
    @Expose
    var totalRecords = 0

    @SerializedName("rows")
    @Expose
    var rows: List<Row>? = null

    class Row : Serializable{
        @SerializedName("serviceType")
        @Expose
        var serviceType: String? = null

        @SerializedName("listingId")
        @Expose
        var listingId = 0

        @SerializedName("listingName")
        @Expose
        var listingName: String? = null

        @SerializedName("lat")
        @Expose
        var lat: String? = null

        @SerializedName("lon")
        @Expose
        var lon: String? = null

        @SerializedName("minPrice")
        @Expose
        var minPrice: String? = null

        @SerializedName("avgRating")
        @Expose
        var avgRating: String? = null

        @SerializedName("totalReviews")
        @Expose
        var totalReviews = 0

        @SerializedName("userFavourite")
        @Expose
        var userFavourite = false

        @SerializedName("distance")
        @Expose
        var distance: String? = null

        @SerializedName("images")
        @Expose
        var images: List<Image>? = null

        @SerializedName("address")
        @Expose
        var address: Address? = null

        @SerializedName("availability")
        @Expose
        var availability = false

        @SerializedName("externalListingId")
        @Expose
        var externalListingId = 0

        @SerializedName("activityStartDate")
        @Expose
        var activityStartDate: String? = null

        @SerializedName("duration")
        @Expose
        var duration: String? = null
    }
    class Image: Serializable {
        @SerializedName("imagePath")
        @Expose
        var imagePath: String? = null

        @SerializedName("thumbImagePath")
        @Expose
        var thumbImagePath: String? = null

        @SerializedName("isDefault")
        @Expose
        var isDefault: String? = null
    }
    class Address: Serializable {
        @SerializedName("city")
        @Expose
        var city: String? = null
    }
}