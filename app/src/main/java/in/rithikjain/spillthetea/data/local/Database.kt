package `in`.rithikjain.spillthetea.data.local

import `in`.rithikjain.spillthetea.data.local.dao.HashtagDao
import `in`.rithikjain.spillthetea.data.local.dao.PeopleDao
import `in`.rithikjain.spillthetea.data.local.dao.PostDao
import `in`.rithikjain.spillthetea.data.local.entity.*
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        Post::class,
        Person::class, Hashtag::class,
        PostPersonCrossRef::class,
        PostHashtagCrossRef::class
    ],
    version = 3
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract val postDao: PostDao
    abstract val peopleDao: PeopleDao
    abstract val hashtagDao: HashtagDao
}