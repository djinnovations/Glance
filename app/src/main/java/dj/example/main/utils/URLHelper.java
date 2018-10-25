package dj.example.main.utils;

/**
 * Created by DJphy on 28-09-2016.
 */
public class URLHelper {

    public static final String END_POINT = "https://abcdef.com/";


    private static URLHelper ourInstance;

    public static URLHelper getInstance(){
        if (ourInstance == null){
            ourInstance = new URLHelper();
        }
        return ourInstance;
    }

    private URLHelper(){

    }

    public static void clearInstance(){
        ourInstance = null;
    }

    private static final class VERB{

    }
}
