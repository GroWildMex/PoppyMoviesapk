package com.poppymovies.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isFireTv = "firetv".equals(BuildConfig.FLAVOR);
        setContentView(isFireTv ? R.layout.activity_welcome_firetv : R.layout.activity_welcome);

        TextView popcorn = findViewById(R.id.popcornEmoji);
        TextView title = findViewById(R.id.titleText);
        LinearLayout bottomSection = findViewById(R.id.bottomSection);
        Button btn = findViewById(R.id.btnContinue);

        // Start everything invisible
        popcorn.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        bottomSection.setVisibility(View.INVISIBLE);
        btn.setVisibility(View.INVISIBLE);

        Handler handler = new Handler(Looper.getMainLooper());

        // 1. Popcorn appears with scale
        handler.postDelayed(() -> {
            popcorn.setVisibility(View.VISIBLE);
            ScaleAnimation scale = new ScaleAnimation(
                    0.3f, 1.0f, 0.3f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            scale.setDuration(700);
            popcorn.startAnimation(scale);
        }, 300);

        // 2. Title fades in
        handler.postDelayed(() -> {
            title.setVisibility(View.VISIBLE);
            AlphaAnimation fade = new AlphaAnimation(0f, 1f);
            fade.setDuration(800);
            title.startAnimation(fade);
        }, 1100);

        // 3. Bottom section appears
        handler.postDelayed(() -> {
            bottomSection.setVisibility(View.VISIBLE);
            AlphaAnimation fade = new AlphaAnimation(0f, 1f);
            fade.setDuration(700);
            bottomSection.startAnimation(fade);
        }, 2000);

        // 4. Continue button appears
        handler.postDelayed(() -> {
            btn.setVisibility(View.VISIBLE);
            AlphaAnimation fade = new AlphaAnimation(0f, 1f);
            fade.setDuration(600);
            btn.startAnimation(fade);
            btn.requestFocus();
        }, 2800);

        btn.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
    }
}
