package com.virgen.peregrina.data.model.user

import android.os.Parcel
import android.os.Parcelable

class UserLiteParcelableModel(
    private val _id: Long,
    private val _name: String?,
    private val _lastName: String?,
    private val _email: String?,
    private val _address: String?,
    private val _city: String?,
    private val _country: String?,
    private val _cellphone: String?,
): Parcelable {

    val id: Long get() = _id
    val name: String? get() = _name
    val lastName: String? get() = _lastName
    val email: String? get() = _email
    val address: String? get() = _address
    val city: String? get() = _city
    val country: String? get() = _country
    val cellphone: String? get() = _cellphone

    val nameAndLastName: String get() = "$_name $_lastName"

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_id)
        parcel.writeString(_name)
        parcel.writeString(_lastName)
        parcel.writeString(_email)
        parcel.writeString(_address)
        parcel.writeString(_city)
        parcel.writeString(_country)
        parcel.writeString(_cellphone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserLiteParcelableModel> {
        override fun createFromParcel(parcel: Parcel): UserLiteParcelableModel {
            return UserLiteParcelableModel(parcel)
        }

        override fun newArray(size: Int): Array<UserLiteParcelableModel?> {
            return arrayOfNulls(size)
        }
    }

}