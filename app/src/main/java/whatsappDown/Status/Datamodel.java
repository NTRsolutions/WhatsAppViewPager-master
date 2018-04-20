package whatsappDown.Status;

import java.util.ArrayList;

/**
 * Created by navee on 2/4/2018.
 */

public class Datamodel {



    private String headerTitle;
    private ArrayList<String> allItemsInSection;


    public Datamodel() {

    }
    public Datamodel(String headerTitle, ArrayList<String> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<String> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<String> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}