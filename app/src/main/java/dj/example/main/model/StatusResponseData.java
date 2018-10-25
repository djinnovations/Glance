package dj.example.main.model;

import android.graphics.Color;

import dj.example.main.R;
import dj.example.main.uiutils.ResourceReader;

/**
 * Created by CSC on 9/12/2018.
 */

public class StatusResponseData {

    public String user;
    public String material;
    public String status;
    public long ts;


    public int getStatusColor(ResourceReader resourceReader){
        if (status.equalsIgnoreCase("About to finish")){
            return resourceReader.getColorFromResource(R.color.darkorange);
        }
        if (status.equalsIgnoreCase("Full")){
            return resourceReader.getColorFromResource(R.color.greenStatus);
        }
        if (status.equalsIgnoreCase("Empty")){
            return resourceReader.getColorFromResource(R.color.redStatus);
        }
        return Color.BLACK;
    }
}
