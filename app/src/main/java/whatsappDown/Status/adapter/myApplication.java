package whatsappDown.Status.adapter;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

import whatsappDown.Status.R;

/**
 * Created by navee on 3/18/2018.
 */

public class myApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id));
    }
}
