package com.kapouter.api.model

import android.os.Parcel
import android.os.Parcelable

data class Photo(val id: Int, val medium_url: String) : Parcelable {

    companion object {
        val CREATOR = object : Parcelable.Creator<Photo> {
            override fun createFromParcel(parcel: Parcel): Photo = Photo(parcel)

            override fun newArray(size: Int): Array<Photo?> = arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(medium_url)
    }

    override fun describeContents(): Int = 0
}