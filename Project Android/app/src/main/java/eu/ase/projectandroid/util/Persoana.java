package eu.ase.projectandroid.util;

import android.os.Parcel;
import android.os.Parcelable;

public class Persoana implements Parcelable {
    private String nume;
    private String prenume;
    private String email;
    private String parola;
    private String confirmareParola;
    private String tara;
    private String sex;
    private int nrTelefon;

    public Persoana(String prenume, String nume,String email, String parola, String confirmareParola, String tara, String sex, int nrTelefon) {
        this.prenume = prenume;
        this.nume=nume;
        this.email = email;
        this.parola = parola;
        this.confirmareParola = confirmareParola;
        this.tara = tara;
        this.sex = sex;
        this.nrTelefon = nrTelefon;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getConfirmareParola() {
        return confirmareParola;
    }

    public void setConfirmareParola(String confirmareParola) {
        this.confirmareParola = confirmareParola;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(int nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

    @Override
    public String toString() {
        return "Persoana{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", email='" + email + '\'' +
                ", parola='" + parola + '\'' +
                ", confirmareParola='" + confirmareParola + '\'' +
                ", tara='" + tara + '\'' +
                ", sex='" + sex + '\'' +
                ", nrTelefon=" + nrTelefon +
                '}';
    }

    protected Persoana(Parcel in) {
        prenume=in.readString();
        nume=in.readString();
        email=in.readString();
        parola=in.readString();
        confirmareParola=in.readString();
        tara=in.readString();
        sex=in.readString();
        nrTelefon=in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(prenume);
        dest.writeString(nume);
        dest.writeString(email);
        dest.writeString(parola);
        dest.writeString(confirmareParola);
        dest.writeString(tara);
        dest.writeString(sex);
        dest.writeInt(nrTelefon);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Persoana> CREATOR = new Creator<Persoana>() {
        @Override
        public Persoana createFromParcel(Parcel in) {
            return new Persoana(in);
        }

        @Override
        public Persoana[] newArray(int size) {
            return new Persoana[size];
        }
    };
}
