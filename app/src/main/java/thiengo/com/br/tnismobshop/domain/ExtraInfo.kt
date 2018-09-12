package thiengo.com.br.tnismobshop.domain

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import thiengo.com.br.tnismobshop.R

class ExtraInfo(
    val stock: Int,
    val recommended: String,
    val type: String,
    val composition: String,
    val fullDescription: String ) : Parcelable {

    /*
     * Obtém o dado de estoque formatado para a apresentação
     * a humano.
     * */
    fun getStockFormatted( context: Context ) : String =
        String.format(
            "%s (%d %s)",
            context.getString(R.string.value_stok_invetory_amount),
            stock,
            context.getString(R.string.value_stok_invetory_end)
        )

    constructor( source: Parcel ) : this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel( dest: Parcel, flags: Int ) = with( dest ) {
        writeInt( stock )
        writeString( recommended )
        writeString( type )
        writeString( composition )
        writeString( fullDescription )
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ExtraInfo> = object : Parcelable.Creator<ExtraInfo> {
            override fun createFromParcel( source: Parcel ): ExtraInfo = ExtraInfo( source )
            override fun newArray( size: Int ): Array<ExtraInfo?> = arrayOfNulls( size )
        }
    }
}