package com.Rapid.id.ImageSlider;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.Rapid.id.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import io.isfaaghyth.rak.Rak;

public class ImageSliderActivity extends AppCompatActivity {


    CarouselView carouselView;
    int[] gambarSlide = {R.drawable.banersatu, R.drawable.banerdua};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lay_jumbotron);

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(gambarSlide.length);

        carouselView.setImageListener(imageListener);




    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(gambarSlide[position]);


        }
    };
}
