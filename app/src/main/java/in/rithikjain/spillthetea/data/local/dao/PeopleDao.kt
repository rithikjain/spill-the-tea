package `in`.rithikjain.spillthetea.data.local.dao

import `in`.rithikjain.spillthetea.data.local.entity.Person
import `in`.rithikjain.spillthetea.data.local.entity.PostPersonCrossRef
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsertPerson(person: Person)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPersonPost(personCrossRef: PostPersonCrossRef)

    @Query("SELECT * FROM person")
    fun getAllPeople(): Flow<List<Person>>

    @Delete
    suspend fun deletePerson(person: Person)
}