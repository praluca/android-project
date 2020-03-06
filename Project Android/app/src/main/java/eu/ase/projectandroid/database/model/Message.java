package eu.ase.projectandroid.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

//@Entity(tableName = "messages", foreignKeys = @ForeignKey(entity = UserRegistration.class,
//        parentColumns = "user_id",childColumns = "id", onDelete = CASCADE),indices = @Index(value = "user_id"))
@Entity(tableName = "messages")
public class Message implements Parcelable, Serializable {
    @PrimaryKey
    @ColumnInfo(name="id")
    private long id;

    //foreign key
   @ColumnInfo(name="user_id")
    private long userId;

    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "length")
    private int length;
    @ColumnInfo(name = "recipient")
    private String recipient;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }



    @Ignore
    public Message(long id,long userId, String content, int length, String recipient) {
        this.id = id;
        this.userId=userId;
        this.content = content;
        this.length = length;
        this.recipient = recipient;

    }

    public Message(String content, int length, String recipient) {
        this.content = content;
        this.length = length;
        this.recipient = recipient;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    protected Message(Parcel in) {
        id=in.readLong();
        userId=in.readLong();
        content = in.readString();
        length = in.readInt();
        recipient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(userId);
        dest.writeString(content);
        dest.writeInt(length);
        dest.writeString(recipient);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", length=" + length +
                ", recipient='" + recipient + '\'' +
                '}';
    }
}
