package com.example.pact24_var2_marinina

import android.os.Parcel
import android.os.Parcelable

data class DateItem(
    val type: String,
    val money: String,
    val zam: String,
    val date: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(money)
        parcel.writeString(zam)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DateItem> {
        override fun createFromParcel(parcel: Parcel): DateItem {
            return DateItem(parcel)
        }

        override fun newArray(size: Int): Array<DateItem?> {
            return arrayOfNulls(size)
        }
    }
}
