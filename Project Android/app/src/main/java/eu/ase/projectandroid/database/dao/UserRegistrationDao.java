package eu.ase.projectandroid.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.projectandroid.database.model.UserRegistration;

@Dao
public interface UserRegistrationDao {
    @Query("select * from users_reg")
    List<UserRegistration> getAllUsers();

    @Query("SELECT * FROM users_reg WHERE username = :username AND password = :password and nr_telefon=:phone")
    UserRegistration getUserByCredentials(String username, String password, String phone);

    @Query("select username from users_reg")
    List<String> getUsernameI();

    @Query("select password from users_reg")
    List<String> getPasswordI();

    @Query("select nr_telefon from users_reg")
    List<String> getPhoneI();

    @Query("select * from users_reg where user_id=:id")
   UserRegistration getUserById(long id);

    @Query("SELECT MAX(user_id) FROM users_reg")
    long getMaxId();

    @Query("SELECT COUNT(*) FROM users_reg WHERE tara LIKE :country")
    int getNumberOfUsersByCountry(String country);

    @Insert
    long insert(UserRegistration userRegistration);

    @Update
    int update(UserRegistration userRegistration);

    @Delete
    int delete(UserRegistration userRegistration);
}
