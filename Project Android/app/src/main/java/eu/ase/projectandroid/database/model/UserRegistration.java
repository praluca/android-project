package eu.ase.projectandroid.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "users_reg")
public class UserRegistration implements Parcelable, Serializable {
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    public long userId;

    @ColumnInfo(name = "nume")
    private String nume;
    @ColumnInfo(name = "prenume")
    private String prenume;
    @ColumnInfo(name = "username")
    private String email;
    @ColumnInfo(name = "password")
    private String parola;
    @ColumnInfo(name = "confirm_password")
    private String confirmareParola;
    @ColumnInfo(name = "tara")
    private String tara;
    @ColumnInfo(name="sex")
    private String sex;
    @ColumnInfo(name = "nr_telefon")
    private String nrTelefon;

    @Ignore
    public UserRegistration(long id, String nume, String prenume, String email, String parola, String confirmareParola, String tara, String sex, String nrTelefon) {
        this.userId = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.parola = parola;
        this.confirmareParola = confirmareParola;
        this.tara = tara;
        this.sex = sex;
        this.nrTelefon = nrTelefon;
    }

    public UserRegistration(String prenume, String nume, String email, String parola, String confirmareParola, String tara, String sex, String nrTelefon) {
        this.prenume = prenume;
        this.nume=nume;
        this.email = email;
        this.parola = parola;
        this.confirmareParola = confirmareParola;
        this.tara = tara;
        this.sex = sex;
        this.nrTelefon = nrTelefon;
    }

    public long getId() {
        return userId;
    }

    public void setId(long id) {
        this.userId = id;
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

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) {
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

    protected UserRegistration(Parcel in) {
        userId=in.readLong();
        prenume=in.readString();
        nume=in.readString();
        email=in.readString();
        parola=in.readString();
        confirmareParola=in.readString();
        tara=in.readString();
        sex=in.readString();
        nrTelefon=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(userId);
        dest.writeString(prenume);
        dest.writeString(nume);
        dest.writeString(email);
        dest.writeString(parola);
        dest.writeString(confirmareParola);
        dest.writeString(tara);
        dest.writeString(sex);
        dest.writeString(nrTelefon);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserRegistration> CREATOR = new Creator<UserRegistration>() {
        @Override
        public UserRegistration createFromParcel(Parcel in) {
            return new UserRegistration(in);
        }

        @Override
        public UserRegistration[] newArray(int size) {
            return new UserRegistration[size];
        }
    };
}
