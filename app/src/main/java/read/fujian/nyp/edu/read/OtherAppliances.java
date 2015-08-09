package read.fujian.nyp.edu.read;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class OtherAppliances extends ActionBarActivity {

    SeekBar OAUsageHours;
    EditText OAPowerRating;
    Spinner OAType;
    TextView OASelectedUsageHours, OAAnnual, OAMonthly, OADaily;
    Button Save, Efficiency, OARealTimeGraph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_appliances);

        OAUsageHours = (SeekBar) findViewById(R.id.sbOAUsageHours);
        OAPowerRating = (EditText) findViewById(R.id.txtOAPower);
        OAType = (Spinner) findViewById(R.id.spOAType);
        OASelectedUsageHours = (TextView) findViewById(R.id.txtOASelectedUsageHours);
        OAAnnual = (TextView) findViewById(R.id.txtOAAnnual);
        OAMonthly = (TextView) findViewById(R.id.txtOAMonthly);
        OADaily = (TextView) findViewById(R.id.txtOADaily);
        Save = (Button) findViewById(R.id.btnSave);
        Efficiency = (Button) findViewById(R.id.btnEfficiency);
        OARealTimeGraph = (Button) findViewById(R.id.btnOARealTimeGraph);

        //Add 2 TV types to Spinner/Dropdown List
        List<String> OAEnergyTicksList = new ArrayList<String>();
        OAEnergyTicksList.add("Water Heater");
        OAEnergyTicksList.add("Laptop");
        OAEnergyTicksList.add("Hair Dryer");
        OAEnergyTicksList.add("Desktop Computer (Gaming)");
        OAEnergyTicksList.add("Microwave");
        OAEnergyTicksList.add("Toaster Oven");
        OAEnergyTicksList.add("Iron");
        OAEnergyTicksList.add("Vacuum Cleaner");
        OAEnergyTicksList.add("Coffee Machine");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, OAEnergyTicksList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        OAType.setAdapter(dataAdapter1);

        //Calculate cost of usage base oh hours and power
        OAUsageHours.incrementProgressBy(1);
        OAUsageHours.setMax(23);
        OAUsageHours.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int HoursUsed = seekBar.getProgress();
                OASelectedUsageHours.setText(String.valueOf(HoursUsed));

                //Calculate and update cost
                if(!OAPowerRating.getText().toString().isEmpty()){
                    double Daily = 0.0;
                    double Monthly = 0.0;
                    double Annual = 0.0;

                    Daily = (Double.valueOf(OAPowerRating.getText().toString())* HoursUsed)*0.27;
                    Monthly = Daily * 30;
                    Annual = Daily * 365;
                    OAAnnual.setText("S$"+String.format("%.2f",Annual));
                    OAMonthly.setText("S$"+String.format("%.2f",Monthly));
                    OADaily.setText("S$"+String.format("%.2f",Daily));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {}
        });

        OAPowerRating.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                if(s.length() != 0){
                    double Daily = 0.0;
                    double Monthly = 0.0;
                    double Annual = 0.0;

                    Daily = (Double.valueOf(OAPowerRating.getText().toString())* OAUsageHours.getProgress())*0.27;
                    Monthly = Daily * 30;
                    Annual = Daily * 365;

                    OAAnnual.setText("S$"+String.format("%.2f",Annual));
                    OAMonthly.setText("S$"+String.format("%.2f",Monthly));
                    OADaily.setText("S$"+String.format("%.2f",Daily));
                }
            }
        });

        //On Save Button Click
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                if(!OADaily.getText().toString().isEmpty()){
                    sb.append(String.valueOf(OAType.getSelectedItem())+"\n");
                    sb.append(String.valueOf(OAUsageHours.getProgress())+"\n");
                    sb.append(OAPowerRating.getText().toString()+"\n");
                    sb.append(OAAnnual.getText().toString()+"\n");
                    sb.append(OAMonthly.getText().toString()+"\n");
                    sb.append(OADaily.getText().toString()+"\n");

                    // add-write text into file
                    try {
                        FileOutputStream fileout = openFileOutput(String.valueOf(OAType.getSelectedItem())+".txt", MODE_PRIVATE);
                        OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
                        outputWriter.write(sb.toString());
                        outputWriter.close();

                        //display file saved message
                        Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //On Efficiency Button Click
        Efficiency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open new Efficiency Activity
                Intent intent = new Intent(getApplicationContext(), Efficiency.class);
                startActivity(intent);
            }
        });

        //On RealTimeGraph Button Click
        OARealTimeGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open DynamicGraphActivity Activity
                Intent intent = new Intent(getApplicationContext(), DynamicGraphActivity.class);
                startActivity(intent);
            }
        });
    }
}
