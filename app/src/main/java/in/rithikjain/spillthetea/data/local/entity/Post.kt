package `in`.rithikjain.spillthetea.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "post")
data class Post(
    val content: String,
    val timestamp: Date,
    @PrimaryKey val id: Int? = null
)