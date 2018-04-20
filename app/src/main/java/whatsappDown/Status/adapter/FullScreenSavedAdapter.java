package whatsappDown.Status.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import de.mateware.snacky.Snacky;
import whatsappDown.Status.Permissions.SingleMediaScanner;
import whatsappDown.Status.R;

import static whatsappDown.Status.adapter.ImageListAdapter.MyPREFERENCES;

/**
 * Created by navee on 3/10/2018.
 */

public class FullScreenSavedAdapter extends PagerAdapter {

    private Activity _activity;
    private ArrayList<String> _imagePaths;
    private LayoutInflater inflater;
    private Animation slideUp;
    private Animation slideDown;
    private Animation bottomcirclebottom;
    private Animation bottomcircletop;
    int downloadposition;
    File myfile;
    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();


    private int mCoinCount;
    private TextView mCoinCountText;
    private CountDownTimer mCountDownTimer;
    private boolean mGameOver;
    private boolean mGamePaused;
    private RewardedVideoAd mRewardedVideoAd;
    private Button mRetryButton;
    private Button mShowVideoButton;
    private long mTimeRemaining;
    TextView textView;
    private static final long COUNTER_TIME = 10;
    private static final int GAME_OVER_REWARD = 1;
    private InterstitialAd mInterstitialAd;



    // constructor
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public FullScreenSavedAdapter(Activity activity,
                                  ArrayList<String> imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
        builder.detectFileUriExposure();

        SharedPreferences prfs = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        int counter=prfs.getInt("savecounter",0);
      //  Toast.makeText(activity, "counter"+counter, Toast.LENGTH_SHORT).show();


        mInterstitialAd = new InterstitialAd(activity);
        // Defined in res/values/strings.xml
        mInterstitialAd.setAdUnitId(activity.getString(R.string.savedintestrrial));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Toast.makeText(Search.this, "ad closed", Toast.LENGTH_SHORT).show();


            }
        });
        if (counter==0) {

            MobileAds.initialize(activity, "ca-app-pub-3630685495225759/3099472451");

            // Create the InterstitialAd and set the adUnitId.
            mInterstitialAd = new InterstitialAd(activity);
            // Defined in res/values/strings.xml
            mInterstitialAd.setAdUnitId(activity.getString(R.string.savedintestrrial));

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    // Toast.makeText(Search.this, "ad closed", Toast.LENGTH_SHORT).show();


                }
            });

            startGame(1);
        }

    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView imgDisplay;
        final VideoView imgDisplay2;
        final ImageView btnClose;
        final ImageView downloadedbtn;
        final ImageView navleft;
        downloadposition = position;
        ImageView play;


        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.saved_video_slider, container,
                false);
        play = (ImageView) viewLayout.findViewById(R.id.play);


        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
        imgDisplay2 = (VideoView) viewLayout.findViewById(R.id.imgDisplayVideo);
        btnClose = (ImageView) viewLayout.findViewById(R.id.btnClose);
        downloadedbtn = (ImageView) viewLayout.findViewById(R.id.downloadmyimage);
        navleft = (ImageView) viewLayout.findViewById(R.id.left_nav);
        imgDisplay.setVisibility(View.GONE);
        imgDisplay2.setVisibility(View.GONE);
        if (_imagePaths.get(position).endsWith(".jpg") || _imagePaths.get(position).endsWith("endsWith(.gif")) {
            play.setVisibility(View.GONE);


            //Toast.makeText(_activity, "PNG"+_imagePaths.get(position), Toast.LENGTH_SHORT).show();
            imgDisplay.setVisibility(View.VISIBLE);
            imgDisplay2.setVisibility(View.GONE);
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position), options);
            imgDisplay.setImageBitmap(bitmap);

        } else {
            play.setVisibility(View.VISIBLE);
            imgDisplay.setVisibility(View.GONE);
            imgDisplay2.setVisibility(View.VISIBLE);

            final String newson = _imagePaths.get(position);
            imgDisplay2.setVideoPath(newson);
            imgDisplay2.requestFocus();
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(_imagePaths.get(position),
                    MediaStore.Images.Thumbnails.MINI_KIND);
            imgDisplay2.pause();
            BitmapDrawable bitmapDrawable = new BitmapDrawable(thumb);

            imgDisplay2.setBackgroundDrawable(bitmapDrawable);
        }
        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _activity.finish();
            }
        });
        slideUp = AnimationUtils.loadAnimation(_activity, R.anim.topcirlcetobuttom);

        slideDown = AnimationUtils.loadAnimation(_activity, R.anim.topcircletotop);
        bottomcircletop = AnimationUtils.loadAnimation(_activity, R.anim.bottomcircletop);
        bottomcirclebottom = AnimationUtils.loadAnimation(_activity, R.anim.bottomcircledown);
        btnClose.setVisibility(View.GONE);
        downloadedbtn.setVisibility(View.GONE);



        play.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                StrictMode.setVmPolicy(builder.build());

                File file = new File(_imagePaths.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "video/*");
                _activity.startActivity(intent);
/*
                VideoInfo videoInfo = new VideoInfo(Uri.parse(_imagePaths.get(position)))
                        .setTitle("test video").setBgColor(_activity.getResources().getColor(R.color.myblack)) //config title
                        .setShowTopBar(true).setFullScreenAnimation(true)//show mediacontroller top bar
                        .setPortraitWhenFullScreen(true);
                GiraffePlayer.play(_activity, videoInfo);*/
                return false;
            }
        });
        imgDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        myfile= new File(_imagePaths.get(position));
        downloadedbtn.setOnClickListener(this.downloadMediaItem(myfile));

        ((ViewPager) container).addView(viewLayout);



        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }


    public View.OnClickListener downloadMediaItem(final File sourceFile) {

        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Runnable(){

                    @Override
                    public void run() {
                        try {
                         //   String DIRECTORY_TO_SAVE_MEDIA_NOW = "/storage/emulated/legacy/"+"/WhatsApp/Media/.Statuses/Saved/";
                            //String DIRECTORY_TO_SAVE_MEDIA_NOW = "/WhatsApp/Media/.Statuses/Saved/";
                            String DIRECTORY_TO_SAVE_MEDIA_NOW = "/storage/emulated/0/DCIM/StatusDownloader/";

                            Log.d("Hello", "onClick: "+DIRECTORY_TO_SAVE_MEDIA_NOW);


                            copyFiles(sourceFile, new File(DIRECTORY_TO_SAVE_MEDIA_NOW +sourceFile.getName()));


                            Snacky.builder().
                                    setActivty(_activity).
                                    setText(R.string.save_successful_message).setBackgroundColor(_activity.getResources().getColor(R.color.footercolor)).
                                    success().
                                    show();//
                            new SingleMediaScanner(_activity, new File(DIRECTORY_TO_SAVE_MEDIA_NOW +sourceFile.getName()));
                            startGame(2);
                            //Toast.makeText(activity,R.string.save_successful_message,Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("RecyclerV", "onClick: Error:"+e.getMessage() );

                            Snacky.builder().
                                    setActivty(_activity).
                                    setText(R.string.save_error_message).
                                    error().
                                    show();
                            //Toast.makeText(activity,R.string.save_error_message,Toast.LENGTH_SHORT).show();
                        }
                    }
                }.run();
            }
        };
    }

    public static void copyFiles(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }

    } private void createTimer(long time) {

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CountDownTimer(time * 100, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                mTimeRemaining = ((millisUnitFinished / 1000) + 1);

            }

            @Override
            public void onFinish() {

                displayInterstitial(1);
                // Toast.makeText(Search.this, "dislay after else", Toast.LENGTH_SHORT).show();
                //displayInterstitial();

                addCoins(GAME_OVER_REWARD);


                mGameOver = true;
            }
        };
        mCountDownTimer.start();
    }

    private void createTimer2(long time) {

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CountDownTimer(time * 700, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                mTimeRemaining = ((millisUnitFinished / 1000) + 1);

            }

            @Override
            public void onFinish() {

                displayInterstitial(2);
                // Toast.makeText(Search.this, "dislay after else", Toast.LENGTH_SHORT).show();
                //displayInterstitial();

                addCoins(GAME_OVER_REWARD);


                mGameOver = true;
            }
        };
        mCountDownTimer.start();
    }

    private void addCoins(int coins) {
        mCoinCount += coins;

    }


    private void startGame(int value) {


        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }
        if (value==1) {
            createTimer(COUNTER_TIME);
        }
        else{
            createTimer2(1);
        }
        mGamePaused = false;
        mGameOver = false;
    }

    private void displayInterstitial(int value) {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            //Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            startGame(value);
        }
    }




}



