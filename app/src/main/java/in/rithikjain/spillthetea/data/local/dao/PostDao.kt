package `in`.rithikjain.spillthetea.data.local.dao

import `in`.rithikjain.spillthetea.data.local.entity.Post
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post)

    @Delete
    suspend fun deletePost(post: Post)

    @Query("SELECT * FROM post WHERE id = :id")
    suspend fun getPostById(id: Int): Post?

    @Query("SELECT * FROM post ORDER BY timestamp DESC")
    fun getPosts(): Flow<List<Post>>
}