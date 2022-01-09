package `in`.rithikjain.spillthetea.data.repository

import `in`.rithikjain.spillthetea.data.local.entity.*
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun insertPost(post: Post): Long

    suspend fun deletePost(post: Post)

    suspend fun getPostById(id: Int): Post?

    fun getPosts(): Flow<List<Post>>

    suspend fun insertPerson(person: Person)

    suspend fun deletePerson(person: Person)

    suspend fun insertHashtag(hashtag: Hashtag)

    suspend fun deleteHashtag(hashtag: Hashtag)

    suspend fun insertHashtagPost(hashtagCrossRef: PostHashtagCrossRef)

    suspend fun insertPersonPost(personCrossRef: PostPersonCrossRef)

    fun getPostsByPerson(personName: String): Flow<List<PersonWithPosts>>

    fun getPostsByHashtag(hashtagName: String): Flow<List<HashtagWithPosts>>
}