package eu.ase.projectandroid.network;

import java.util.List;

public class HttpResponse {
    //mesaj1= mesaje trimise
    //mesaj2=mesaje primite
    List<Message> mesaj1;
    List<Message> mesaj2;

    public HttpResponse(List<Message> mesaj1, List<Message> mesaj2) {
        this.mesaj1 = mesaj1;
        this.mesaj2 = mesaj2;
    }

    public List<Message> getMesaj1() {
        return mesaj1;
    }

    public void setMesaj1(List<Message> mesaj1) {
        this.mesaj1 = mesaj1;
    }

    public List<Message> getMesaj2() {
        return mesaj2;
    }

    public void setMesaj2(List<Message> mesaj2) {
        this.mesaj2 = mesaj2;
    }


    @Override
    public String toString() {
        return "HttpResponse{" +
                "mesaj1=" + mesaj1 +
                ", mesaj2=" + mesaj2 +
                '}';
    }
}
