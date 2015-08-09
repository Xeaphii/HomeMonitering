package read.fujian.nyp.edu.read;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;


public class OfflineChart extends ActionBarActivity {

    private XYPlot plot;
    String[][] data;
    String[] colTime;
    Number[] colPower;
    int length;
    String FileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_chart);

        //Get appliance type
        Intent intent = getIntent();
        FileName = intent.getStringExtra("FileName");

        //Read data from  excel file
        readExcelFile(this,FileName);

        //Split 2D array to individual arrays
        length = data.length-1;
        colTime = new String[length];
        colPower = new Number[length];
        for(int i = 1 ; i < length ; i++){
            colTime[i] = data[i][0];
        }
        for(int i = 1 ; i < length ; i++){
            colPower[i] = (Number)Double.valueOf(data[i][1]);
        }

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);

        // Domain
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, colTime.length);
        plot.setDomainValueFormat(new DecimalFormat("0"));
        plot.setDomainStepValue(1);

        //Range
        plot.setRangeBoundaries(0, 15, BoundaryMode.FIXED);
        plot.setRangeStepValue(10);
        plot.setRangeValueFormat(new DecimalFormat("0"));

        //Remove legend
        plot.getLayoutManager().remove(plot.getLegendWidget());
        plot.getLayoutManager().remove(plot.getDomainLabelWidget());
        plot.getLayoutManager().remove(plot.getRangeLabelWidget());
        plot.getGraphWidget().setSize(new SizeMetrics(0, SizeLayoutType.FILL,0, SizeLayoutType.FILL));

        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(colPower), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Power");
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getApplicationContext(),R.xml.line_point_formatter_with_plf1);
        plot.addSeries(series1, series1Format);

        // reduce the number of range labels
        //plot.setTicksPerRangeLabel(3);
        plot.getGraphWidget().setDomainLabelOrientation(-45);

    }

    class GraphXLabelFormat extends Format {
        @Override
        public StringBuffer format(Object arg0, StringBuffer arg1, FieldPosition arg2) {
            // TODO Auto-generated method stub

            int parsedInt = Math.round(Float.parseFloat(arg0.toString()));
            Log.d("test", parsedInt + " " + arg1 + " " + arg2);
            String labelString = colTime[parsedInt];
            arg1.append(labelString);
            return arg1;
        }

        @Override
        public Object parseObject(String arg0, ParsePosition arg1) {
            // TODO Auto-generated method stub
            return java.util.Arrays.asList(colTime).indexOf(arg0);
        }
    }

    public void readExcelFile(Context context, String filename) {

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()){
            return;
        }

        try{
            // Creating Input Stream
            final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
            FileInputStream myInput = new FileInputStream(file);

            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            int rowNum = mySheet.getLastRowNum() + 1 ;
            int colNum = mySheet.getRow(0).getLastCellNum() ;
            data = new String[rowNum][colNum] ;

            for  ( int i = 0 ; i < rowNum ; i++) {
                HSSFRow row = mySheet.getRow(i) ;
                for ( int j = 0 ; j < colNum ; j++) {
                    HSSFCell cell = row.getCell(j) ;
                    String value = cellToString(cell);
                    data[i][j] = value ;
                }
            }
        }catch (Exception e){e.printStackTrace(); }

        return;
    }

    //change cell to string
    public static String cellToString(HSSFCell cell) {
        int type ;
        Object result ;
        type = cell.getCellType() ;

        switch (type) {

            case 0 : // numeric value in Excel
                result = cell.getNumericCellValue() ;
                break ;
            case 1 : // String Value in Excel
                result = cell.getStringCellValue() ;
                break ;
            default :
                throw new RuntimeException("There are no support for this type of cell") ;
        }
        return result.toString() ;
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

}
