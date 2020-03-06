package eu.ase.projectandroid.database.service;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import eu.ase.projectandroid.database.DatabaseManager;
import eu.ase.projectandroid.database.dao.UserRegistrationDao;
import eu.ase.projectandroid.database.model.UserRegistration;

public class UserRegistrationService {
    private static UserRegistrationDao userRegistrationDao;

    public static class GetAllUsers extends AsyncTask<Void,Void, List<UserRegistration>>{

        public GetAllUsers(Context context)
        {
           userRegistrationDao=DatabaseManager.getInstance(context).getUserRegistrationDao();
        }
        @Override
        protected List<UserRegistration> doInBackground(Void... voids) {
            return userRegistrationDao.getAllUsers();
        }
    }
    public static class  GetUsernameI extends AsyncTask<Void, Void,List<String>>{

        public GetUsernameI(Context context){
            userRegistrationDao=DatabaseManager.getInstance(context).getUserRegistrationDao();
        }
        @Override
        protected List<String> doInBackground(Void... voids) {
            return userRegistrationDao.getUsernameI();
        }
    }

    public static class  GetPasswordI extends AsyncTask<Void, Void,List<String>>{

        public GetPasswordI(Context context){
            userRegistrationDao=DatabaseManager.getInstance(context).getUserRegistrationDao();
        }
        @Override
        protected List<String> doInBackground(Void... voids) {
            return userRegistrationDao.getPasswordI();
        }
    }
    public static class  GetPhoneI extends AsyncTask<Void, Void,List<String>>{

        public GetPhoneI(Context context){
            userRegistrationDao=DatabaseManager.getInstance(context).getUserRegistrationDao();
        }
        @Override
        protected List<String> doInBackground(Void... voids) {
            return userRegistrationDao.getPhoneI();
        }
    }

}
