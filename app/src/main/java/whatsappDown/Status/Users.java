package whatsappDown.Status;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by navee on 3/12/2018.
 */

public class Users {

    ArrayList<File> imagepathfile;
    ArrayList<String>imagedatesfile;

    public Users() {
    }

    public Users(ArrayList<File> imagepathfile, ArrayList<String> imagedatesfile) {
        this.imagepathfile = imagepathfile;
        this.imagedatesfile = imagedatesfile;
    }

    public ArrayList<File> getImagepathfile() {
        return imagepathfile;
    }

    public void setImagepathfile(ArrayList<File> imagepathfile) {
        this.imagepathfile = imagepathfile;
    }

    public ArrayList<String> getImagedatesfile() {
        return imagedatesfile;
    }

    public void setImagedatesfile(ArrayList<String> imagedatesfile) {
        this.imagedatesfile = imagedatesfile;
    }
}
