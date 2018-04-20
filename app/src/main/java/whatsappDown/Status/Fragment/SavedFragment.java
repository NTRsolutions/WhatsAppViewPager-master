package whatsappDown.Status.Fragment;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import whatsappDown.Status.R;
import whatsappDown.Status.adapter.SavelistAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class SavedFragment extends Fragment {


    public static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
   // private static final String WHATSAPP_STATUSES_LOCATION = "/storage/emulated/legacy/pictures/Whatsapp Status/";
    private static final String WHATSAPP_STATUSES_LOCATION = "/DCIM/StatusDownloader/";
    private RecyclerView mRecyclerViewMediaList;
    private LinearLayoutManager mLinearLayoutManager;
    public static final String TAG = "Home";
    private SimpleDateFormat sdf;

    public static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 1;


    public SavedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean checkpersm=checkPermissionForReadExtertalStorage();
        Log.d("saved item directory",WHATSAPP_STATUSES_LOCATION);

        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_calls, container, false);
        mRecyclerViewMediaList = (RecyclerView) v.findViewById(R.id.my_recycler_view);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_calls_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
        SavedFragment.TaskPerform taskPerform=new  TaskPerform();
        taskPerform.execute();
    }

    private ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files;
        files = parentDir.listFiles();
        if (files==null){
            Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
        }
        else {

            Arrays.sort(files, new Comparator() {
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
                            file.getName().endsWith(".gif")|| file.getName().endsWith(".mp4")) {
                        if (!inFiles.contains(file))
                            inFiles.add(file);
                    }
                }
            }
        }
        return inFiles;
    }
    private ArrayList<String> getListFilesDates(File parentDir) {
        ArrayList<String> inFilesDates = new ArrayList<>();
        File[] files;
        files = parentDir.listFiles();

        if (files==null){
            Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
        }
        else {
            Arrays.sort(files, new Comparator() {
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
                            file.getName().endsWith(".gif")||file.getName().endsWith(".mp4")) {
                        if (!inFilesDates.contains(file))
                            file.lastModified();
                        String todaydate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                        String filedate = sdf.format(file.lastModified());


                   /* for (int i=0;i<=file.length();i++){
                        Datamodel dm = new Datamodel();

                        dm.setHeaderTitle("Today");

                    }*/


                        sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                        String dates = sdf.format(file.lastModified());
                        String filename = dates.substring(dates.lastIndexOf("/") + 1);
                        inFilesDates.add(filename);
                    }
                }
            }
        }
        return inFilesDates;
    }
    @Override
    public void onStart() {
        super.onStart();
       /* mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewMediaList.setLayoutManager(mLinearLayoutManager);
        System.out.println("......"+ Environment.getExternalStorageDirectory().toString());

        SavelistAdapter imageListAdapter = new SavelistAdapter(getListFiles(new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_STATUSES_LOCATION)), getActivity(),getListFilesDates(new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_STATUSES_LOCATION)));
        mRecyclerViewMediaList.setAdapter(imageListAdapter);
        int count = imageListAdapter.getItemCount();*/
        //  Toast.makeText(getActivity(), "this appear"+count, Toast.LENGTH_SHORT).show();
    }



    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.ratingimage:

                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }
break;
            case R.id.action_clear_log:
                showdialog();
            {

            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showdialog(){

        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);

        View view  = getActivity().getLayoutInflater().inflate(R.layout.rate_us, null);
        dialog.setContentView(view);
        dialog.setTitle("How to Use");

        TextView edit = (TextView) view.findViewById(R.id.whatsappopen);



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                    startActivity(launchIntent);

                } catch (ActivityNotFoundException e) {

                }
                //Do something

            }
        });




        dialog.show();
    };


    public class TaskPerform extends AsyncTask<Void,Void,ArrayList<File>> {

        SavelistAdapter imageListAdapter;
        @Override
        protected ArrayList<File> doInBackground(Void... voids) {
            ArrayList<File> mylist = null;
            try {



                imageListAdapter = new SavelistAdapter(getListFiles(new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_STATUSES_LOCATION)), getActivity(),getListFilesDates(new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_STATUSES_LOCATION)));


            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
            return mylist;
        }

        @Override
        protected void onPostExecute(ArrayList<File> files) {
            super.onPostExecute(files);
            mLinearLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerViewMediaList.setLayoutManager(mLinearLayoutManager);
            System.out.println("......"+ Environment.getExternalStorageDirectory().toString());
            mRecyclerViewMediaList.setAdapter(imageListAdapter);
        }
    }

}

