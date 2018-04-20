package whatsappDown.Status;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import whatsappDown.Status.Models.Data;
import whatsappDown.Status.ViewPager.CustomTabActivity;

public class SplashScreen extends AppCompatActivity {

    int STORAGE_PERMISSION_CODE=23,STORAGE_PERMISSION_CODE_Write=23;
    private static final String WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/.Statuses";


    ArrayList<File> imagepaths;
    ArrayList<String> imagedates;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        TextView whtsapp= (TextView) findViewById(R.id.actualtxt);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "Nexa Bold.otf");
       /* imagepaths=SortArrayList.getListFiles(new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_STATUSES_LOCATION));
        imagedates=SortArrayList.getListFilesDates(new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_STATUSES_LOCATION));*/
        Data data=new Data();
        data.setImagepaths(imagepaths);
        data.setImagedates(imagedates);
       save_User_To_Shared_Prefs(SplashScreen.this,data);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if(isReadStorageAllowed()){
            //If permission is already having then showing the toast
          //  Toast.makeText(SplashScreen.this,"You already have the permission",Toast.LENGTH_LONG).show();
            //Existing the method with return
            return;
        }

        //If the app has not the permission then asking for the permission
        requestStoragePermission();
    }

    public void start(View view) {


    }


    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED && write == PackageManager.PERMISSION_DENIED) {
            return true;
        } else {

            //If permission is not granted returning false
            return false;
        }
    }


    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)
                &&ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);


        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                //Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void recentStories(View view) {


        Intent i=new Intent(SplashScreen.this,CustomTabActivity.class);
        i.putExtra("value","recent");
        startActivity(i);
        finish();

    }

    public void saved(View view) {
        Intent i=new Intent(SplashScreen.this,CustomTabActivity.class);
        i.putExtra("value","saved");
        startActivity(i);
        finish();
    }


    public static void save_User_To_Shared_Prefs(Context context, Data _USER) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(_USER);
        prefsEditor.putString("data", json);
        prefsEditor.commit();

    }

    public void howtouse(View view) {
/*
        startActivity(new Intent(
                SplashScreen.this,HowToUse.class));
*/
        showdialog();
    }


    public void showdialog(){

        final Dialog dialog = new Dialog(SplashScreen.this);
        dialog.setCancelable(true);

        View view  = this.getLayoutInflater().inflate(R.layout.rate_us, null);
        dialog.setContentView(view);
        dialog.setTitle("How to Use");


        TextView edit = (TextView) view.findViewById(R.id.whatsappopen);



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                    startActivity(launchIntent);

                } catch (ActivityNotFoundException e) {

                }
                //Do something

            }
        });




        dialog.show();
    };

}
