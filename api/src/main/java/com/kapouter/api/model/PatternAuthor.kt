package com.kapouter.api.model

import android.os.Parcel
import android.os.Parcelable

data class PatternAuthor(val id: Int, val name: String) : Parcelable {

    companion object {
        val CREATOR = object : Parcelable.Creator<PatternAuthor> {
            override fun createFromParcel(parcel: Parcel): PatternAuthor = PatternAuthor(parcel)

            override fun newArray(size: Int): Array<PatternAuthor?> = arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
    }

    override fun describeContents(): Int = 0
}