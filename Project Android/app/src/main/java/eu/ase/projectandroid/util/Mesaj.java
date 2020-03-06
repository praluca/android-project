package eu.ase.projectandroid.util;

public class Mesaj {
    private int mImageDrawable;
    private String mTitlu;
    private String mMesaj;

    public Mesaj(int mImageDrawable, String mTitlu, String mMesaj) {
        this.mImageDrawable = mImageDrawable;
        this.mTitlu = mTitlu;
        this.mMesaj = mMesaj;
    }

    public int getmImageDrawable() {
        return mImageDrawable;
    }

    public void setmImageDrawable(int mImageDrawable) {
        this.mImageDrawable = mImageDrawable;
    }

    public String getmTitlu() {
        return mTitlu;
    }

    public void setmTitlu(String mTitlu) {
        this.mTitlu = mTitlu;
    }

    public String getmMesaj() {
        return mMesaj;
    }

    public void setmMesaj(String mMesaj) {
        this.mMesaj = mMesaj;
    }
}
