package blendex.idiomasblendex.com.Objects

import com.google.gson.annotations.SerializedName

data class

        Programa(@SerializedName("FechaVigenciaFinal")
                    val fechaVigenciaFinal: FechaVigenciaFinal,
                    @SerializedName("contrato")
                    val contrato: String = "",
                    @SerializedName("programa")
                    val programa: String = "",
                    @SerializedName("Horario")
                    val horario: String = "",
                    @SerializedName("FechaVigenciaInicial")
                    val fechaVigenciaInicial: FechaVigenciaInicial,
                    @SerializedName("Estado")
                    val estado: String = "")