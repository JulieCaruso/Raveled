package com.kapouter.api.model

import android.os.Parcel
import android.os.Parcelable

data class YarnWeight(val id: Int, val name: String) : Parcelable {

    companion object {
        val CREATOR = object : Parcelable.Creator<YarnWeight> {
            override fun createFromParcel(parcel: Parcel): YarnWeight = YarnWeight(parcel)

            override fun newArray(size: Int): Array<YarnWeight?> = arrayOfNulls(size)
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