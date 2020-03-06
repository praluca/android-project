package eu.ase.projectandroid.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.projectandroid.database.model.Message;

@Dao
public interface MessageDao {
    //selecteaza toate mesajele din baza de date
    @Query("select* from messages")
    List<Message> getAllMessages();


    //selecteaza continutul messajelor trimise catre un anumit destinatar
    @Query("select content from messages where recipient like:name")
    List<String> getMessagesR(String name);

    @Insert
    long insertMessage(Message message);

    @Query("SELECT MAX(id) FROM messages")
    long getMaxId();


}
