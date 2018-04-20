package whatsappDown.Status;

import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import whatsappDown.Status.adapter.FullScreenSavedAdapter;

public class SavedVideoSlider extends AppCompatActivity {
    ViewPager viewPager;
    private Utils utils;
    private FullScreenSavedAdapter adapter;
    ImageView layout;
    private Animation slideUp;
    private Animation slideDown;
    InterstitialAd mInterstitialAd;
    int counter = 1;
    int counter2 = 1;
    int loopcounter=5;

    private static final String WHATSAPP_STATUSES_LOCATIONs= "/DCIM/StatusDownloader/";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);
        getSupportActionBar().hide();

        viewPager = (ViewPager) findViewById(R.id.pager);





        utils = new Utils(getApplicationContext());

        Intent i = getIntent();
        int position = i.getIntExtra("Image", 0);

        //Toast.makeText(this, "position"+position, Toast.LENGTH_SHORT).show();


        adapter = new FullScreenSavedAdapter(SavedVideoSlider.this,
                getListFiles(new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_STATUSES_LOCATIONs)));

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(position);


        slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_bottom);

        slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_bottom);


        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(SavedVideoSlider.this, "Clicked", Toast.LENGTH_SHORT).show();

            }
        });
        int viewpaggeritem=viewPager.getCurrentItem();
        //  Toast.makeText(this, "check view pagger item num"+viewpaggeritem, Toast.LENGTH_SHORT).show();



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                if (counter==5) {

                    mInterstitialAd = new InterstitialAd(SavedVideoSlider.this);

                    // set the ad unit ID
                    mInterstitialAd.setAdUnitId(getString(R.string.savedintestrrial));

                    AdRequest adRequests = new AdRequest.Builder()
                            .build();

                    // Load ads into Interstitial Ads
                    mInterstitialAd.loadAd(adRequests);



counter2 = 0;
                    displayInterstitial();
                    //Toast.makeText(SavedVideoSlider.this, "show"+counter, Toast.LENGTH_SHORT).show();


                }
                if (counter2%10==0){
                    mInterstitialAd = new InterstitialAd(SavedVideoSlider.this);

                    // set the ad unit ID
                    mInterstitialAd.setAdUnitId(getString(R.string.savedintestrrial));

                    AdRequest adRequests = new AdRequest.Builder()
                            .build();

                    // Load ads into Interstitial Ads
                    mInterstitialAd.loadAd(adRequests);




                    displayInterstitial();
                   // Toast.makeText(SavedVideoSlider.this, "show"+counter, Toast.LENGTH_SHORT).show();


                }
                counter=counter+1;
                counter2=counter2+1;
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
    }

    public void displayInterstitial(){

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });

    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
    private ArrayList<String> getListFiles(File parentDir) {
        ArrayList<String> inFiles = new ArrayList<String>();
        File[] files;
        files = parentDir.listFiles();

        Arrays.sort( files, new Comparator() {
            public int compare(Object o1, Object o2) {

                if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                    return -1;
                } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                    return +1;
                } else {
                    return 0;
                }
            }
        });
        if (files != null) {
            for (File file : files) {

                if (file.getName().endsWith(".jpg") ||
                        file.getName().endsWith(".gif") ||file.getName().endsWith(".mp4") ) {
                    if (!inFiles.contains(file))
                        inFiles.add(String.valueOf(file));
                }
            }
        }
        return inFiles;
    }


    public void leftside(View view) {
        viewPager.arrowScroll(View.FOCUS_LEFT);
    }

    public void rightside(View view) {
        viewPager.arrowScroll(View.FOCUS_RIGHT);
    }
}

