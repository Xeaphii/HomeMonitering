package read.fujian.nyp.edu.read;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class QuickTips extends ActionBarActivity {

    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_tips);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width =dm.widthPixels;
        int height= dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.8));


        mWebview = (WebView)findViewById(R.id.wvQuickTips);
        WebSettings webSettings= mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebview.loadUrl("http://www.e2singapore.gov.sg/Households/At_Home_10_Energy_Challenge/EnergySaving_Habits/EnergySaving_Tips.aspx");
    }
}
