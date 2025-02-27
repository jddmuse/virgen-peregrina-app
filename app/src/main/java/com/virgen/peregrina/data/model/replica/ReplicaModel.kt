package com.virgen.peregrina.data.model.replica

import com.virgen.peregrina.data.model.pilgrimage.PilgrimageOnlyDatesModel
import com.virgen.peregrina.data.model.user.UserLiteModel
import com.virgen.peregrina.data.model.user.UserModel
import java.time.LocalDate

data class ReplicaModel(
    val id: Long,
    val photoUrl: String?,
    val code: String,
    val birthdate: LocalDate,
    val user: UserLiteModel,
    val pilgrimages: List<PilgrimageOnlyDatesModel>?
)