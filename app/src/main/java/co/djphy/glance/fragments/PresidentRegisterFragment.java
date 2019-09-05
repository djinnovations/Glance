package co.djphy.glance.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import co.djphy.glance.R;
import co.djphy.glance.activities.BaseActivity;
import co.djphy.glance.activities.NormalLoginActivity;
import co.djphy.glance.model.UserInfo;
import co.djphy.glance.utils.IntentKeys;
/*import in.madapps.placesautocomplete.PlaceAPI;
import in.madapps.placesautocomplete.PlaceAPI.Builder;
import in.madapps.placesautocomplete.adapter.PlacesAutoCompleteAdapter;
import in.madapps.placesautocomplete.listener.OnPlacesDetailsListener;
import in.madapps.placesautocomplete.model.Address;
import in.madapps.placesautocomplete.model.Place;
import in.madapps.placesautocomplete.model.PlaceDetails;*/

public class PresidentRegisterFragment extends PrimaryBaseFragment {

    String TAG = "PresidentRegisterFragment";

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_register_president;
    }

    @Override
    protected void onGarbageCollection() {
        TAG = null;
        place = null;
    }

    @Override
    protected String getFragmentTitle() {
        return "Register Fragment";
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*PlaceAPI placesApi = new Builder()
                .apiKey("AIzaSyD7ck6fgxF80yS7a22XH8A--jZl_owIfys").build(getActivity());
        //.apiKey("YOUR_API_KEY").build(getActivity());
        autoCompleteEditText.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), placesApi));*/

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.PHONE_NUMBER,
                Place.Field.ADDRESS, Place.Field.ID, Place.Field.NAME));
        autocompleteFragment.setCountry("IN");
        autocompleteFragment.setHint("Apartment name?");

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                PresidentRegisterFragment.this.place = place;
                Log.i(TAG, "Place: " + place.getName() + ", "
                        + "Address: " + place.getAddress() + ", "
                        + "Phone: " + place.getPhoneNumber() + ", "
                        + "Place ID: "+place.getId());
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    private Place place;

    @BindView(R.id.btnRegisterAcct)
    Button btnRegisterAcct;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etEmailId)
    EditText etEmailId;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etPasswordConfirm)
    EditText etPasswordConfirm;


    @OnClick(R.id.btnRegisterAcct)
    void onRegisterClicked() {
        if (getActivity() instanceof NormalLoginActivity){
            String name = etUserName.getText().toString().trim();
            String email = etEmailId.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            UserInfo info = new UserInfo(name, email, password, this.place);
            ((NormalLoginActivity) getActivity()).onRegisterClickedDelegate(info);
        }
    }

    private boolean canContinue(){
        return !(TextUtils.isEmpty(etEmailId.getText().toString())
                || TextUtils.isEmpty(etUserName.getText().toString())
                || TextUtils.isEmpty(etPassword.getText().toString())
                || TextUtils.isEmpty(etPasswordConfirm.getText().toString()));
    }

    private boolean validateEmail() {
        String email = etUserName.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            ((BaseActivity) getActivity()).setWarningMsg("Invalid Email Address");
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
