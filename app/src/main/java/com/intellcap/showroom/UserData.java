package com.intellcap.showroom;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserData {
    private static final String DATA_FILE = "user_data";
    Context context;

    public static final String URL_DOMAIN = "https://jsonplaceholder.typicode.com";



    //Reading and Writing
    public UserData(Context c){
        context = c;
    }

    public String getUserData() {
        FileInputStream fileInputStream = null;
        String fileData = null;

        try{
            fileInputStream = context.openFileInput(DATA_FILE);
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            fileInputStream.close();
            fileData = new String(buffer,"UTF-8");
        }catch(FileNotFoundException e){
            Log.e("FILE","Couldnt find that file");
            e.printStackTrace();
        }catch(IOException e){
            Log.e("FILE","Error");
            e.printStackTrace();
        }finally{
            try{
                if(fileInputStream != null){
                    fileInputStream.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        return fileData;
    }

    public void saveUserData(String content) {
        FileOutputStream fileOutputStream = null;

        try{
            fileOutputStream = context.openFileOutput(DATA_FILE, Context.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
        }catch(FileNotFoundException e){
            Log.e("FILE","Couldnt find that file");
            e.printStackTrace();
        }catch(IOException e){
            Log.e("FILE","IO Error");
            e.printStackTrace();
        }finally{
            try{
                if(fileOutputStream != null){
                    fileOutputStream.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void deleteUserData(){
        FileOutputStream fileOutputStream = null;
        String content = "";

        try{
            fileOutputStream = context.openFileOutput(DATA_FILE, Context.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
        }catch(FileNotFoundException e){
            Log.e("FILE","Couldnt find that file");
            e.printStackTrace();
        }catch(IOException e){
            Log.e("FILE","IO Error");
            e.printStackTrace();
        }finally{
            try{
                if(fileOutputStream != null){
                    fileOutputStream.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
