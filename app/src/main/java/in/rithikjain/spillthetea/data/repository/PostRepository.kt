package `in`.rithikjain.spillthetea.data.repository

import `in`.rithikjain.spillthetea.data.local.entity.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun insertPost(post: Post)

    suspend fun deletePost(post: Post)

    suspend fun getPostById(id: Int): Post?

    fun getPosts(): Flow<List<Post>>
}