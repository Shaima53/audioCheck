package com.example.a96653.practice;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class firstlevel_1 extends AppCompatActivity {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    public static SQLiteHelper sqLiteHelper;
    private ImageView imageViewHeadphones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstlevel_1);

        sqLiteHelper = new SQLiteHelper(this, "AUDIODB2.db");
        String sql = "CREATE TABLE IF NOT EXISTS audioNew ( mp MEDIUMBLOB )";
        sqLiteHelper.queryData(sql);


        InputStream inStream = this.getResources().openRawResource(R.raw.test);
        try {
            byte[] music3 = new byte[inStream.available()];
            music3 = convertStreamToByteArray(inStream, music3.length);
            inStream.close();

            Toast.makeText(this, "Recordin Finished" + music3.length + " " + music3.length, Toast.LENGTH_LONG).show();

            sqLiteHelper.insertData(music3, music3.length);
            Toast.makeText(getApplicationContext(), " added", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "not added", Toast.LENGTH_SHORT).show();
        }


        Button goButton = (Button) findViewById(R.id.button3);

        goButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        openSecondActivity();
                    }

                }

        );
    }


    public void openSecondActivity() {
        Intent intent = new Intent(this, firstlevel_2.class);
        startActivity(intent);

    }

    public void btn_save(View view) {

        try {
            Cursor cursor = sqLiteHelper.getData();
            String x = "";
            if (cursor == null) x = "null";
            cursor.moveToFirst();
            //  Log.d("myTag", "This is my message");
            int index = cursor.getColumnIndexOrThrow("mp");
            byte[] voice = cursor.getBlob(index);
            playMp3(voice);


            Toast.makeText(getApplicationContext(), " get" + x, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "not get", Toast.LENGTH_SHORT).show();
        }
    }

    public static byte[] convertStreamToByteArray(InputStream is, int leng) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[leng];
        int i = Integer.MAX_VALUE;
        while ((i = is.read(buff, 0, buff.length)) > 0) {
            baos.write(buff, 0, i);
        }

        return baos.toByteArray(); // be sure to close InputStream in calling function
    }


    private void playMp3(byte[] mp3SoundByteArray) {
        try {
            // create temp file that will hold byte array
            File tempMp3 = File.createTempFile("kurchina", "mp3", getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(mp3SoundByteArray);
            fos.close();

            // resetting mediaplayer instance to evade problems
            mediaPlayer.reset();

            // In case you run into issues with threading consider new instance like:
            // MediaPlayer mediaPlayer = new MediaPlayer();

            // Tried passing path directly, but kept getting
            // "Prepare failed.: status=0x1"
            // so using file descriptor instead
            FileInputStream fis = new FileInputStream(tempMp3);
            mediaPlayer.setDataSource(fis.getFD());

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException ex) {
            String s = ex.toString();
            ex.printStackTrace();
        }
    }

}














