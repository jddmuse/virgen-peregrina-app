package com.virgen.peregrina.data.model.pilgrimage

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

class PilgrimageOnlyDatesParcelableModel(
    private val _startDate: String?,
    private val _endDate: String?
): Parcelable {

    val startDate: LocalDate? get() = LocalDate.parse(_startDate)
    val endDate: LocalDate? get() = LocalDate.parse(_endDate)

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_startDate)
        parcel.writeString(_endDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PilgrimageOnlyDatesParcelableModel> {
        override fun createFromParcel(parcel: Parcel): PilgrimageOnlyDatesParcelableModel {
            return PilgrimageOnlyDatesParcelableModel(parcel)
        }

        override fun newArray(size: Int): Array<PilgrimageOnlyDatesParcelableModel?> {
            return arrayOfNulls(size)
        }
    }

}