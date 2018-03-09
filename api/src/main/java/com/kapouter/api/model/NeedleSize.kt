package com.kapouter.api.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by jcaruso on 09-03-18.
 */
data class NeedleSize(val name: String) : Parcelable {

    companion object {
        val CREATOR = object : Parcelable.Creator<NeedleSize> {
            override fun createFromParcel(parcel: Parcel): NeedleSize = NeedleSize(parcel)

            override fun newArray(size: Int): Array<NeedleSize?> = arrayOfNulls(size)
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