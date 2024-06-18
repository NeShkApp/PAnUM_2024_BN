package org.bohdan.panum_lab5

import android.os.Parcel
import android.os.Parcelable

data class FoodItem(
    val name: String,
    val price: Double,
    val imageUrl: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeDouble(price)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FoodItem> {
        override fun createFromParcel(parcel: Parcel): FoodItem {
            return FoodItem(parcel)
        }

        override fun newArray(size: Int): Array<FoodItem?> {
            return arrayOfNulls(size)
        }
    }
}

