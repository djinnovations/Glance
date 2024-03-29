package co.djphy.glance.uiutils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marcoscg.dialogsheet.DialogSheet;

import java.util.ArrayList;
import java.util.List;

import co.djphy.glance.R;
import co.djphy.glance.MyApplication;


public class WindowUtils {

    private Context appContext;
    ViewConstructor mViewConstructor;
    DisplayProperties mDispProp;
    private static WindowUtils thisInstance;
    //private final String networkInfoMsg = "Please turn on your mobile DATA or WIFI";

    private WindowUtils(Context appContext) {
        this.appContext = appContext;
        mViewConstructor = ViewConstructor.getInstance(appContext);
        mDispProp = DisplayProperties.getInstance(DisplayProperties.ORIENTATION_PORTRAIT);
    }


    public static WindowUtils getInstance() {
        if (thisInstance == null) {
            thisInstance = new WindowUtils(MyApplication.getInstance());
        }
        return thisInstance;
    }

    public static void clearInstance(){
        thisInstance = null;
    }

    public void displayBottomSheetGenericMsg(Activity activity, String title, String msg,
                                             String positive, String negative,
                                             ViewConstructor.InfoDisplayListener infoDisplayListener){
        DialogSheet dialogSheet = new DialogSheet(activity)
                .setTitle(title)
                .setMessage(msg)
                //.setColoredNavigationBar(true)
                .setCancelable(true)
                .setPositiveButton(positive, new DialogSheet.OnPositiveClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Your action
                        infoDisplayListener.onPositiveSelection(null);
                    }
                })
                .setNegativeButton(negative, new DialogSheet.OnNegativeClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Your action
                        infoDisplayListener.onNegativeSelection(null);
                    }
                });
        //.setNeutralButton("Neutral", null)
        //.setBackgroundColor(Color.BLACK) // Your custom background color
        //.setButtonsColorRes(R.color.colorPrimary); // Default color is accent
        //.show();
        dialogSheet.show();
    }

    public void displayBottomSheetContactList(Activity activity){
        View view = LayoutInflater.from(activity.getApplicationContext())
                .inflate(R.layout.fragment_single_listview, null);
        ListView rvMenu = view.findViewById(R.id.listView);
        List<String> contacts = new ArrayList<>();
        contacts.add("+919845711889");
        contacts.add("+919980559750");
        contacts.add("+919845385366");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, contacts);
        rvMenu.setAdapter(adapter);
        rvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String number = contacts.get(position);
                intent.setData(Uri.parse("tel:"+number));
                activity.startActivity(intent);
            }
        });

        DialogSheet dialogSheet = new DialogSheet(activity)
                .setTitle("Contacts")
                //.setMessage("")
                //.setColoredNavigationBar(true)
                .setCancelable(false)
                .setPositiveButton("thanks", new DialogSheet.OnPositiveClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Your action
                        //infoDisplayListener.onPositiveSelection(null);
                    }
                });
                /*.setNegativeButton(negative, new DialogSheet.OnNegativeClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Your action
                    }
                });*/
        //.setNeutralButton("Neutral", null)
        //.setBackgroundColor(Color.BLACK) // Your custom background color
        //.setButtonsColorRes(R.color.colorPrimary); // Default color is accent
        //.show();
        dialogSheet.setView(view);
        dialogSheet.show();
    }

    public void displayBottomSheetDialog(Activity activity, String title,
                                         String positive, String negative,
                                         int layoutRsrId, View view,
                                         ViewConstructor.InfoDisplayListener infoDisplayListener){
        DialogSheet dialogSheet = new DialogSheet(activity)
                .setTitle(title)
                //.setMessage("")
                //.setColoredNavigationBar(true)
                .setCancelable(false)
                .setPositiveButton(positive, new DialogSheet.OnPositiveClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Your action
                        infoDisplayListener.onPositiveSelection(null);
                    }
                })
                .setNegativeButton(negative, new DialogSheet.OnNegativeClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Your action
                    }
                });
                //.setNeutralButton("Neutral", null)
                //.setBackgroundColor(Color.BLACK) // Your custom background color
                //.setButtonsColorRes(R.color.colorPrimary); // Default color is accent
                //.show();
                if (view == null)
                    dialogSheet.setView(layoutRsrId);
                else dialogSheet.setView(view);
                dialogSheet.show();
    }

    public void genericPermissionInfoDialog(Activity activity, String message) {
        mViewConstructor.displayInfo(activity, "Permission info", message, "OKAY", "",
                false, new ViewConstructor.InfoDisplayListener() {
                    @Override
                    public void onPositiveSelection(Dialog alertDialog) {
                        alertDialog.dismiss();
                    }

                    @Override
                    public void onNegativeSelection(Dialog alertDialog) {

                    }
                });
    }


    public void genericInfoMsgWithOkay(Activity activity, String title, String infoMsg, int colorInfoMsg) {
        mViewConstructor.displayInfo(activity, title, infoMsg, "Okay", colorInfoMsg);
    }

    public void genericInfoMsgWithOK(Activity activity, String title, String infoMsg, int colorInfoMsg) {
        mViewConstructor.displayInfo(activity, title, infoMsg, "OK", colorInfoMsg);
    }

    public void genericInfoMsgWithOKCallBack(Activity activity, String title, String infoMsg, int colorInfoMsg, String posBtnTxt,
                                             final ViewConstructor.InfoDisplayListener mInfoListener) {
        mViewConstructor.displayInfo(activity, title, infoMsg, posBtnTxt, colorInfoMsg, mInfoListener);
    }

    public void genericInfoMsgWithOKCallBack(Activity activity, String title, String infoMsg, int colorInfoMsg,
                                             final ViewConstructor.InfoDisplayListener mInfoListener){
        mViewConstructor.displayInfo(activity, title, infoMsg, "OK", colorInfoMsg, mInfoListener);
    }


    public void genericInfoMsgWithCallBack(Activity activity, String title, String infoMsg, int colorInfoMsg, String posBtnTxt,
                                           final ViewConstructor.InfoDisplayListener mInfoListener) {
        mViewConstructor.displayInfo(activity, title, infoMsg, posBtnTxt, colorInfoMsg, true, mInfoListener);
    }

    public void invokeCustomizationDialog(Activity activity, View view, final ViewConstructor.InfoDisplayListener mInfoListener){
        mViewConstructor.displayViewInfo(activity, "",
                view, "Send Request", true, mInfoListener);
    }

    public void invokeForgotPasswordDialog(Activity activity, final ViewConstructor.InfoDisplayListener mInfoListener) {
        final androidx.appcompat.app.AlertDialog dialog = mViewConstructor.displayViewRsdId(activity, "Forgot your password",
                R.layout.layout_edittext, "Send", true, mInfoListener);
        final EditText editText = (EditText) dialog.findViewById(R.id.etItem);
        final TextView textView = (TextView) dialog.findViewById(R.id.tvItem);
        editText.setHint("Email ID to receive a password");
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "";
                if (editText != null)
                    data = editText.getText().toString().trim();
                if (isValidEmail(data)) {
                    if (textView != null)
                        textView.setVisibility(View.GONE);
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTag(data);
                    mInfoListener.onPositiveSelection(dialog);
                } else {
                    if (textView != null) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("enter a valid email address");
                        textView.setTextColor(ResourceReader.getInstance().getColorFromResource(R.color.redStatus));
                    }
                }
            }
        });
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mInfoListener.onNegativeSelection(dialog);
            }
        });
    }


    boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static float dialogDimAmount = 0.0f;

    public Dialog displayDialogNoTitle(Context activityContext, View layout/*, String title*/) {

        Dialog dialog = new Dialog(activityContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*TextView alertTitle=(TextView) dialog.getWindow().getDecorView().findViewById(android.R.id.title);
        alertTitle.setBackgroundColor(new ResourceReader(activityContext).getColorFromResource(R.color.colorBlackDimText));
        alertTitle.setTextColor(Color.WHITE);
        alertTitle.setGravity(Gravity.CENTER);
        dialog.setTitle(title);*/

        WindowManager.LayoutParams tempParams = new WindowManager.LayoutParams();
        tempParams.copyFrom(dialog.getWindow().getAttributes());

		/*tempParams.width = dialogWidthInPx;
        tempParams.height = dialogHeightInPx;*/
        tempParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        tempParams.height = WindowManager.LayoutParams.MATCH_PARENT;

        tempParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        tempParams.dimAmount = dialogDimAmount;

        View tempView = layout;
        dialog.setContentView(tempView);
        dialog.setCancelable(false);

        dialog.getWindow().setAttributes(tempParams);
        //dialog.getWindow().setBackgroundDrawableResource(android.R.drawable.editbox_dropdown_dark_frame);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
/*		if(keyPadOnTop)
			dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);*/

        return dialog;
    }


    public static final int PROGRESS_FRAME_GRAVITY_TOP = 9001;
    public static final int PROGRESS_FRAME_GRAVITY_CENTER = 9003;
    public static final int PROGRESS_FRAME_GRAVITY_BOTTOM = 9004;


    public static boolean justPlainOverLay = false;
    public static int marginForProgressViewInGrid = 5;

    private void setGravity(View proView, int gravity) {
        RelativeLayout.LayoutParams rlParams = /*new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)*/ (RelativeLayout.LayoutParams) proView.getLayoutParams();
        if (gravity == PROGRESS_FRAME_GRAVITY_CENTER) {
            rlParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else if (gravity == PROGRESS_FRAME_GRAVITY_TOP) {
            rlParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            rlParams.topMargin = (int) (marginForProgressViewInGrid * mDispProp.getYPixelsPerCell());
        } else if ((gravity == PROGRESS_FRAME_GRAVITY_BOTTOM)) {
            rlParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            rlParams.bottomMargin = (int) (marginForProgressViewInGrid * mDispProp.getYPixelsPerCell());
        }
        proView.setLayoutParams(rlParams);
    }


    private void startAnim(View view, int animResID) throws Exception {
        Animation anim = AnimationUtils.loadAnimation(appContext, animResID);
        anim.setDuration(1200);
        view.startAnimation(anim);
    }


    public Dialog displayViewDialog(Activity activity, View cutomizationView) {
        return mViewConstructor.displayDialog(activity, cutomizationView);
    }


}
