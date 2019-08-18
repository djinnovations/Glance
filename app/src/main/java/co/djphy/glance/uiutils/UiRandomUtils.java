package co.djphy.glance.uiutils;

import android.content.res.Resources;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.android.gms.common.SignInButton;

import co.djphy.glance.MyApplication;

/**
 * Created by DJphy on 18-12-2016.
 */

public class UiRandomUtils {

    private static UiRandomUtils ourInstance;
    public static UiRandomUtils getInstance(){
        if (ourInstance == null)
            ourInstance = new UiRandomUtils();
        return ourInstance;
    }

    public static void clearInstance(){
        ourInstance = null;
    }

    public void startAnim(View view, int animResID){
        try {
            Animation anim = AnimationUtils.loadAnimation(MyApplication.getInstance(), animResID);
            view.startAnimation(anim);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    public void addSnapper(RecyclerView recyclerView, int gravity){
        SnapHelper snapHelper = new GravitySnapHelper(gravity);
        snapHelper.attachToRecyclerView(recyclerView);
    }

    /******************Customizing google login button*******************/
    public void setGooglePlusButtonText(String txt, SignInButton button) {
        for (int i = 0; i < button.getChildCount(); i++) {
            View v = button.getChildAt(i);
            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setTypeface(Typeface.SERIF, Typeface.BOLD);
                tv.setText(txt);
                tv.setGravity(Gravity.CENTER);
                return;
            }
        }
    }

    public void boldSomeTxt(@NonNull TextView textView, int start, int end) {
        SpannableStringBuilder str = new SpannableStringBuilder(textView.getText().toString());
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(str);
    }

}
