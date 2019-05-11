package com.example.mygif1102.model

import android.os.Parcel
import android.os.Parcelable

class TitleMessage(type: Int, var title: String, var iconId: Int) : MessageData(type) {
    constructor(parcel: Parcel) : this(
        TODO("type"),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(title)
        parcel.writeInt(iconId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TitleMessage> {
        override fun createFromParcel(parcel: Parcel): TitleMessage {
            return TitleMessage(parcel)
        }

        override fun newArray(size: Int): Array<TitleMessage?> {
            return arrayOfNulls(size)
        }
    }

}