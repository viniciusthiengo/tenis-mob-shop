package thiengo.com.br.tnismobshop.domain

import android.os.Parcel
import android.os.Parcelable
import java.util.*


class Sneaker(
        val imageResource: Int,
        val album: IntArray,
        val model: String,
        val brand: Brand,
        val isForMale: Boolean,
        val isForFemale: Boolean,
        val rating: Rating,
        val price: Double,
        val extraInfo: ExtraInfo) : Parcelable {
    /*
     * Método responsável por retornar o valor do tênis
     * em um formato humano, brasileiro.
     * */
    fun getPriceAsString(): String {
        return String.format(Locale.FRANCE, "R$ %.2f", price)
    }

    /*
     * Método responsável por retornar o valor do tênis,
     * em parcelas de 10x, em um formato humano, brasileiro.
     * */
    fun getPriceParcelsAsString(): String {
        val value = price / 10
        return String.format(Locale.FRANCE, "10x R$ %.2f", value)
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.createIntArray(),
            source.readString(),
            source.readParcelable<Brand>(Brand::class.java.classLoader),
            1 == source.readInt(),
            1 == source.readInt(),
            source.readParcelable<Rating>(Rating::class.java.classLoader),
            source.readDouble(),
            source.readParcelable<ExtraInfo>(ExtraInfo::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(imageResource)
        writeIntArray(album)
        writeString(model)
        writeParcelable(brand, 0)
        writeInt((if (isForMale) 1 else 0))
        writeInt((if (isForFemale) 1 else 0))
        writeParcelable(rating, 0)
        writeDouble(price)
        writeParcelable(extraInfo, 0)
    }

    companion object {
        val KEY = "SNEAKER_KEY"

        @JvmField
        val CREATOR: Parcelable.Creator<Sneaker> = object : Parcelable.Creator<Sneaker> {
            override fun createFromParcel(source: Parcel): Sneaker = Sneaker(source)
            override fun newArray(size: Int): Array<Sneaker?> = arrayOfNulls(size)
        }
    }
}