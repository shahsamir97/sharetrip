package net.sharetrip.visa.booking.model.coupon

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CouponResponse(
    var discount: String = "",
    var discountType: String = "",
    var withDiscount: String = "No",
    var gateway: List<String> = ArrayList()
) : Parcelable
