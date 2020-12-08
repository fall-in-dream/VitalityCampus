package com.huang.vitalitycampus.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Dialog {
    public static void show(final Context context, String hint, int image, String message) {
        AlertDialog aldg;
        AlertDialog.Builder adBd = new AlertDialog.Builder(context);
        adBd.setTitle(hint);
        adBd.setIcon(image);
        adBd.setMessage(message);
        adBd.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        aldg = adBd.create();
        aldg.show();
    }
}
