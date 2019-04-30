package ru.obessonova.module2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.SEND_SMS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ActivityA extends AppCompatActivity {
    
    private final static String TAB = "tab";
    private final int PERMISSION_REQUEST_CODE = 600;
    private TextView mSharedText;
    private TextView mFilePath;
    private Button mChoose;
    private Button mEmail;
    private Button mGmail;
    private Button mNext;
    private ImageView mImage;
    private TabLayout mTabs;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        
        setTitle(R.string.activityA);
        mFilePath = findViewById(R.id.filePath);
        mSharedText = findViewById(R.id.sharedText);
        mChoose = findViewById(R.id.choose);
        mEmail = findViewById(R.id.email);
        mGmail = findViewById(R.id.gmail);
        mNext = findViewById(R.id.next);
        mImage = findViewById(R.id.image);
        mTabs = findViewById(R.id.tabs);
        
        mSharedText.setVisibility(View.GONE);
        mFilePath.setVisibility(View.GONE);
        mChoose.setVisibility(View.GONE);
        mEmail.setVisibility(View.GONE);
        mGmail.setVisibility(View.GONE);
        mImage.setVisibility(View.GONE);
        
        checkPermission();
        
        if (savedInstanceState != null) {
            mTabs.getTabAt(savedInstanceState.getInt(TAB)).select();
        }
        
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                mTabs.getTabAt(1).select();
                handleSendText(intent);
            } else if (type.startsWith("image/")) {
                mTabs.getTabAt(2).select();
                handleSendImage(intent);
            }
        }
    }
    
    void handleSendText(Intent intent) {
        String newSharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (newSharedText != null) {
            mSharedText.setText(intent.getClipData().getItemAt(0).getText());
        }
    }
    
    void handleSendImage(Intent intent) {
        Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            mImage.setImageURI(imageUri);
        }
    }
    
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, READ_CONTACTS) != PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, SEND_SMS) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{READ_EXTERNAL_STORAGE, READ_CONTACTS, SEND_SMS},
                    PERMISSION_REQUEST_CODE);
        } else {
            initUI();
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PERMISSION_GRANTED
                        && grantResults[1] == PERMISSION_GRANTED
                        && grantResults[2] == PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissions Granted", Toast.LENGTH_LONG).show();
                    initUI();
                } else {
                    Toast.makeText(this, "Can't Work", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }
    
    private void initUI() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mSharedText.setVisibility(View.GONE);
                        mFilePath.setVisibility(View.GONE);
                        mChoose.setVisibility(View.GONE);
                        mEmail.setVisibility(View.GONE);
                        mGmail.setVisibility(View.GONE);
                        mNext.setVisibility(View.VISIBLE);
                        mImage.setVisibility(View.GONE);
                        break;
                    case 1:
                        mSharedText.setVisibility(View.VISIBLE);
                        mFilePath.setVisibility(View.VISIBLE);
                        mChoose.setVisibility(View.VISIBLE);
                        mEmail.setVisibility(View.VISIBLE);
                        mGmail.setVisibility(View.VISIBLE);
                        mNext.setVisibility(View.GONE);
                        mImage.setVisibility(View.GONE);
                        break;
                    case 2:
                        mSharedText.setVisibility(View.GONE);
                        mFilePath.setVisibility(View.GONE);
                        mChoose.setVisibility(View.GONE);
                        mEmail.setVisibility(View.GONE);
                        mGmail.setVisibility(View.GONE);
                        mNext.setVisibility(View.GONE);
                        mImage.setVisibility(View.VISIBLE);
                        break;
                }
            }
            
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //do nothing
            }
            
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //do nothing
            }
        });
    }
    
    public void chooseFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 7);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                    String pathHolder = data.getData().getPath();
                    mFilePath.setText(pathHolder);
                }
                break;
        }
    }
    
    public void clickButton(View view) {
        Intent intent = new Intent(this, ActivityB.class);
        startActivity(intent);
    }
    
    public void sendEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        try {
            File filelocation = new File (mFilePath.getText().toString());
            Uri path = Uri.fromFile(filelocation);
            intent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, e + "is exception raises during sending mail", Toast.LENGTH_LONG).show();
        }
    }
    
    public void sendGmail(View view) {
        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        try {
            //intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + mFilePath));
            final PackageManager pm = getPackageManager();
            final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
            ResolveInfo best = null;
            for (final ResolveInfo info : matches)
                if (info.activityInfo.packageName.endsWith(".gm") ||
                        info.activityInfo.name.toLowerCase().contains("mGmail")) best = info;
            if (best != null)
                intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, e + "is exception raises during sending mail", Toast.LENGTH_LONG).show();
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(TAB, mTabs.getSelectedTabPosition());
    }
}