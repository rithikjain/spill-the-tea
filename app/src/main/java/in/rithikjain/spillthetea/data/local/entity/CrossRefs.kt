package `in`.rithikjain.spillthetea.data.local.entity

import androidx.room.Entity

@Entity(primaryKeys = ["id", "personName"])
data class PostPersonCrossRef(
    val id: Long,
    val personName: String
)

@Entity(primaryKeys = ["id", "hashtagName"])
data class PostHashtagCrossRef(
    val id: Long,
    val hashtagName: String
)

