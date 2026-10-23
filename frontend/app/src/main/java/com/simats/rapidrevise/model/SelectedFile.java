package com.simats.rapidrevise.model;

import android.net.Uri;

public class SelectedFile {

    private Uri uri;
    private String name;

    public SelectedFile(Uri uri,String name){
        this.uri=uri;
        this.name=name;
    }

    public Uri getUri(){
        return uri;
    }

    public String getName(){
        return name;
    }
}