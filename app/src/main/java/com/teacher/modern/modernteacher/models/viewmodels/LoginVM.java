package com.teacher.modern.modernteacher.models.viewmodels;

public class LoginVM {
    public long UId ;
    public String Password ;
    public int UserTypeId ;

    public LoginVM(long UId, String password, int userTypeId) {
        this.UId = UId;
        Password = password;
        UserTypeId = userTypeId;
    }
}
