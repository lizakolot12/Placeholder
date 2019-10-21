package proj.kolot.com.placeholder.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val userName: String,

    @SerializedName("email")
    val email: String,

    @Embedded
    @SerializedName("address")
    val address: Address,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("website")
    val website: String,

    @Embedded
    @SerializedName("company")
    val company: Company
)