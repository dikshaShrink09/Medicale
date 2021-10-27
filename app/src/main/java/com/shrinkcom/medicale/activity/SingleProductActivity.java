package com.shrinkcom.medicale.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.shrinkcom.medicale.R;

import java.util.ArrayList;

public class SingleProductActivity extends Fragment {
    private SliderLayout sliderShow;
    ImageView back;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_single_product, container, false);

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        getSupportActionBar().hide();*/
        sliderShow =view. findViewById(R.id.slider_product);
        back = view.findViewById(R.id.back);


        inflateImageSlider();

    return view;}
    private void inflateImageSlider() {
        {
            // Using Image Slider -----------------------------------------------------------------------
            sliderShow = view.findViewById(R.id.slider_product);
            //populating Image slider
            ArrayList<Integer> sliderImages = new ArrayList<>();
            sliderImages.add(R.drawable.dwai);
            sliderImages.add(R.drawable.category_image);
            sliderImages.add(R.drawable.intro_three);
            sliderImages.add(R.drawable.otc_product);

            for (Integer s : sliderImages) {
                DefaultSliderView sliderView = new DefaultSliderView(getContext());
                sliderView.image(s);
                sliderShow.addSlider(sliderView);
            }
            sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);

           /* sliderShow = view.findViewById(R.id.slider);
            for (String s : arraylist) {
                Log.e("SSSSS", "" + s);
                DefaultSliderView sliderView = new DefaultSliderView(mcontext);
                sliderView.image(s);
                sliderShow.addSlider(sliderView);
            }
            sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);*/

        }
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}