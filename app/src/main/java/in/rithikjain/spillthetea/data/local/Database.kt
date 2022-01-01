package `in`.rithikjain.spillthetea.data.local

import `in`.rithikjain.spillthetea.data.local.dao.PostDao
import `in`.rithikjain.spillthetea.data.local.entity.Post
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Post::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract val postDao: PostDao
}