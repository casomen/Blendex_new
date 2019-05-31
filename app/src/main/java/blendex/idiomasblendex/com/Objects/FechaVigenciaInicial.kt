package blendex.idiomasblendex.com.Objects

import com.google.gson.annotations.SerializedName

data class FechaVigenciaInicial(@SerializedName("date")
                                val date: String = "",
                                @SerializedName("timezone")
                                val timezone: String = "",
                                @SerializedName("timezone_type")
                                val timezoneType: Int = 0)