package file.zhang.entity;

import java.io.Serializable;

public class Guest_infoWithBLOBs extends Guest_info implements Serializable {
    private String personalInfo;

    private byte[] photo;

    public String getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(String personalInfo) {
        this.personalInfo = personalInfo;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}