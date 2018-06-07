package com.kapouter.api.model

import android.os.Parcel
import android.os.Parcelable

data class PersonalAttributes(var favorited: Boolean, val in_library: Boolean, var queued: Boolean) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (favorited) 1 else 0)
        parcel.writeByte(if (in_library) 1 else 0)
        parcel.writeByte(if (queued) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<PersonalAttributes> {
        override fun createFromParcel(parcel: Parcel): PersonalAttributes =
                PersonalAttributes(parcel)

        override fun newArray(size: Int): Array<PersonalAttributes?> = arrayOfNulls(size)
    }
}