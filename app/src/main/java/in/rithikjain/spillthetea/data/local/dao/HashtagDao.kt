package `in`.rithikjain.spillthetea.data.local.dao

import `in`.rithikjain.spillthetea.data.local.entity.Hashtag
import `in`.rithikjain.spillthetea.data.local.entity.PostHashtagCrossRef
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HashtagDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsertHashtag(hashtag: Hashtag)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHashtagPost(hashtagCrossRef: PostHashtagCrossRef)

    @Query("SELECT * FROM hashtag")
    fun getAllHashtags(): Flow<List<Hashtag>>

    @Delete
    suspend fun deleteHashtag(hashtag: Hashtag)
}