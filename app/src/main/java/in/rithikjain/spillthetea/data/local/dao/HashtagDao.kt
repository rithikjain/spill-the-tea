package `in`.rithikjain.spillthetea.data.local.dao

import `in`.rithikjain.spillthetea.data.local.entity.Hashtag
import `in`.rithikjain.spillthetea.data.local.entity.PostHashtagCrossRef
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface HashtagDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsertHashtag(hashtag: Hashtag)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHashtagPost(hashtagCrossRef: PostHashtagCrossRef)

    @Delete
    suspend fun deleteHashtag(hashtag: Hashtag)
}