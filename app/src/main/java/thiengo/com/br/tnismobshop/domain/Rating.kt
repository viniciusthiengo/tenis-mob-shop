package thiengo.com.br.tnismobshop.domain

import android.os.Parcel
import android.os.Parcelable


class Rating(
    val amount: Int,
    val stars: Int ) : Parcelable {

    constructor( source: Parcel ) : this(
        source.readInt(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel( dest: Parcel, flags: Int ) = with( dest ) {
        writeInt( amount )
        writeInt( stars )
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Rating> = object : Parcelable.Creator<Rating> {
            override fun createFromParcel( source: Parcel ): Rating = Rating( source )
            override fun newArray( size: Int ): Array<Rating?> = arrayOfNulls( size )
        }
    }
}