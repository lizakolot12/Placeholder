package proj.kolot.com.placeholder.data.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("street")
    val stree: String,
    @SerializedName("suite")

    val suite: String,
    @SerializedName("city")

    val city: String,
    @SerializedName("zipcode")

    val zipcode: String,

    @Embedded
    @SerializedName("geo")
    val geo: Geo
)