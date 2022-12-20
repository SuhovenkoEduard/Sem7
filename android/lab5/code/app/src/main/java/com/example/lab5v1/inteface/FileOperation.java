package com.example.lab5v1.inteface;

import android.content.Context;

public interface FileOperation {

    void writeFile(String data, Context context);
    String readFile(Context context);
}
