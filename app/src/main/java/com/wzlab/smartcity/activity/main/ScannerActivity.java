package com.wzlab.smartcity.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.skateboard.zxinglib.CaptureActivity;
import com.wzlab.smartcity.activity.R;

public class ScannerActivity extends AppCompatActivity {

    private ImageView mIvQRScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1001 && resultCode== Activity.RESULT_OK)
        {
            String result=data.getStringExtra(CaptureActivity.KEY_DATA);
            Toast.makeText(this, "qrcode result is "+result, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title_bar_tools, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_qr_scanner){

            Intent intent=new Intent(ScannerActivity.this, CaptureActivity.class);

            startActivityForResult(intent,1001);


        }
        return true;
    }
}
