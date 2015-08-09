package read.fujian.nyp.edu.read;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Fridge extends ActionBarActivity {

    SeekBar PowerRating;
    Spinner FridgeType, FridgeEnergyTicks;
    TextView FridgeAnnualEnergyCostResults, FridgeDailyEnergyCostResults, SelectedPowerRating, FridgeResults1, FridgeResults2;
    Button FridgeExcelGraph, FridgeRealTimeGraph;
    ImageButton Help;

    List<String> FridgeEnergyTicksList, FridgeWithFreezerEnergyTicksList;
    ArrayAdapter<String> dataAdapter2;
    //Prices and Power Consumption
    int[] FridgePrice = {81, 87, 60};
    int[] FridgePowerConsumption = {300, 320, 225};
    int[] FridgeWithFreezerPrice = {240, 187, 127};
    int[] FridgeWithFreezerConsumption = {876, 694, 471};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        PowerRating = (SeekBar) findViewById(R.id.sbPowerRating);
        FridgeType = (Spinner) findViewById(R.id.spFridgeType);
        FridgeEnergyTicks = (Spinner) findViewById(R.id.spFridgeEnergyTicks);
        FridgeAnnualEnergyCostResults = (TextView) findViewById(R.id.txtFridgeAnnualEnergyCostResults);
        FridgeDailyEnergyCostResults = (TextView) findViewById(R.id.txtFridgeDailyEnergyCostResults);
        FridgeDailyEnergyCostResults = (TextView) findViewById(R.id.txtFridgeDailyEnergyCostResults);
        FridgeResults1 = (TextView) findViewById(R.id.txtFridgeResults1);
        FridgeResults2 = (TextView) findViewById(R.id.txtFridgeResults2);
        SelectedPowerRating = (TextView) findViewById(R.id.txtPowerRating);
        FridgeExcelGraph = (Button) findViewById(R.id.btnFridgeExcelGraph);
        FridgeRealTimeGraph = (Button) findViewById(R.id.btnFridgeRealTimeGraph);
        Help = (ImageButton) findViewById(R.id.btnFridgeHelp);

        //Add 2 Fridge types to Spinner/Dropdown List
        List<String> FridgeTypeList = new ArrayList<String>();
        FridgeTypeList.add("Fridge");
        FridgeTypeList.add("Fridge with Freezer");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, FridgeTypeList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FridgeType.setAdapter(dataAdapter1);

        //Create string list for ticks
        FridgeEnergyTicksList = new ArrayList<String>();
        FridgeEnergyTicksList.add("✔");
        FridgeEnergyTicksList.add("✔✔");
        FridgeEnergyTicksList.add("✔✔✔");

        FridgeWithFreezerEnergyTicksList = new ArrayList<String>();
        FridgeWithFreezerEnergyTicksList.add("✔");
        FridgeWithFreezerEnergyTicksList.add("✔✔");
        FridgeWithFreezerEnergyTicksList.add("✔✔✔");

        //Change ticks displayed base on selected tv type
        FridgeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(FridgeType.getSelectedItem().toString().equals("Fridge")){
                    dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, FridgeEnergyTicksList);
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    FridgeEnergyTicks.setAdapter(dataAdapter2);
                } else{
                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, FridgeWithFreezerEnergyTicksList);
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    FridgeEnergyTicks.setAdapter(dataAdapter2);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        //Change ticks displayed base on selected tv type
        FridgeEnergyTicks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(FridgeType.getSelectedItem().toString().equals("Fridge")){
                    FridgeAnnualEnergyCostResults.setText("S$"+Integer.valueOf(FridgePrice[position]).toString());
                    Double DailyCost = (double)(Integer.valueOf(FridgePrice[position]))/365;
                    FridgeDailyEnergyCostResults.setText("S$"+String.format("%.2f",DailyCost));

                } else{
                    FridgeAnnualEnergyCostResults.setText("S$"+Integer.valueOf(FridgeWithFreezerPrice[position]).toString());
                    Double DailyCost = (double)(Integer.valueOf(FridgeWithFreezerPrice[position]))/365;
                    FridgeDailyEnergyCostResults.setText("S$"+String.format("%.2f",DailyCost));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        //Calculate cost of usage base oh hours and power
        PowerRating.incrementProgressBy(1);
        PowerRating.setMax(1000);
        PowerRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int selectedPower = seekBar.getProgress();
                SelectedPowerRating.setText(String.valueOf(selectedPower));
                double Daily = 0.0;
                double Annual = 0.0;
                Daily = Double.valueOf(selectedPower)*0.27;
                Annual = Daily * 365;
                FridgeResults1.setText("S$"+String.format("%.2f",Annual));
                FridgeResults2.setText("S$"+String.format("%.2f",Daily));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                SelectedPowerRating.setText(String.valueOf(progress));
            }
        });


        //On ExcelGraph Button Click
        FridgeExcelGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open new Chart Activity
                Intent intent = new Intent(getApplicationContext(), OfflineChart.class);
                intent.putExtra("FileName", "fridgepowerreading.xls");
                startActivity(intent);
            }
        });

        //On RealTimeGraph Button Click
        FridgeRealTimeGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open DynamicGraphActivity Activity
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
