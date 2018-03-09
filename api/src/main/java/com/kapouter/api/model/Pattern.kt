package com.kapouter.api.model

import android.os.Parcel
import android.os.Parcelable

data class Pattern(val id: Int, val name: String, val first_photo: Photo, val pattern_author: PatternAuthor, val photos: List<Photo>) : Parcelable {

    companion object {
        val CREATOR = object : Parcelable.Creator<Pattern> {
            override fun createFromParcel(parcel: Parcel): Pattern = Pattern(parcel)

            override fun newArray(size: Int): Array<Pattern?> = arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readParcelable(Photo::class.java.classLoader),
            parcel.readParcelable(PatternAuthor::class.java.classLoader),
            arrayListOf<Photo>().apply {
                parcel.readList(this, Photo::class.java.classLoader)
            }
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeParcelable(first_photo, 0)
        dest.writeParcelable(pattern_author, 0)
        dest.writeList(photos)
    }

    override fun describeContents(): Int = 0
}