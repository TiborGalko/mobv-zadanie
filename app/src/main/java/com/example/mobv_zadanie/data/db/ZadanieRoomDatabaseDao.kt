package com.example.mobv_zadanie.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mobv_zadanie.data.db.model.ContactItem
import com.example.mobv_zadanie.data.db.model.MessageItem
import com.example.mobv_zadanie.data.db.model.PostItem
import com.example.mobv_zadanie.data.db.model.WifiRoomItem

@Dao
interface ZadanieRoomDatabaseDao {
    // WifiRooms
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWifiRoom(wifiRoomItem: WifiRoomItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWifiRooms(wifiRoomItems: List<WifiRoomItem>)

    @Update
    suspend fun updateWifiRoom(wifiRoomItem: WifiRoomItem)

    @Delete
    suspend fun deleteWifiRoom(wifiRoomItem: WifiRoomItem)

    @Query("SELECT * FROM wifirooms")
    fun getWifiRooms(): LiveData<List<WifiRoomItem>>

    @Query("SELECT * FROM wifirooms ORDER BY ssid ASC")
    fun getWifiRoomsSorted(): LiveData<List<WifiRoomItem>>

    //Contacts
    @Query("SELECT * FROM contacts")
    fun getContacts(): LiveData<List<ContactItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contactItems: List<ContactItem>)

    //Posts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(postItem: PostItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(postItem: List<PostItem>)

    @Update
    suspend fun updatePost(postItem: PostItem)

    @Delete
    fun deletePost(postItem: PostItem)

    @Query("SELECT * FROM posts")
    fun getPosts(): LiveData<List<PostItem>>

    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getPostsSorted(): LiveData<List<PostItem>>




    //Messages
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(messageItem: MessageItem)

    @Update
    suspend fun updateMessage(messageItem: MessageItem)

    @Delete
    suspend fun deleteMessage(messageItem: MessageItem)

    @Query("SELECT * FROM messages")
    fun getMessages(): LiveData<List<MessageItem>>
}