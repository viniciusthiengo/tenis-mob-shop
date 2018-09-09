package thiengo.com.br.tnismobshop.domain

import android.os.Parcel
import android.os.Parcelable


class Brand(
        val imageResource: Int,
        val name: String) : Parcelable {

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(imageResource)
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Brand> = object : Parcelable.Creator<Brand> {
            override fun createFromParcel(source: Parcel): Brand = Brand(source)
            override fun newArray(size: Int): Array<Brand?> = arrayOfNulls(size)
        }
    }
}