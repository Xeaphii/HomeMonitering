package read.fujian.nyp.edu.read;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class Efficiency extends ActionBarActivity {

    SeekBar PowerFactor;
    EditText MaxInputVoltage, MaxInputCurrent;
    TextView PowerFactorValue, EfficiencyValue, InputPower, OutputPower;
    Button Calculate;
    ImageButton Help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_efficiency);

        PowerFactor = (SeekBar) findViewById(R.id.sbPowerFactor);
        MaxInputVoltage = (EditText) findViewById(R.id.txtMaxInputVoltage);
        MaxInputCurrent = (EditText) findViewById(R.id.txtMaxInputCurrent);
        PowerFactorValue = (TextView) findViewById(R.id.txtPowerFactorValue);
        EfficiencyValue = (TextView) findViewById(R.id.txtEfficiencyValue);
        InputPower = (TextView) findViewById(R.id.txtInputPower);
        OutputPower = (TextView) findViewById(R.id.txtOutputPower);
        Calculate = (Button) findViewById(R.id.btnCalculate);
        Help = (ImageButton) findViewById(R.id.btnEfficiencyHelp);

        //Set Max value and increment of seek bar
        PowerFactor.incrementProgressBy(1);
        PowerFactor.setMax(10);
        PowerFactor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                double Powerfactor =  (Double.valueOf(seekBar.getProgress())/10);
                PowerFactorValue.setText(String.valueOf(Powerfactor));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {}
        });

        //On Save Button Click
        Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MaxInputVoltage.getText().toString().isEmpty()&& !MaxInputCurrent.getText().toString().isEmpty()){
                    Double Pout = 0.0;
                    Double Pin = 0.0;
                    Double EffPerc = 0.0;
                    Pout = Double.valueOf(MaxInputVoltage.getText().toString()) * Double.valueOf(MaxInputCurrent.getText().toString()) * (Double.valueOf(PowerFactor.getProgress())/10);
                    Pin = Double.valueOf(MaxInputVoltage.getText().toString()) * Double.valueOf(MaxInputCurrent.getText().toString());
                    InputPower.setText(String.valueOf(Pin));
                    OutputPower.setText(String.valueOf(Pout));
                    EffPerc = (Pout/Pin) * 100;
                    EfficiencyValue.setText(String.valueOf(EffPerc)+"%");
                }
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
