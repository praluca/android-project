package eu.ase.projectandroid.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import eu.ase.projectandroid.database.dao.MessageDao;
import eu.ase.projectandroid.database.dao.UserRegistrationDao;
import eu.ase.projectandroid.database.model.Message;
import eu.ase.projectandroid.database.model.UserRegistration;

@Database(entities = { UserRegistration.class, Message.class},version =13,exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {
    private static final String DATABASE_NAME="project_db";
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context){

        if(databaseManager==null){
            synchronized (DatabaseManager.class){
                if(databaseManager==null){
                    databaseManager= Room.databaseBuilder(context,DatabaseManager.class,DATABASE_NAME)
                            .fallbackToDestructiveMigration().build();
                    return  databaseManager;
                }
            }
        }
        return databaseManager;
    }

    public abstract UserRegistrationDao getUserRegistrationDao();
    public abstract MessageDao getMessageDao();
}
