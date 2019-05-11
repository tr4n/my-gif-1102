package com.example.mygif1102.model

import android.os.Parcel
import android.os.Parcelable

class GifMessage(type: Int, var id: String) : MessageData(type) {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeInt(type)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GifMessage> {
        override fun createFromParcel(parcel: Parcel): GifMessage {
            return GifMessage(parcel)
        }

        override fun newArray(size: Int): Array<GifMessage?> {
            return arrayOfNulls(size)
        }
    }
}