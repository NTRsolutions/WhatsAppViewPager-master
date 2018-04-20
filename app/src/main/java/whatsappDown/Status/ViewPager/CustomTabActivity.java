package whatsappDown.Status.ViewPager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import whatsappDown.Status.Fragment.ImageFragment;
import whatsappDown.Status.Fragment.VideoFragment;
import whatsappDown.Status.Fragment.SavedFragment;
import whatsappDown.Status.R;
import whatsappDown.Status.ViewPagerAdapter;

public class CustomTabActivity extends AppCompatActivity {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;
    private AdView mAdView;

    //Fragments

    VideoFragment videoFragment;
    ImageFragment imageFragment;
    SavedFragment savedFragment;

    String[] tabTitle={"Images","Videos"};
    int[] unreadCount={0,0,0};
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_without_icon);

        mInterstitialAd = new InterstitialAd(CustomTabActivity.this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.resentintestrrial));

        AdRequest adRequests = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequests);




        displayInterstitial();

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);

        String valueoftab=getIntent().getStringExtra("value");
        setupViewPager(viewPager,valueoftab);


        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        try
        {
            setupTabIcons();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position,false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        MobileAds.initialize(this, "3630685495225759~5195561875");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);




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


    private void setupViewPager(ViewPager viewPager,String values)
    {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        imageFragment =new ImageFragment();
        videoFragment =new VideoFragment();
        savedFragment =new SavedFragment();
        adapter.addFragment(imageFragment,"Images");
        adapter.addFragment(videoFragment,"Videos");
        adapter.addFragment(savedFragment,"Saved");
        viewPager.setAdapter(adapter);
        if (values.matches("recent")) {
            viewPager.setCurrentItem(0);
        }
        else {
            viewPager.setCurrentItem(2);
        }
    }

    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab,null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_title.setText(tabTitle[pos]);
        if(unreadCount[pos]>0)
        {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText(""+unreadCount[pos]);
        }
        else
            tv_count.setVisibility(View.GONE);


        return view;
    }

    private void setupTabIcons()
    {

        for(int i=0;i<tabTitle.length;i++)
        {
            /*TabLayout.Tab tabitem = tabLayout.newTab();
            tabitem.setCustomView(prepareTabView(i));
            tabLayout.addTab(tabitem);*/

            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
