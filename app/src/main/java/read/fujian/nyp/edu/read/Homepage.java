package read.fujian.nyp.edu.read;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Homepage extends ActionBarActivity {

    Button QuickTips, EnergyInfo;
    ImageButton Fridge, WasherDryer, TV, OtherAppliances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        QuickTips = (Button) findViewById(R.id.btnQuickTips);
        EnergyInfo = (Button) findViewById(R.id.btnEnergyInfo);
        TV = (ImageButton) findViewById(R.id.btnTV);
        Fridge = (ImageButton) findViewById(R.id.btnFridge);
        WasherDryer = (ImageButton) findViewById(R.id.btnWasherDryer);
        OtherAppliances = (ImageButton) findViewById(R.id.btnOtherAppliances);

        //On QuickTips Button Click
        QuickTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open new QuickTips Activity
                startActivity(new Intent(getApplicationContext(), QuickTips.class));
            }
        });

        //On EnergyInfo Button Click
        EnergyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open new QuickTips Activity
                startActivity(new Intent(getApplicationContext(), EnergyTicks.class));
            }
        });

        //On TV Button Click
        TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open new TV Activity
                Intent intent = new Intent(getApplicationContext(), TV.class);
                startActivity(intent);
            }
        });

        //On Fridge Button Click
        Fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open new Fridge Activity
                Intent intent = new Intent(getApplicationContext(), Fridge.class);
                startActivity(intent);
            }
        });

        //On WasherDryer Button Click
        WasherDryer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open new Dryer Activity
                Intent intent = new Intent(getApplicationContext(), Dryer.class);
                startActivity(intent);
            }
        });

        //On OtherAppliances Button Click
        OtherAppliances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open new OtherAppliances Activity
                Intent intent = new Intent(getApplicationContext(), OtherAppliances.class);
                startActivity(intent);
            }
        });
    }
}
