package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(election: Election)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(election: List<Election>)

    @Query("SELECT * FROM election_table ORDER BY electionDay ASC")
    fun getAllElections(): LiveData<List<Election>>

    @Query("SELECT * FROM election_table WHERE id = :electionId")
    suspend fun getElectionById(electionId: Int): Election?

    @Delete
    suspend fun delete(election: Election)

    @Query("DELETE FROM election_table")
    suspend fun deleteAll()

    @Query("UPDATE election_table SET followed = 1 WHERE id = :id")
    suspend fun markAsFollowed(id: Int)

    @Query("UPDATE election_table SET followed = 0 WHERE id = :id")
    suspend fun markAsUnFollowed(id: Int)

    @Query("SELECT * FROM election_table WHERE followed = 1")
    fun getFollowedElections(): LiveData<List<Election>>
}