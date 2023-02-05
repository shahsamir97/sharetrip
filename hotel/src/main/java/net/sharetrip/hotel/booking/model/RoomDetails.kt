package net.sharetrip.hotel.booking.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoomDetails(
    @field:Json(name = "name")
    var name: String,
    @field:Json(name = "amenities")
    var roomAmenities: List<RoomAmenities>
) : Parcelable
