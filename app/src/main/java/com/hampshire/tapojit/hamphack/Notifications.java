package com.hampshire.tapojit.hamphack;


public class Notifications {
    private int _id;
    private String _details;

    public Notifications(String _details) {
        this._details = _details;
    }

    public int get_id(){
        return _id;
    }

    public void set_id(int _id){
        this._id=_id;
    }

    public String get_details(){
        return _details;
    }

    public void set_details(String _details){
        this._details=_details;
    }
}