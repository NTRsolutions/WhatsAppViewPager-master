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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import de.mateware.snacky.Snacky;
import fr.tvbarthel.intentshare.IconLoader;
import fr.tvbarthel.intentshare.IntentShareListener;
import fr.tvbarthel.intentshare.TargetActivityComparatorProvider;
import whatsappDown.Status.R;
import whatsappDown.Status.SavedVideoSlider;

/**
 * Created by navee on 3/9/2018.
 */

public class SavelistAdapter extends RecyclerView.Adapter<SavelistAdapter.FileHolder> {

    private static String DIRECTORY_TO_SAVE_MEDIA_NOW = "/storage/emulated/Pictures/WSDownloader/";
    private ArrayList<File> filesList;
    private ArrayList<String> filesListdates;
    private Activity activity;
    private IntentShareListener intentShareListener;
    private String targetPackage;
    private IconLoader iconLoader;


    public static final String MyPREFERENCES = "MyPrefs";

    SharedPreferences sharedpreferences;
    public  SharedPreferences.Editor editor;
    String counterprs="savecounter";
    int counter=0;

    private TargetActivityComparatorProvider customComparatorProvider;

    public SavelistAdapter(ArrayList<File> filesList, Activity activity, ArrayList<String> filesListdates) {
        this.filesList = filesList;
        this.activity = activity;
        this.filesListdates = filesListdates;
    }

    @Override
    public SavelistAdapter.FileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.savelistview, parent, false);

        targetPackage = null;
        customComparatorProvider = null;

        intentShareListener = new IntentShareListener() {
            @Override
            public void onCompleted(String packageName) {
                targetPackage = packageName;
            }

            @Override
            public void onCanceled() {
                Toast.makeText(activity, "Sharing canceled", Toast.LENGTH_SHORT).show();
            }
        };
        return new FileHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(final SavelistAdapter.FileHolder holder, final int position) {
        final File currentFile = filesList.get(position);
        final String currentdates= filesListdates.get(position);
        String date = null,time = null;

holder.playbtn.setVisibility(View.GONE);
holder.imagebtn.setVisibility(View.GONE);

        date = currentdates.substring(0, Math.min(currentdates.length(), 10));
        time = currentdates.substring(10, Math.min(currentdates.length(), currentdates.length()));
        if (currentFile.getAbsolutePath().endsWith(".mp4")) {

            holder.playbtn.setVisibility(View.VISIBLE);
            holder.imagebtn.setVisibility(View.GONE);
holder.dates.setText("Video");
            String video = String.valueOf(Uri.parse(currentFile.getAbsolutePath()));

            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(video, MediaStore.Images.Thumbnails.MINI_KIND);

            holder.imageViewImageMedia.setImageBitmap(thumb);
        }
        else {
            holder.playbtn.setVisibility(View.GONE);
            holder.imagebtn.setVisibility(View.VISIBLE);
            holder.dates.setText("Image");
//        holder.buttonImageDownload.setOnClickListener(this.downloadMediaItem(currentFile));
//        holder.buttonVideoDownload.setOnClickListener(this.downloadMediaItem(currentFile));
            final Bitmap myBitmap = BitmapFactory.decodeFile(currentFile.getAbsolutePath());
            holder.imageViewImageMedia.setImageBitmap(myBitmap);
        }
        holder.time.setVisibility(View.VISIBLE);
        holder.time.setText(date);
        /*if (currentFile.getAbsolutePath().endsWith(".mp4")) {
            holder.cardViewImageMedia.setVisibility(View.GONE);*/
//            holder.cardViewVideoMedia.setVisibility(View.VISIBLE);
        Uri video = Uri.parse(currentFile.getAbsolutePath());

        holder.mylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (counter==3){
                    String myPath= String.valueOf(filesList.get(position));
                    Intent i = new Intent(activity,SavedVideoSlider.class);
                    //  i.putExtra("Image", myPath);
                    i.putExtra("Image", position);
                    activity.startActivity(i);

                   counter=0;
                    sharedpreferences = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
                    editor = sharedpreferences.edit();
                    editor.putInt(counterprs, counter);

                    editor.commit();
                }
                else {

                    sharedpreferences = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
                    editor = sharedpreferences.edit();
                    editor.putInt(counterprs, counter);

                    editor.commit();
                    counter = counter + 1;

                    String myPath= String.valueOf(filesList.get(position));
                    Intent i = new Intent(activity,SavedVideoSlider.class);
                    //  i.putExtra("Image", myPath);
                    i.putExtra("Image", position);
                    activity.startActivity(i);
                }
                //Toast.makeText(activity, ""+filesList.get(position), Toast.LENGTH_SHORT).show();
               // Toast.makeText(activity, "clicked"+position, Toast.LENGTH_SHORT).show();

            }
        });


holder.sharebtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (currentFile.getAbsolutePath().endsWith(".mp4")){


            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("video/mp4");
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Subject");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Shared via: https://goo.gl/1UgQVz");
            shareIntent.putExtra(Intent.EXTRA_ORIGINATING_URI,"https://goo.gl/1UgQVz");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(currentFile.getAbsolutePath()));
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivityForResult(Intent.createChooser(shareIntent, "Share Your Video"),1);
/*
com.status.downloader.codexive

https://goo.gl/1UgQVz
*/
        }
        else {
            BitmapDrawable drawable = (BitmapDrawable) holder.imageViewImageMedia.getDrawable();
            Bitmap icon = drawable.getBitmap();

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");

            Uri uri = Uri.parse("https://goo.gl/1UgQVz");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
            try {
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
            share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));


            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(android.content.Intent.EXTRA_SUBJECT, "My " +
                    "Subject");
            /*share.putExtra(Intent.EXTRA_STREAM,String.valueOf(uri));*/
            share.putExtra(android.content.Intent.EXTRA_TEXT,"Shared via: https://goo.gl/1UgQVz");
            share.putExtra(Intent.EXTRA_ORIGINATING_URI,"https://goo.gl/1UgQVz");

            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivityForResult(Intent.createChooser(share, "Share Your Video"),1);


        }

        }

});

holder.deletebtn.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        File file = new File(String.valueOf(currentFile.getAbsoluteFile()));
        if(file.exists())
        {
            file.delete();
            Toast.makeText(activity, "deleted", Toast.LENGTH_SHORT).show();
            filesList.remove(position);
            filesListdates.remove(position);

            notifyItemRemoved(position);
            notifyItemRangeChanged(position,filesList.size());
        }
return true;
    }
});
    }




    @Override
    public int getItemCount() {
        return filesList.size();

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

                            Log.d("", "onClickiiii:  "+DIRECTORY_TO_SAVE_MEDIA_NOW);

                            copyFile(sourceFile, new File(DIRECTORY_TO_SAVE_MEDIA_NOW +sourceFile.getName()));
                            Snacky.builder().
                                    setActivty(activity).
                                    setText(R.string.save_successful_message).
                                    success().
                                    show();//
                            //Toast.makeText(activity,R.string.save_successful_message,Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("RecyclerV", "onClick: Error:"+e.getMessage() );

                            Snacky.builder().
                                    setActivty(activity).
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


    public static class FileHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout mylayout;
        ImageView imageViewImageMedia;
        TextView time;
        VideoView videoViewVideoMedia;
        TextView dates;
        ImageView sharebtn,deletebtn,imagebtn,playbtn;
        /* CardView cardViewVideoMedia;
         CardView cardViewImageMedia;*/
        Button buttonVideoDownload;
        ImageView buttonImageDownload;

        public FileHolder(View itemView) {
            super(itemView);
            imageViewImageMedia = (ImageView) itemView.findViewById(R.id.list_avatar);
            playbtn = (ImageView) itemView.findViewById(R.id.playbtn);
            imagebtn = (ImageView) itemView.findViewById(R.id.pic);
            sharebtn = (ImageView) itemView.findViewById(R.id.share);
            deletebtn = (ImageView) itemView.findViewById(R.id.deleteb);
            dates= (TextView) itemView.findViewById(R.id.list_title);
            mylayout= (RelativeLayout) itemView.findViewById(R.id.layout1);
            time= (TextView) itemView.findViewById(R.id.list_desc);
            /*videoViewVideoMedia = (VideoView) itemView.findViewById(R.id.videoViewVideoMedia);
            cardViewVideoMedia = (CardView) itemView.findViewById(R.id.cardViewVideoMedia);
            cardViewImageMedia = (CardView) itemView.findViewById(R.id.cardViewImageMedia);
            buttonImageDownload = (ImageView) itemView.findViewById(R.id.buttonImageDownload);
            buttonVideoDownload = (Button) itemView.findViewById(R.id.buttonVideoDownload);*/

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

    }
}

