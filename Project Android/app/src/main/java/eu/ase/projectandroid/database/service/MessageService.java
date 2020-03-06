package eu.ase.projectandroid.database.service;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import eu.ase.projectandroid.database.DatabaseManager;
import eu.ase.projectandroid.database.dao.MessageDao;
import eu.ase.projectandroid.database.model.Message;

public class MessageService {
    private static MessageDao messageDao;

    public static class GetAllMessage extends AsyncTask<Void,Void, List<Message>>{
        public GetAllMessage(Context context){
            messageDao= DatabaseManager.getInstance(context).getMessageDao();
        }
        @Override
        protected List<Message> doInBackground(Void... voids) {
            return messageDao.getAllMessages();
        }
    }

}
