package com.example.coolkidsdev.ratingapp;

//2015 Wintress Technical Schools / League Of Amazing Programmers
//Developed by Matthew Smith and Russ Baxt (Will ask about spelling of last name)
//Version 0.1 - GUI TEST

        import android.app.Activity;

        import android.content.ClipData;
        import android.content.ClipData.Item;
        import android.content.Context;
        import android.os.Bundle;

        import android.os.StrictMode;

        import android.util.Log;
        import android.view.ContextMenu;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.RatingBar;



        import java.io.BufferedWriter;
        import java.io.FileOutputStream;

        import java.io.OutputStreamWriter;
        import java.util.Random;


public class MainActivity extends Activity {

    public static String teacherSelected = "None";
    public static float funSelected;
    public static float infoSelected;

    public static MenuItem a;
    public static MenuItem b;
    public static MenuItem c;

    public static Context context;

    public String badRatings[] = {
            "Please Provide A Rating",
            "Come on, it takes 2 seconds",
            "PLEASE",
            "Okay Now You're getting on my nerves",
            "You're wasting my time",
            "Why am I even writing these messages",
            "Its not like anyone is going to find them",
            "Im a bored programmer",
            "Hiding secret messages in apps...",
            "You know what...",
            "IM DONE",
            "RATE YOUR CLASS ALREADY!",
            "Do you want to know how many button presses you have made?",
            "Well I have a variable for that too..."
    };
    public int badRatingNum = -1;

    //This is just for a small easter egg im putting in...
    RatingBar ratingFun;
    RatingBar ratingLearn;

    MenuItem tA;
    MenuItem tB;
    MenuItem tC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        ratingFun = (RatingBar) findViewById(R.id.ratingBar);
        ratingLearn = (RatingBar) findViewById(R.id.ratingBar2);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        Log.d("Called?", "Working!");


        return super.onPrepareOptionsMenu(menu);

    }


    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.teacher_menu, menu);

        String[] names = DataBaseFunctions.getRatings();
        for(String name : names) {
            menu.add(0, 0, 0, name);
        }

    }



    @Override
    public boolean onContextItemSelected(MenuItem item){

        teacherSelected = item.getTitle().toString();
        RatingSaver.saveRating(funSelected, infoSelected, teacherSelected, getApplication());
        item.setChecked(true);
        return true;

    }

    public void submitRating(View v){

        ratingFun = (RatingBar) findViewById(R.id.ratingBar);
        ratingLearn = (RatingBar) findViewById(R.id.ratingBar2);

        if (ratingFun.getRating() == 0f || ratingLearn.getRating() == 0f) {
            //The next few lines are a secret I hid for anyone who wants to find it...
            badRatingNum++;

            if (badRatingNum > 13) {
                ToastStuff.createToast("You have pressed this " + badRatingNum + " times!", MainActivity.this);
            } else {
                ToastStuff.createToast(badRatings[badRatingNum], MainActivity.this);
            }

        } else {

            registerForContextMenu(v);
            invalidateOptionsMenu();
            openContextMenu(v);

            funSelected = ratingFun.getRating();
            infoSelected = ratingLearn.getRating();

            ToastStuff.createToast("Fun : " + ratingFun.getRating() + " Learn : " + ratingLearn.getRating(), MainActivity.this);

            ratingFun.setRating(0.0f);
            ratingLearn.setRating(0.0f);
            //Show a toast with the rating information...
            badRatingNum = -1;

        }


    }

    public void clearRating(View v){
        try {
            FileOutputStream fos = openFileOutput(Data.FILE_NAME, Context.MODE_PRIVATE);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write("");
            bw.close();
        } catch (Exception e) {
            ToastStuff.createToast("An Error Occured While Clearing Data!", getApplication());
            ToastStuff.createToast(e.getMessage(), getApplication());
        }
    }

    public void read(View v){
        RatingReader.readRatings(v);
    }
}
