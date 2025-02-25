package com.virgen.peregrina.ui.pilgrimage.util

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

class PilgrimageParcelableModel(
    val _replicaId: Long,
    val _userId: Long,
    val _startDate: String?,
    val _endDate: String?,
    val _intention: String?
) : Parcelable {

    val replicaId: Long get() = _replicaId
    val userId: Long get() = _userId
    val startDate: LocalDate? get() = _startDate?.let { LocalDate.parse(it) }
    val endDate: LocalDate? get() = _endDate?.let { LocalDate.parse(it) }
    val intention: String? get() = _intention


    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_replicaId)
        parcel.writeLong(_userId)
        parcel.writeString(_startDate)
        parcel.writeString(_endDate)
        parcel.writeString(_intention)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PilgrimageParcelableModel> {
        override fun createFromParcel(parcel: Parcel): PilgrimageParcelableModel {
            return PilgrimageParcelableModel(parcel)
        }

        override fun newArray(size: Int): Array<PilgrimageParcelableModel?> {
            return arrayOfNulls(size)
        }
    }
}