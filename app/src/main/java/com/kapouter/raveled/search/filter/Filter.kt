package com.kapouter.raveled.search.filter

import android.os.Parcel
import android.os.Parcelable
import com.kapouter.raveled.R

data class Filter(var sort: FilterSort = FilterSort.BEST, var craft: List<FilterCraft> = ArrayList()) : Parcelable {
    constructor(parcel: Parcel) : this(
            sort = parcel.readParcelable(FilterSort::class.java.classLoader) as FilterSort,
            craft = parcel.readParcelableArray(FilterCraft::class.java.classLoader).toList() as List<FilterCraft>)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(sort, flags)
        parcel.writeParcelableArray(craft.toTypedArray(), flags)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Filter> {
        override fun createFromParcel(parcel: Parcel): Filter = Filter(parcel)

        override fun newArray(size: Int): Array<Filter?> = arrayOfNulls(size)
    }
}

enum class FilterSort(val value: String, val title: Int) : Parcelable {
    BEST("best", R.string.sort_best),
    HOT("recently-popular", R.string.sort_hot),
    NAME("name", R.string.sort_name),
    POPULAR("popularity", R.string.sort_popularity),
    PROJECTS("projects", R.string.sort_projects),
    FAVORITES("favorites", R.string.sort_favorites),
    QUEUES("queues", R.string.sort_queues),
    PUBLICATION("date", R.string.sort_publication),
    NEW("created", R.string.sort_new),
    RATING("rating", R.string.sort_rating),
    DIFFICULTY("difficulty", R.string.sort_difficulty),
    YARN("yarn", R.string.sort_yarn);

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ordinal)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<FilterSort> {
        override fun createFromParcel(parcel: Parcel): FilterSort =
                FilterSort.values()[parcel.readInt()]

        override fun newArray(size: Int): Array<FilterSort?> = arrayOfNulls(size)
    }
}

enum class FilterCraft(val value: String) : Parcelable {
    CROCHET("crochet"),
    KNITTING("knitting");

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ordinal)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<FilterCraft> {
        override fun createFromParcel(parcel: Parcel): FilterCraft =
                FilterCraft.values()[parcel.readInt()]

        override fun newArray(size: Int): Array<FilterCraft?> = arrayOfNulls(size)
    }
}