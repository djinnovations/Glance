package co.djphy.glance.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

import butterknife.BindView;
import co.djphy.glance.R;
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


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_register_president;
    }

    @Override
    protected void onGarbageCollection() {

    }

    @Override
    protected String getFragmentTitle() {
        return "Register Fragment";
    }
}
