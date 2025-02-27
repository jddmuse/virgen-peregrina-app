package com.virgen.peregrina.data.model.replica

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import com.virgen.peregrina.data.model.pilgrimage.PilgrimageOnlyDatesParcelableModel
import com.virgen.peregrina.data.model.user.UserLiteParcelableModel
import java.time.LocalDate

class ReplicaParcelableModel(
    private val _id: Long,
    private val _code: String?,
    private val _birthdate: String?,
    private val _user: UserLiteParcelableModel?,
    private val _pilgrimages: List<PilgrimageOnlyDatesParcelableModel>?
): Parcelable {

    val id: Long get() = _id
    val code: String? get() = _code
    val birthdate: LocalDate? get() = LocalDate.parse(_birthdate)
    val user: UserLiteParcelableModel? get() = _user
    val pilgrimages: List<PilgrimageOnlyDatesParcelableModel>? get() = _pilgrimages

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            parcel.readParcelable(UserLiteParcelableModel::class.java.classLoader, UserLiteParcelableModel::class.java)
        else
            parcel.readParcelable(UserLiteParcelableModel::class.java.classLoader),
        parcel.createTypedArrayList(PilgrimageOnlyDatesParcelableModel.CREATOR)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_id)
        parcel.writeString(_code)
        parcel.writeString(_birthdate)
        parcel.writeParcelable(_user, flags)
        parcel.writeTypedList(_pilgrimages)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReplicaParcelableModel> {
        override fun createFromParcel(parcel: Parcel): ReplicaParcelableModel {
            return ReplicaParcelableModel(parcel)
        }

        override fun newArray(size: Int): Array<ReplicaParcelableModel?> {
            return arrayOfNulls(size)
        }
    }

}