package `in`.rithikjain.spillthetea.data.local.dao

import `in`.rithikjain.spillthetea.data.local.entity.HashtagWithPosts
import `in`.rithikjain.spillthetea.data.local.entity.PersonWithPosts
import `in`.rithikjain.spillthetea.data.local.entity.Post
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post): Long

    @Delete
    suspend fun deletePost(post: Post)

    @Query("SELECT * FROM post WHERE id = :id")
    suspend fun getPostById(id: Int): Post?

    @Query("SELECT * FROM post ORDER BY timestamp DESC")
    fun getPosts(): Flow<List<Post>>

    @Transaction
    @Query("SELECT * FROM person where personName=:personName")
    fun getPostsByPerson(personName: String): Flow<List<PersonWithPosts>>

    @Transaction
    @Query("SELECT * FROM hashtag where hashtagName=:hashtagName")
    fun getPostsByHashtag(hashtagName: String): Flow<List<HashtagWithPosts>>
}