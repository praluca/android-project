package eu.ase.projectandroid.network;

import android.content.Intent;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public static HttpResponse parseJson(String json){
        if(json==null){
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            List<Message>mesaj1=getMessageListFromJson(jsonObject.getJSONArray("mesaj1"));
            List<Message>mesaj2=getMessageListFromJson(jsonObject.getJSONArray("mesaj2"));
            return new HttpResponse(mesaj1,mesaj2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static List<Message> getMessageListFromJson(JSONArray array) throws JSONException {
        if(array==null)
        {
            return null;
        }
        List<Message> results=new ArrayList<>();
        for(int i=0;i<array.length();i++){
            Message message=getMessageFromJson(array.getJSONObject(i));
            if(message!=null)
            {
                results.add(message);
            }
        }
        return results;
    }
    private static Message getMessageFromJson(JSONObject object) throws JSONException{
        if(object==null)
        {
            return null;
        }
        String content=object.getString("content");
        int length=object.getInt("length");
        RecipientInfo recipientInfo=getRecipientInfoFromJson(object.getJSONObject("recipientInfo"));
        return  new Message(content,length,recipientInfo);



    }
    private static RecipientInfo getRecipientInfoFromJson(JSONObject object) throws JSONException{
        if(object==null){
            return  null;
        }
        String firstname=object.getString("firstname");
        String lastname=object.getString("lastname");
        String phone=object.getString("phone");
        return new RecipientInfo(firstname,lastname,phone);

    }
}
