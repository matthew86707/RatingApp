package com.example.coolkidsdev.ratingapp;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastStuff {

    public static void createToast(String message, Context con){
        if (!(message.isEmpty())) {
            Toast toast = new Toast(con);
            toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
            toast.makeText(con, message, Toast.LENGTH_LONG).show();
        }
    }
}
