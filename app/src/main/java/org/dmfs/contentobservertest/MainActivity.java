package org.dmfs.contentobservertest;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import org.dmfs.rfc5545.DateTime;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity
{

    private ContentObserver observer;

    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        observer = new TaskObserver(new Handler());
        getContentResolver().
                registerContentObserver(
                        Uri.parse("content://org.dmfs.tasks"),
                        true,
                        observer);
    }


    @Override
    protected void onDestroy()
    {
        getContentResolver().unregisterContentObserver(observer);
        super.onDestroy();
    }


    public final class TaskObserver extends ContentObserver
    {
        public TaskObserver(Handler handler)
        {
            super(handler);
        }


        @Override
        public void onChange(boolean selfChange)
        {
            this.onChange(selfChange, null);
        }


        @Override
        public void onChange(boolean selfChange, Uri uri)
        {
            textView.setText(textView.getText().toString() + DateTime.nowAndHere().toString() + " " + uri + "\n");
        }
    }

}
