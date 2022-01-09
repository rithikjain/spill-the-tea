package `in`.rithikjain.spillthetea.data.local.dao

import `in`.rithikjain.spillthetea.data.local.entity.Person
import `in`.rithikjain.spillthetea.data.local.entity.PostPersonCrossRef
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsertPerson(person: Person)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPersonPost(personCrossRef: PostPersonCrossRef)

    @Delete
    suspend fun deletePerson(person: Person)
}