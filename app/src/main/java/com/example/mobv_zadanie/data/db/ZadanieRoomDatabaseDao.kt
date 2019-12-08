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

    @Query("SELECT * FROM wifirooms WHERE uid=:uid")
    fun getWifiRooms(uid: String): LiveData<List<WifiRoomItem>>

    @Query("SELECT * FROM wifirooms WHERE uid=:uid ORDER BY ssid ASC")
    fun getWifiRoomsSorted(uid: String): LiveData<List<WifiRoomItem>>

    //Contacts
    @Query("SELECT * FROM contacts WHERE uid=:uid")
    fun getContacts(uid: String): LiveData<List<ContactItem>>

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

    @Query("SELECT * FROM posts WHERE roomdid =:roomid ORDER BY id DESC")
    fun getroomPosts(roomid:String): LiveData<List<PostItem>>


    //Messages
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(messageItem: MessageItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messageList: List<MessageItem>)

    @Update
    suspend fun updateMessage(messageItem: MessageItem)

    @Delete
    suspend fun deleteMessage(messageItem: MessageItem)

    @Query("SELECT * FROM messages")
    fun getMessages(): LiveData<List<MessageItem>>

    @Query("SELECT * FROM messages ORDER BY id DESC")
    fun getChatSorted(): LiveData<List<MessageItem>>

    @Query("SELECT * FROM messages WHERE contact =:contact ORDER BY id DESC")
    fun getcontactchatsorted(contact:String): LiveData<List<MessageItem>>


}