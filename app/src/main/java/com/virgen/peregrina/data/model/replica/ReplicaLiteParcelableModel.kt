package com.virgen.peregrina.data.model.replica

import android.os.Parcel
import android.os.Parcelable

class ReplicaLiteParcelableModel(
    private val _code: String?,
    private val _birthdate: String?
): Parcelable {

    val code: String? get() = _code
    val birthdate: String? get() = _birthdate

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(birthdate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReplicaLiteParcelableModel> {
        override fun createFromParcel(parcel: Parcel): ReplicaLiteParcelableModel {
            return ReplicaLiteParcelableModel(parcel)
        }

        override fun newArray(size: Int): Array<ReplicaLiteParcelableModel?> {
            return arrayOfNulls(size)
        }
    }
}