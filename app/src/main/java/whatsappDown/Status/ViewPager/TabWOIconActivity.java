package whatsappDown.Status.ViewPager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import whatsappDown.Status.Fragment.ImageFragment;
import whatsappDown.Status.Fragment.VideoFragment;
import whatsappDown.Status.Fragment.SavedFragment;
import whatsappDown.Status.R;
import whatsappDown.Status.ViewPagerAdapter;

public class TabWOIconActivity extends AppCompatActivity {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    //Fragments

    VideoFragment videoFragment;
    ImageFragment imageFragment;
    SavedFragment savedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_without_icon);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

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

        setupViewPager(viewPager);


    }




    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        imageFragment =new ImageFragment();
        videoFragment =new VideoFragment();
        savedFragment =new SavedFragment();
        adapter.addFragment(imageFragment,"CALLS");
        adapter.addFragment(videoFragment,"CHAT");
        adapter.addFragment(savedFragment,"CONTACTS");
        viewPager.setAdapter(adapter);
    }

}
