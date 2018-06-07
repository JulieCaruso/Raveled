package com.kapouter.api.model

import android.os.Parcel
import android.os.Parcelable

data class Pattern(val id: Int,
                   val name: String,
                   val first_photo: Photo?,
                   val pattern_author: PatternAuthor,
                   val photos: List<Photo>,
                   val free: Boolean,
                   val currency: String,
                   val price: String,
                   val downloadable: Boolean,
                   val download_location: DownloadLocation,
                   val favorites_count: Int,
                   val projects_count: Int,
                   val queued_projects_count: Int,
                   val rating_average: Float,
                   val difficulty_average: Float,
                   val notes_html: String,
                   val yarn_weight: YarnWeight,
                   val gauge_description: String,
                   val yardage_description: String,
                   val pattern_needle_sizes: List<NeedleSize>)
    : Parcelable {

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
            },
            parcel.readInt() > 0,
            parcel.readString(),
            parcel.readString(),
            parcel.readInt() > 0,
            parcel.readParcelable(DownloadLocation::class.java.classLoader),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readString(),
            parcel.readParcelable(YarnWeight::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            arrayListOf<NeedleSize>().apply {
                parcel.readList(this, NeedleSize::class.java.classLoader)
            }
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeParcelable(first_photo, 0)
        dest.writeParcelable(pattern_author, 0)
        dest.writeList(photos)
        dest.writeInt(if (free) 1 else 0)
        dest.writeString(currency)
        dest.writeString(price)
        dest.writeInt(if (downloadable) 1 else 0)
        dest.writeParcelable(download_location, 0)
        dest.writeInt(favorites_count)
        dest.writeInt(projects_count)
        dest.writeInt(queued_projects_count)
        dest.writeFloat(rating_average)
        dest.writeFloat(difficulty_average)
        dest.writeString(notes_html)
        dest.writeParcelable(yarn_weight, 0)
        dest.writeString(gauge_description)
        dest.writeString(yardage_description)
        dest.writeList(pattern_needle_sizes)
    }

    override fun describeContents(): Int = 0
}