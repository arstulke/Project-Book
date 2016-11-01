package arstulke.projectbook.view;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import arstulke.projectbook.controller.MyApplication;
import arstulke.projectbook.model.Library;
import arstulke.projectbook.utils.LibraryExport;


public class ShareDialog {

    private final Application application;
    private final Context context;
    private final Activity activity;

    public ShareDialog(Application application, Context context ,Activity activity) {
        this.application = application;
        this.context = context;
        this.activity = activity;
    }

    public void showShareDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(application);

        final HashMap<String, Boolean> columns = new HashMap<>();
        final String[] keys = new String[]{"isbn", "title", "author", "releaseDate", "publisher", "description", "buyLink"};
        {
            columns.put(keys[0], true);
            columns.put(keys[1], true);
            columns.put(keys[2], true);
            columns.put(keys[3], true);
            columns.put(keys[4], true);
            columns.put(keys[5], false);
            columns.put(keys[6], false);
        }
        builder.setMultiChoiceItems(new String[]{"ISBN", "Titel", "Autor", "Erscheinungsdatum", "Verlag", "Beschreibung", "Kauf-Link"},
                new boolean[]{true, true, true, true, true, false, false}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        columns.put(keys[which], isChecked);
                    }
                });
        builder.setTitle("Bibliothek teilen");

        builder.setPositiveButton("Teilen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Library lib = MyApplication.libraryManager.getLibrary(MyApplication.libName);

                String filename = lib.getName().replaceAll(" ", "_") + ".csv";
                String path = Environment.getExternalStorageDirectory() + "/" + filename;
                if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    try {
                        new File(path).createNewFile();
                        LibraryExport.asCSV(new FileOutputStream(new File(path)), lib, columns, ';');
                        Uri fileUri = Uri.parse("file://" + path);
                        System.out.println(fileUri);

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/csv");
                        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
                        activity.startActivity(Intent.createChooser(intent, "Email: "));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    requestWritePermission(context);
                }
            }
        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private static void requestWritePermission(final Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(context).setMessage("This app needs permission to access your files").setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
}
