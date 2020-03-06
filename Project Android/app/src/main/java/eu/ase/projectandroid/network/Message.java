package eu.ase.projectandroid.network;

public class Message {
    private String content;
    private int length;
    private RecipientInfo recipientInfo;

    public Message(String content, int length, RecipientInfo recipientInfo) {
        this.content = content;
        this.length = length;
        this.recipientInfo = recipientInfo;
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

    public RecipientInfo getRecipientInfo() {
        return recipientInfo;
    }

    public void setRecipientInfo(RecipientInfo recipientInfo) {
        this.recipientInfo = recipientInfo;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", length=" + length +
                ", recipientInfo=" + recipientInfo +
                '}';
    }
}
