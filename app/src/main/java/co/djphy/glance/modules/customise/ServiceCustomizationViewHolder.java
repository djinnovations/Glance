package co.djphy.glance.modules.customise;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial;
import com.rey.material.widget.CircleCheckedTextView;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import co.djphy.glance.MyApplication;
import co.djphy.glance.R;
import co.djphy.glance.uiutils.ResourceReader;

public class ServiceCustomizationViewHolder extends RecyclerView.ViewHolder {

    private final TextView name;
    private final TextView extra;
    //private final ImageView image;
    private ImageView addRemoveButton;
    private View separator;
    private LinearLayout extraLayout;

    private Map.Entry<OptionKey, ArrayList<OptionValue>> data;
    MyApplication.MenuSelectionListener menuSelectionListener;
    IconicsDrawable plusDrawable;
    IconicsDrawable minusDrawable;
    ResourceReader resourceReader;

    public ServiceCustomizationViewHolder(View itemView, MyApplication.MenuSelectionListener menuSelectionListener) {
        super(itemView);
        this.menuSelectionListener = menuSelectionListener;
        name = (TextView) itemView.findViewById(R.id.name);
        extra = (TextView) itemView.findViewById(R.id.extra);
        extra.setVisibility(View.VISIBLE);
        extra.setText("0");
        //image = (ImageView) itemView.findViewById(R.id.image);
        addRemoveButton = itemView.findViewById(R.id.ivAddRemove);
        separator = itemView.findViewById(R.id.separator);
        extraLayout = (LinearLayout) itemView.findViewById(R.id.extra_layout);
        resourceReader = ResourceReader.getInstance();
        plusDrawable = new IconicsDrawable(itemView.getContext())
                .icon(GoogleMaterial.Icon.gmd_add_circle)
                .color(com.mikepenz.iconics.IconicsColor
                        .colorInt(resourceReader
                                .getColorFromResource(R.color.colorMaterialBlack)));
        minusDrawable = new IconicsDrawable(itemView.getContext())
                .icon(GoogleMaterial.Icon.gmd_remove_circle)
                .color(com.mikepenz.iconics.IconicsColor
                        .colorInt(resourceReader
                                .getColorFromResource(R.color.colorMaterialBlack)));
        addRemoveButton.setImageDrawable(plusDrawable);
        addRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extraLayout.getVisibility() == View.VISIBLE) {
                    addRemoveButton.setImageDrawable(plusDrawable);
                    extraLayout.setVisibility(View.GONE);
                } else {
                    addRemoveButton.setImageDrawable(minusDrawable);
                    extraLayout.setVisibility(View.VISIBLE);
                    populateExtraLayout();
                }
            }
        });
    }

    public void bindUI(Map.Entry<OptionKey, ArrayList<OptionValue>> option) {
        data = option;
        name.setText(option.getKey().getDisplayString());
    }

    private Set<OptionValue> primarySelected = new HashSet<>();

    public Set<OptionValue> getSelectedList(){
        return new HashSet<>(primarySelected);
    }

    private int count;

    private View.OnClickListener mItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //todo dispatch click event;
            CheckBox checkBox = v.findViewById(R.id.checkbox);
            if (checkBox.isChecked()){
                checkBox.setChecked(false);
                count--;
            }else {
                checkBox.setChecked(true);
                count++;
            }
            extra.setText(String.valueOf(count));
        }
    };


    private void populateExtraLayout() {
        List<OptionValue> list = data.getValue();
        for (int i = 0; i < list.size(); i++) {
            OptionValue optionValue = list.get(i);
            View child = extraLayout.getChildAt(i);
            View rootView = LayoutInflater.from(extraLayout.getContext())
                    .inflate(R.layout.item_customize_type, extraLayout, false);
            rootView.setTag(optionValue);
            //rootView.setOnClickListener(mItemClick);
            CheckBox checkBox = rootView.findViewById(R.id.checkbox);
            checkBox.setTag(optionValue);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
                    //mItemClick.onClick(rootView);
                    if (!bool){
                        count--;
                        primarySelected.remove((OptionValue) compoundButton.getTag());
                    }else {
                        count++;
                        primarySelected.add((OptionValue) compoundButton.getTag());
                    }
                    extra.setText(String.valueOf(count));
                    if (menuSelectionListener != null)
                        menuSelectionListener.onMenuSelected(primarySelected);
                }
            });
            TextView tv = (TextView) rootView.findViewById(R.id.name);
            /*CircleCheckedTextView image = (CircleCheckedTextView) v.findViewById(R.id.image);
            image.setText(s.getDisplayString());
            if(data.getKey().keyID.equalsIgnoreCase("Metal Purity List"))
                tv.setText(s.getDisplayString()+"K");
            else*/
                tv.setText(optionValue.getDisplayString());
            rootView.setVisibility(View.VISIBLE);
            if (child == null) {
                extraLayout.addView(rootView);
            } /*else {
                CheckBox checkBoxTemp = child.findViewById(R.id.checkbox);
                if (checkBoxTemp.isChecked())
                    primarySelected.add(optionValue);
            }*/
        }
        if (extraLayout.getChildCount() > list.size()) {
            for (int i = list.size(); i < extraLayout.getChildCount(); i++) {
                extraLayout.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }


}