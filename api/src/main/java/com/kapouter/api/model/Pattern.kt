package com.kapouter.api.model

import android.os.Parcel
import android.os.Parcelable

data class Pattern(val id: Int, val name: String, val first_photo: Photo) : Parcelable {

    companion object {
        val CREATOR = object : Parcelable.Creator<Pattern> {
            override fun createFromParcel(parcel: Parcel): Pattern = Pattern(parcel)

            override fun newArray(size: Int): Array<Pattern?> = arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readParcelable(Photo::class.java.classLoader)
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeParcelable(first_photo, 0)
    }

    override fun describeContents(): Int = 0
}