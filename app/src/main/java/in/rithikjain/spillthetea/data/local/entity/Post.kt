package `in`.rithikjain.spillthetea.data.local.entity

import androidx.room.*
import java.io.Serializable
import java.util.*


@Entity(tableName = "post")
data class Post(
    val content: String,
    val timestamp: Date,
    @PrimaryKey val id: Int? = null,
) : Serializable

data class PersonWithPosts(
    @Embedded val person: Person,
    @Relation(
        parentColumn = "personName",
        entityColumn = "id",
        associateBy = Junction(PostPersonCrossRef::class)
    )
    val posts: List<Post>
)

data class HashtagWithPosts(
    @Embedded val hashtag: Hashtag,
    @Relation(
        parentColumn = "hashtagName",
        entityColumn = "id",
        associateBy = Junction(PostHashtagCrossRef::class)
    )
    val posts: List<Post>
)