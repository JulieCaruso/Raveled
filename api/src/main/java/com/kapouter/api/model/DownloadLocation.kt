package com.kapouter.api.model

import android.os.Parcel
import android.os.Parcelable

data class DownloadLocation(val type: String, val free: Boolean, val url: String) : Parcelable {

    companion object {
        val CREATOR = object : Parcelable.Creator<DownloadLocation> {
            override fun createFromParcel(parcel: Parcel): DownloadLocation = DownloadLocation(parcel)

            override fun newArray(size: Int): Array<DownloadLocation?> = arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt() > 0,
            parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(type)
        dest.writeInt(if (free) 1 else 0)
        dest.writeString(url)
    }

    override fun describeContents(): Int = 0
}