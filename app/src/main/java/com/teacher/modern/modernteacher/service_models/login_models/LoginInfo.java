package com.teacher.modern.modernteacher.service_models.login_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginInfo {

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getTerminalId() {
        return TerminalId;
    }

    public void setTerminalId(String terminalId) {
        TerminalId = terminalId;
    }

    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("Password")
    @Expose
    private String Password;

    @SerializedName("UserType")
    @Expose
    private String UserType;

    @SerializedName("TerminalId")
    @Expose
    private String TerminalId;

    public LoginInfo(String email, String password, String userType, String terminalId) {
        Email = email;
        Password = password;
        UserType = userType;
        TerminalId = terminalId;
    }
}
