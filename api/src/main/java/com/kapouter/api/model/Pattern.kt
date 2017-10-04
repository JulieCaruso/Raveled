package com.kapouter.api.model

import android.os.Parcel
import android.os.Parcelable

data class Pattern(val name: String) : Parcelable {

    companion object {
        val CREATOR = object : Parcelable.Creator<Pattern> {
            override fun createFromParcel(parcel: Parcel): Pattern = Pattern(parcel)

            override fun newArray(size: Int): Array<Pattern?> = arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
    }

    override fun describeContents(): Int = 0
}