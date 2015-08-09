package read.fujian.nyp.edu.read;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class TV extends ActionBarActivity {

    SeekBar UsageHours;
    EditText PowerRating;
    Spinner TVType, EnergyTicks;
    TextView AnnualEnergyCostResults, DailyEnergyCostResults, TVSelectedUsageHours, Results1, Results2;
    Button ExcelGraph, RealTimeGraph;
    ImageButton Help;

    List<String> LCDEnergyTicksList, OLEDEnergyTicksList;
    ArrayAdapter<String> dataAdapter2;
    //Prices and Power Consumption
    int[] LCDPrice = {63, 194, 158, 119, 26};
    int[] LCDPowerConsumption = {233, 717, 584, 440, 95};
    int[] OLEDPrice = {152, 102, 61};
    int[] OLEDPowerConsumption = {564, 378, 226};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        UsageHours = (SeekBar) findViewById(R.id.sbUsageHours);
        PowerRating = (EditText) findViewById(R.id.txtPower);
        TVType = (Spinner) findViewById(R.id.spTVType);
        EnergyTicks = (Spinner) findViewById(R.id.spEnergyTicks);
        AnnualEnergyCostResults = (TextView) findViewById(R.id.txtAnnualEnergyCostResults);
        DailyEnergyCostResults = (TextView) findViewById(R.id.txtDailyEnergyCostResults);
        TVSelectedUsageHours = (TextView) findViewById(R.id.txtTVSelectedUsageHours);
        Results1 = (TextView) findViewById(R.id.txtResults1);
        Results2 = (TextView) findViewById(R.id.txtResults2);
        ExcelGraph = (Button) findViewById(R.id.btnExcelGraph);
        RealTimeGraph = (Button) findViewById(R.id.btnRealTimeGraph);
        Help = (ImageButton) findViewById(R.id.btnTVHelp);


        //Add 2 TV types to Spinner/Dropdown List
        List<String> TVTypeList = new ArrayList<String>();
        TVTypeList.add("LCD - LED");
        TVTypeList.add("OLED");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, TVTypeList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TVType.setAdapter(dataAdapter1);

        //Create string list for ticks
        LCDEnergyTicksList = new ArrayList<String>();
        LCDEnergyTicksList.add("✔");
        LCDEnergyTicksList.add("✔✔");
        LCDEnergyTicksList.add("✔✔✔");
        LCDEnergyTicksList.add("✔✔✔✔");
        LCDEnergyTicksList.add("✔✔✔✔✔");

        OLEDEnergyTicksList = new ArrayList<String>();
        OLEDEnergyTicksList.add("✔✔");
        OLEDEnergyTicksList.add("✔✔✔");
        OLEDEnergyTicksList.add("✔✔✔✔");

        //Change ticks displayed base on selected tv type
        TVType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(TVType.getSelectedItem().toString().equals("OLED")){
                    dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, OLEDEnergyTicksList);
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    EnergyTicks.setAdapter(dataAdapter2);
                } else{
                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, LCDEnergyTicksList);
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    EnergyTicks.setAdapter(dataAdapter2);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        //Change ticks displayed base on selected tv type
        EnergyTicks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(TVType.getSelectedItem().toString().equals("OLED")){
                    AnnualEnergyCostResults.setText("S$"+Integer.valueOf(OLEDPrice[position]).toString());
                    Double DailyCost = (double)(Integer.valueOf(OLEDPrice[position]))/365;
                    DailyEnergyCostResults.setText("S$"+String.format("%.2f",DailyCost));
                    PowerRating.setText(String.valueOf(OLEDPowerConsumption[position]));

                } else{
                    AnnualEnergyCostResults.setText("S$"+Integer.valueOf(LCDPrice[position]).toString());
                    Double DailyCost = (double)(Integer.valueOf(LCDPrice[position]))/365;
                    DailyEnergyCostResults.setText("S$"+String.format("%.2f",DailyCost));
                    PowerRating.setText(String.valueOf(LCDPowerConsumption[position]));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        //Calculate cost of usage base oh hours and power
        UsageHours.incrementProgressBy(1);
        UsageHours.setMax(23);
        UsageHours.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int HoursUsed = seekBar.getProgress();
                TVSelectedUsageHours.setText(String.valueOf(HoursUsed));

                //Calculate and update cost when the user release the seekbar
                if(!PowerRating.getText().toString().isEmpty()){
                    double Daily = 0.0;
                    double Annual = 0.0;

                    Daily = (Double.valueOf(PowerRating.getText().toString())* UsageHours.getProgress())*0.27;
                    Annual = Daily * 365;
                    Results1.setText("S$"+String.format("%.2f",Annual));
                    Results2.setText("S$"+String.format("%.2f",Daily));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                //As the user is changing the seekbar value, it will display the current value
                TVSelectedUsageHours.setText(String.valueOf(progress));
            }
        });

        PowerRating.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                if(s.length() != 0){
                    double Daily = 0.0;
                    double Annual = 0.0;

                    Daily = (Double.valueOf(PowerRating.getText().toString())* UsageHours.getProgress())*0.27;
                    Annual = Daily * 365;
                    Results1.setText("S$"+String.format("%.2f",Annual));
                    Results2.setText("S$"+String.format("%.2f",Daily));
                }
            }
        });

        //On ExcelGraph Button Click
        ExcelGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open new Chart Activity
                Intent intent = new Intent(getApplicationContext(), OfflineChart.class);
                //Send the file name to the intent
                intent.putExtra("FileName", "tvpowerreading.xls");
                startActivity(intent);
            }
        });

        //On RealTimeGraph Button Click
        RealTimeGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Open DynamicGraphActivity Activity
                Intent intent = new Intent(getApplicationContext(), BluetoothPairedList.class);
                startActivity(intent);
            }
        });

        //On Help Button Click
        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Enter Text", Toast.LENGTH_LONG).show();
            }
        });
    }
}
