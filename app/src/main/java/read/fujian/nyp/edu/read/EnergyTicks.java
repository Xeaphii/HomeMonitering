package read.fujian.nyp.edu.read;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class EnergyTicks extends ActionBarActivity {

    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_ticks);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width =dm.widthPixels;
        int height= dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.8));


        mWebview = (WebView)findViewById(R.id.wvEnergyTicks);
        WebSettings webSettings= mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebview.loadUrl("http://www.nea.gov.sg/energy-waste/energy-efficiency/household-sector/the-energy-label");
    }
}
