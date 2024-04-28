package com.ondinnonk.newsapp.repository.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface BaseDao<E> {

    @Transaction
    @Insert(onConflict = REPLACE)
    suspend fun insert(entity: E): Long

    @Transaction
    @Insert
    suspend fun insert(vararg entities: E): List<Long>

    @Transaction
    @Insert
    suspend fun insertAll(entities: List<E>): List<Long>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(entity: E): Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(entities: List<E>): List<Long>

    /**
     * @return # of deleted records
     */
    @Delete
    suspend fun delete(vararg entities: E): Int

    /**
     * @return # of deleted records
     */
    @Delete
    suspend fun delete(entities: List<E>): Int

    /**
     * @return number of updated records
     */
    @Transaction
    @Update
    suspend fun update(vararg entities: E): Int
}