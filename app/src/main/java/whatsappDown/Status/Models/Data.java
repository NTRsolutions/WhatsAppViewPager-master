package whatsappDown.Status.Models;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by navee on 3/16/2018.
 */

public class Data {

    ArrayList<File> imagepaths;
    ArrayList<String> imagedates;

    public Data() {
    }

    public Data(ArrayList<File> imagepaths, ArrayList<String> imagedates) {
        this.imagepaths = imagepaths;
        this.imagedates = imagedates;
    }

    public ArrayList<File> getImagepaths() {
        return imagepaths;
    }

    public void setImagepaths(ArrayList<File> imagepaths) {
        this.imagepaths = imagepaths;
    }

    public ArrayList<String> getImagedates() {
        return imagedates;
    }

    public void setImagedates(ArrayList<String> imagedates) {
        this.imagedates = imagedates;
    }
}
