package whatsappDown.Status.Permissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Created by navee on 3/12/2018.
 */

public class SortArrayList {




    public static ArrayList<String> getListFilesDates(File parentDir) {
        ArrayList<String> inFilesDates = new ArrayList<>();
        File[] files;
        files = parentDir.listFiles();

/*
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
        });*/

        if (files != null) {
            for (File file : files) {


                if (file.getName().endsWith(".jpg") ||
                        file.getName().endsWith(".gif")) {
                    if (!inFilesDates.contains(file))
                        file.lastModified();
                    String todaydate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                    String filedate= sdf.format(file.lastModified());


                   /* for (int i=0;i<=file.length();i++){
                        Datamodel dm = new Datamodel();

                        dm.setHeaderTitle("Today");

                    }*/




                    sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                    String dates= sdf.format(file.lastModified());
                    String filename=dates.substring(dates.lastIndexOf("/")+1);
                    inFilesDates.add(filename);
                }
            }
        }
        return inFilesDates;
    }


    public static ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
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
                        file.getName().endsWith(".gif") ) {
                    if (!inFiles.contains(file))
                        inFiles.add(file);
                }
            }
        }
        return inFiles;
    }


    public static ArrayList<String> getListFilesimageSlider(File parentDir) {
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
                        file.getName().endsWith(".gif") ) {
                    if (!inFiles.contains(file))
                        inFiles.add(String.valueOf(file));
                }
            }
        }
        return inFiles;
    }


}
