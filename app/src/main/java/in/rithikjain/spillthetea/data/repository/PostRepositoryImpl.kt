package `in`.rithikjain.spillthetea.data.repository

import `in`.rithikjain.spillthetea.data.local.dao.PostDao
import `in`.rithikjain.spillthetea.data.local.entity.Post
import kotlinx.coroutines.flow.Flow

class PostRepositoryImpl(
    private val dao: PostDao
) : PostRepository {

    override suspend fun insertPost(post: Post) {
        dao.insertPost(post)
    }

    override suspend fun deletePost(post: Post) {
        dao.deletePost(post)
    }

    override suspend fun getPostById(id: Int): Post? {
        return dao.getPostById(id)
    }

    override fun getPosts(): Flow<List<Post>> {
        return dao.getPosts()
    }
}