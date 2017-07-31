package dj.example.main.activities;

import android.app.Application;
import android.os.Handler;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dj.example.main.R;
import dj.example.main.fragments.HomePage;
import dj.example.main.model.NavigationDataObject;

/**
 * Created by DJphy on 28-09-2016.
 */
public class MyApplication extends Application {

    public static final String API_KEY_TW = "";
    public static final String API_SECRET_TW = "";
    public static final String OAUTH_WEBCLIENT_ID_GL = "1098955210882-p9dhi34jaj4bjgd2fpsf9d2lf5rf7qd2.apps.googleusercontent.com";
    public static final String ERR_MSG_NETWORK = "network ERR";


    private static MyApplication ourInstance;
    public static MyApplication getInstance(){
        return ourInstance;
    }

    public interface MenuSelectionListener {
        void onMenuSelected(Object data);
    }

    private Handler uiHandler;
    public Handler getUiHandler() {
        return uiHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        uiHandler = new Handler();
        setMenuItems();
    }


    private Map<Integer, NavigationDataObject> menuItems = new HashMap<>();
    public NavigationDataObject getNavigationObj(Integer viewId){
        return menuItems.get(viewId);
    }
    private void setMenuItems(){
        menuItems.clear();
        menuItems.put(R.id.nav_menu_1, new NavigationDataObject(R.id.nav_menu_1, MainActivity.class, NavigationDataObject.ACTIVITY));
        menuItems.put(R.id.nav_menu_2, new NavigationDataObject(R.id.nav_menu_2, null, NavigationDataObject.LOGOUT));
        menuItems.put(R.id.nav_menu_3, new NavigationDataObject(R.id.nav_menu_3, null, NavigationDataObject.RATE_US));
        menuItems.put(R.id.nav_menu_4, new NavigationDataObject(R.id.nav_menu_4, WebActivity.class, "titleofwebactivity"
                ,NavigationDataObject.ACTIVITY, "urlhere"));
        menuItems.put(R.id.nav_home, new NavigationDataObject(R.id.nav_home, HomePage.class, NavigationDataObject.FRAGMENT_VIEW));
    }


    public interface IFragmentWatcher {
        void onDestroy(Fragment var1);

        void onCreate(Fragment var1);
    }

    public interface IFragmentData {
        Class getView();

        String getID();

        String getName();

        Object getParam();

        void setParam(Object var1);

        Object getActionValue();

        String getActionType();
    }

}
