package `in`.rithikjain.spillthetea.data.repository

import `in`.rithikjain.spillthetea.data.local.dao.HashtagDao
import `in`.rithikjain.spillthetea.data.local.dao.PeopleDao
import `in`.rithikjain.spillthetea.data.local.dao.PostDao
import `in`.rithikjain.spillthetea.data.local.entity.*
import kotlinx.coroutines.flow.Flow

class AppRepositoryImpl(
    private val postDao: PostDao,
    private val peopleDao: PeopleDao,
    private val hashtagDao: HashtagDao
) : AppRepository {

    override suspend fun insertPost(post: Post): Long {
        return postDao.insertPost(post)
    }

    override suspend fun deletePost(post: Post) {
        postDao.deletePost(post)
    }

    override suspend fun getPostById(id: Int): Post? {
        return postDao.getPostById(id)
    }

    override fun getPosts(): Flow<List<Post>> {
        return postDao.getPosts()
    }

    override suspend fun insertPerson(person: Person) {
        peopleDao.upsertPerson(person)
    }

    override suspend fun deletePerson(person: Person) {
        peopleDao.deletePerson(person)
    }

    override suspend fun insertHashtag(hashtag: Hashtag) {
        hashtagDao.upsertHashtag(hashtag)
    }

    override suspend fun deleteHashtag(hashtag: Hashtag) {
        hashtagDao.deleteHashtag(hashtag)
    }

    override suspend fun insertHashtagPost(hashtagCrossRef: PostHashtagCrossRef) {
        hashtagDao.insertHashtagPost(hashtagCrossRef)
    }

    override suspend fun insertPersonPost(personCrossRef: PostPersonCrossRef) {
        peopleDao.insertPersonPost(personCrossRef)
    }

    override fun getPostsByPerson(personName: String): Flow<List<PersonWithPosts>> {
        return postDao.getPostsByPerson(personName)
    }

    override fun getPostsByHashtag(hashtagName: String): Flow<List<HashtagWithPosts>> {
        return postDao.getPostsByHashtag(hashtagName)
    }
}