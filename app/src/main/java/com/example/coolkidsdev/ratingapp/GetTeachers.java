package com.example.coolkidsdev.ratingapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Matthew on 2/12/2016.
 */
public class GetTeachers implements Runnable {

    InputStream is;
    public volatile String[] teach;
    @Override
    public void run() {
        String s = "";
        try {
            URL url = new URL("http://feedback.jointheleague.org/getTeachers.php");
            HttpURLConnection mUrlConnection = (HttpURLConnection) url.openConnection();
            mUrlConnection.setDoInput(true);

            is = new BufferedInputStream(mUrlConnection.getInputStream());

            s = readStream(is);
        }catch (Exception e){

        }

        String[] teachers = s.split(",");
        teachers[teachers.length - 1] = "None Of The Above";
        this.teach = teachers;
    }

    public String[] getTeachersArray(){
        return this.teach;
    }

    private static String readStream(InputStream is) throws Exception{

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {

        } finally {
            try {
                is.close();
            } catch (IOException e) {

            }
        }
        return sb.toString();
    }
}
