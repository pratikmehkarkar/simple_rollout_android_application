package in.baselinesoft.mahindraro;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class SplashScreen extends AppCompatActivity
{
    private static int SPLASH_TIME = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView imgv = (ImageView)findViewById(R.id.loader);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imgv);
        Glide.with(this).load(R.raw.loader).asGif().fitCenter().crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgv);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent m = new Intent(SplashScreen.this,MainNavigation.class);
                startActivity(m);
                finish();
            }
        },SPLASH_TIME);
    }
}

