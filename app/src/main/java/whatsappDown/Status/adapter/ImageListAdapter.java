package whatsappDown.Status.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import de.mateware.snacky.Snacky;
import whatsappDown.Status.ImageSlider;
import whatsappDown.Status.Permissions.SingleMediaScanner;
import whatsappDown.Status.R;

/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 06-May-17
 * 8:24 AM
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.FileHolder> {

    public static final String MyPREFERENCES = "MyPrefs";

    SharedPreferences sharedpreferences;
    public  SharedPreferences.Editor editor;
    String counterprs="counter";
    int counter=0;

    private static String DIRECTORY_TO_SAVE_MEDIA_NOW = "/storage/emulated/Pictures/WSDownloader/";
    private ArrayList<File> filesList;
    private ArrayList<String> filesListdates;
    private Activity activity;

    public ImageListAdapter(ArrayList<File> filesList, Activity activity, ArrayList<String> filesListdates) {
        this.filesList = filesList;
        this.activity = activity;
        this.filesListdates = filesListdates;
    }

    @Override
    public ImageListAdapter.FileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mylistview, parent, false);
        return new FileHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(final ImageListAdapter.FileHolder holder, final int position) {
        File currentFile = filesList.get(position);
        final String currentdates= filesListdates.get(position);
        String date = null,time = null;



         date = currentdates.substring(0, Math.min(currentdates.length(), 10));
         time = currentdates.substring(10, Math.min(currentdates.length(), currentdates.length()));


//        holder.buttonImageDownload.setOnClickListener(this.downloadMediaItem(currentFile));
//        holder.buttonVideoDownload.setOnClickListener(this.downloadMediaItem(currentFile));
        final Bitmap myBitmap = BitmapFactory.decodeFile(currentFile.getAbsolutePath());
        holder.imageViewImageMedia.setImageBitmap(myBitmap);
        holder.dates.setText("Image");
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
                        Intent i = new Intent(activity,ImageSlider.class);
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
                        //Toast.makeText(activity, ""+filesList.get(position), Toast.LENGTH_SHORT).show();
                        // Toast.makeText(activity, "clicked"+position, Toast.LENGTH_SHORT).show();
                        String myPath = String.valueOf(filesList.get(position));
                        Intent i = new Intent(activity, ImageSlider.class);
                        //  i.putExtra("Image", myPath);
                        i.putExtra("Image", position);
                        activity.startActivity(i);

                    }
                }
            });

        File myfile= new File(String.valueOf(filesList.get(position)));

        holder.downloadimage.setOnClickListener(this.downloadMediaItem(myfile));
        }
    public void setListItems(ArrayList<File> imagepath,ArrayList<String> imagedates) {
        filesListdates = imagedates;
        filesList = imagepath;
        notifyDataSetChanged();
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
                            //String DIRECTORY_TO_SAVE_MEDIA_NOW = "/storage/emulated/legacy/"+"/WhatsApp/Media/.Statuses/Saved/";
/*
                            String DIRECTORY_TO_SAVE_MEDIA_NOW = "/storage/emulated/0/WhatsApp/Media/.Statuses/Saved/";
*/

                            String DIRECTORY_TO_SAVE_MEDIA_NOW = "/storage/emulated/0/DCIM/StatusDownloader/";

                            Log.d("Hello", "onClick: "+DIRECTORY_TO_SAVE_MEDIA_NOW);


                            copyFile(sourceFile, new File(DIRECTORY_TO_SAVE_MEDIA_NOW +sourceFile.getName()));


                            Snacky.builder().
                                    setActivty(activity).
                                    setText(R.string.save_successful_message).setBackgroundColor(activity.getResources().getColor(R.color.footercolor)).
                                    success().
                                    show();//
                            new SingleMediaScanner(activity, new File(DIRECTORY_TO_SAVE_MEDIA_NOW +sourceFile.getName()));

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
        ImageView downloadimage;
       /* CardView cardViewVideoMedia;
        CardView cardViewImageMedia;*/
        Button buttonVideoDownload;
        ImageView buttonImageDownload;

        public FileHolder(View itemView) {
            super(itemView);
            imageViewImageMedia = (ImageView) itemView.findViewById(R.id.list_avatar);
            downloadimage = (ImageView) itemView.findViewById(R.id.downloadmyimagelist);
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
