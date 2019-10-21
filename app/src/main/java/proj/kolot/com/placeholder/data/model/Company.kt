package proj.kolot.com.placeholder.data.model

import com.google.gson.annotations.SerializedName

data class Company(
    @SerializedName("name")
    val companyName: String,
    @SerializedName("catchPhrase")
    val catchPhrase: String,
    @SerializedName("bs")
    val bs: String
)