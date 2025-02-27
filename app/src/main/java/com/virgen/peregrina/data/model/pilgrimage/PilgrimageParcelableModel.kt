package com.virgen.peregrina.data.model.pilgrimage

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import com.virgen.peregrina.data.model.replica.ReplicaLiteParcelableModel
import com.virgen.peregrina.data.model.user.UserLiteParcelableModel
import java.time.LocalDate

class PilgrimageParcelableModel(
    private val _replica: ReplicaLiteParcelableModel?,
    private val _user: UserLiteParcelableModel?,
    private val _startDate: String?,
    private val _endDate: String?,
    private val _intention: String?
) : Parcelable {

    val replica: ReplicaLiteParcelableModel? get() = _replica
    val user: UserLiteParcelableModel? get() = _user
    val startDate: LocalDate? get() = _startDate?.let { LocalDate.parse(it) }
    val endDate: LocalDate? get() = _endDate?.let { LocalDate.parse(it) }
    val intention: String? get() = _intention


    constructor(parcel: Parcel) : this(
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            parcel.readParcelable(ReplicaLiteParcelableModel::class.java.classLoader, ReplicaLiteParcelableModel::class.java)
        else
            parcel.readParcelable(ReplicaLiteParcelableModel::class.java.classLoader),
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            parcel.readParcelable(UserLiteParcelableModel::class.java.classLoader, UserLiteParcelableModel::class.java)
        else
            parcel.readParcelable(UserLiteParcelableModel::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    constructor(): this(null, null, null, null, null)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(_replica, flags)
        parcel.writeParcelable(_user, flags)
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