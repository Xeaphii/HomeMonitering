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


public class Dryer extends ActionBarActivity {

    SeekBar DryerUsageHours;
    EditText DryerPowerRating;
    Spinner DryerType, DryerEnergyTicks;
    TextView DryerAnnualEnergyCostResults, DryerDailyEnergyCostResults, DryerSelectedUsageHours, DryerResults1, DryerResults2;
    Button DryerExcelGraph, DryerRealTimeGraph;
    ImageButton Help;

    List<String> DryerCondenserEnergyTicksList, DryerVentEnergyTicksList;
    ArrayAdapter<String> dataAdapter2;
    //Prices and Power Consumption
    int[] DryerCondenserPrice = {78, 54, 35, 31};
    int[] DryerCondenserPowerConsumption = {209, 199, 129, 116};
    int[] DryerVentPrice = {65, 45};
    int[] DryerVentPowerConsumption = {241, 166};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dryer);

        DryerUsageHours = (SeekBar) findViewById(R.id.sbDryerUsageHours);
        DryerPowerRating = (EditText) findViewById(R.id.txtDryerPower);
        DryerType = (Spinner) findViewById(R.id.spDryerType);
        DryerEnergyTicks = (Spinner) findViewById(R.id.spDryerEnergyTicks);
        DryerAnnualEnergyCostResults = (TextView) findViewById(R.id.txtDryerAnnualEnergyCostResults);
        DryerDailyEnergyCostResults = (TextView) findViewById(R.id.txtDryerDailyEnergyCostResults);
        DryerSelectedUsageHours = (TextView) findViewById(R.id.txtDryerSelectedUsageHours);
        DryerResults1 = (TextView) findViewById(R.id.txtDryerResults1);
        DryerResults2 = (TextView) findViewById(R.id.txtDryerResults2);
        DryerExcelGraph = (Button) findViewById(R.id.btnDryerExcelGraph);
        DryerRealTimeGraph = (Button) findViewById(R.id.btnDryerRealTimeGraph);
        Help = (ImageButton) findViewById(R.id.btnDryerHelp);

        //Add 2 Dryer types to Spinner/Dropdown List
        List<String> DryerTypeList = new ArrayList<String>();
        DryerTypeList.add("Condenser");
        DryerTypeList.add("Vent");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, DryerTypeList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DryerType.setAdapter(dataAdapter1);

        //Create string list for ticks
        DryerCondenserEnergyTicksList = new ArrayList<String>();
        DryerCondenserEnergyTicksList.add("✔");
        DryerCondenserEnergyTicksList.add("✔✔");
        DryerCondenserEnergyTicksList.add("✔✔✔✔");
        DryerCondenserEnergyTicksList.add("✔✔✔✔✔");

        DryerVentEnergyTicksList = new ArrayList<String>();
        DryerVentEnergyTicksList.add("✔");
        DryerVentEnergyTicksList.add("✔✔");

        //Change ticks displayed base on selected tv type
        DryerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(DryerType.getSelectedItem().toString().equals("Vent")){
                    dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, DryerVentEnergyTicksList);
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    DryerEnergyTicks.setAdapter(dataAdapter2);
                } else{
                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, DryerCondenserEnergyTicksList);
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    DryerEnergyTicks.setAdapter(dataAdapter2);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        //Change ticks displayed base on selected tv type
        DryerEnergyTicks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(DryerType.getSelectedItem().toString().equals("Vent")){
                    DryerAnnualEnergyCostResults.setText("S$"+Integer.valueOf(DryerVentPrice[position]).toString());
                    Double DailyCost = (double)(Integer.valueOf(DryerVentPrice[position]))/365;
                    DryerDailyEnergyCostResults.setText("S$"+String.format("%.2f",DailyCost));

                } else{
                    DryerAnnualEnergyCostResults.setText("S$"+Integer.valueOf(DryerCondenserPrice[position]).toString());
                    Double DailyCost = (double)(Integer.valueOf(DryerCondenserPrice[position]))/365;
                    DryerDailyEnergyCostResults.setText("S$"+String.format("%.2f",DailyCost));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        //Calculate cost of usage base oh hours and power
        DryerUsageHours.incrementProgressBy(1);
        DryerUsageHours.setMax(23);
        DryerUsageHours.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int HoursUsed = seekBar.getProgress();
                DryerSelectedUsageHours.setText(String.valueOf(HoursUsed));

                //Calculate and update cost
                if(!DryerPowerRating.getText().toString().isEmpty()){
                    double Daily = 0.0;
                    double Annual = 0.0;

                    Daily = (Double.valueOf(DryerPowerRating.getText().toString())* HoursUsed)*0.27;
                    Annual = Daily * 365;
                    DryerResults1.setText("S$"+String.format("%.2f",Annual));
                    DryerResults2.setText("S$"+String.format("%.2f",Daily));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                DryerSelectedUsageHours.setText(String.valueOf(progress));
            }
        });

        DryerPowerRating.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                if(s.length() != 0){
                    double Daily = 0.0;
                    double Annual = 0.0;

                    Daily = (Double.valueOf(DryerPowerRating.getText().toString())* DryerUsageHours.getProgress())*0.27;
                    Annual = Daily * 365;
                    DryerResults1.setText("S$"+String.format("%.2f",Annual));
                    DryerResults2.setText("S$"+String.format("%.2f",Daily));
                }
            }
        });

        //On ExcelGraph Button Click
        DryerExcelGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open new Chart Activity
                Intent intent = new Intent(getApplicationContext(), OfflineChart.class);
                intent.putExtra("FileName", "dryerpowerreading.xls");
                startActivity(intent);
            }
        });

        //On RealTimeGraph Button Click
        DryerRealTimeGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open new Chart Activity
                Intent intent = new Intent(getApplicationContext(), DynamicGraphActivity.class);
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
