package com.diewland.smsurlextractor;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

public class MainActivity extends AppCompatActivity {

    String TAG = "DIEWLAND";

    private TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (TextView)findViewById(R.id.output);

        Permissions.check(this/*context*/, Manifest.permission.READ_SMS, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                readSms();
            }
        });
    }

    // https://stackoverflow.com/a/9494532/466693
    private void readSms(){
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                String msgData = "";
                for(int idx=0; idx < cursor.getColumnCount(); idx++) {
                    // msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                    if(cursor.getColumnName(idx).equals("body")){
                        String body = cursor.getString(idx);
                        String u = UrlFromString.extract(body);
                        if(!u.isEmpty()){
                            output.append(u + "\n");
                        }
                    }
                }

            } while (cursor.moveToNext());
        } else {
            // empty box, no SMS
        }
    }

    // share button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            menu.add(1, 1, 1, "Share")
                    .setIcon(android.R.drawable.ic_menu_share)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                String shareBody = output.getText().toString();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Urls from SMS");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
