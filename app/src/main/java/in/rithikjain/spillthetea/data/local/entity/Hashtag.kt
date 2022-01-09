package `in`.rithikjain.spillthetea.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hashtag")
data class Hashtag(
    @PrimaryKey val hashtagName: String
)