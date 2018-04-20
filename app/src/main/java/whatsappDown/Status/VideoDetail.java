package whatsappDown.Status;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import de.mateware.snacky.Snacky;

public class VideoDetail extends AppCompatActivity {
    VideoView myimage;
    LinearLayout layout;


    ImageView download;

    String path;
    File myfile;

    Animation slideDown,slideUp;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        getSupportActionBar().hide();
        download= (ImageView) findViewById(R.id.downloadimage);
        myimage= (VideoView) findViewById(R.id.myimage);
        layout = (LinearLayout)findViewById(R.id.bottonlayout);

        path =  getIntent().getStringExtra("Image");
        myfile= new File(path);


       // Toast.makeText(this, "jkkk"+path, Toast.LENGTH_SHORT).show();
        Bitmap bitmap1 = BitmapFactory.decodeFile(path);
        BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap1);

        layout.setVisibility(View.INVISIBLE);
        slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_bottom);

        slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_bottom);


        if(layout.getVisibility()==View.INVISIBLE){

            layout.startAnimation(slideUp);
            layout.setVisibility(View.VISIBLE);
        }
        myimage.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(layout.getVisibility()==View.INVISIBLE){

                    layout.startAnimation(slideUp);
                    layout.setVisibility(View.VISIBLE);
                }
                else {
                    layout.startAnimation(slideDown);
                    layout.setVisibility(View.INVISIBLE);

                }
                return false;
            }

        });
        download.setOnClickListener(this.downloadMediaItem(myfile));
 /*if (currentFile.getAbsolutePath().endsWith(".mp4")) {
            holder.cardViewImageMedia.setVisibility(View.GONE);*/
//            holder.cardViewVideoMedia.setVisibility(View.VISIBLE);
       // Uri video = Uri.parse(myfile.getAbsolutePath());
       // myimage.setVideoPath(path);

        myimage.setVideoPath(path);
        myimage.requestFocus();
        myimage.start();
//



    }



    public View.OnClickListener downloadMediaItem(final File sourceFile) {

        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Runnable(){

                    @Override
                    public void run() {
                        try {
                            //String DIRECTORY_TO_SAVE_MEDIA_NOW = "/storage/emulated/legacy/"+ Resources.getSystem().getString(R.string.app_name)+"/";

                            Log.d("Hello", "onClick:  "+path);

                            copyFile(sourceFile, new File(path +sourceFile.getName()));
                            layout.startAnimation(slideUp);
                            layout.setVisibility(View.INVISIBLE);
                            Snacky.builder().
                                    setActivty(VideoDetail.this).
                                    setText(R.string.save_successful_message).setBackgroundColor(getResources().getColor(R.color.footercolor)).
                                    success().
                                    show();//

                            //Toast.makeText(activity,R.string.save_successful_message,Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("RecyclerV", "onClick: Error:"+e.getMessage() );

                            Snacky.builder().
                                    setActivty(VideoDetail.this).
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

    /**
     * copy file to destination.
     *
     * @param sourceFile
     * @param destFile
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File destFile) throws IOException {
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
    }

}
