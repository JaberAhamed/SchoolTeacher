package com.teacher.modern.modernteacher.models;

public class Message {
    public long SenderId ;
    public long ReceiverId ;
    public String Message1 ;
    public String Date ;

    public Message(long senderId, long receiverId, String message1, String date) {
        SenderId = senderId;
        ReceiverId = receiverId;
        Message1 = message1;
        Date = date;
    }
}
