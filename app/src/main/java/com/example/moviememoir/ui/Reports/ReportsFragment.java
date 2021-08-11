package com.example.moviememoir.ui.Reports;

        import android.app.DatePickerDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Typeface;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.LinearLayout;
        import android.widget.Spinner;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.lifecycle.Observer;
        import androidx.lifecycle.ViewModelProviders;

        import com.example.moviememoir.R;
        import com.example.moviememoir.networkconnection.NetworkConnection;
        import com.example.moviememoir.sharedPreference.SharedPreferenceUtil;
        import com.github.mikephil.charting.charts.BarChart;
        import com.github.mikephil.charting.charts.PieChart;
        import com.github.mikephil.charting.components.Description;
        import com.github.mikephil.charting.components.Legend;
        import com.github.mikephil.charting.components.XAxis;
        import com.github.mikephil.charting.components.YAxis;
        import com.github.mikephil.charting.data.BarData;
        import com.github.mikephil.charting.data.BarDataSet;
        import com.github.mikephil.charting.data.BarEntry;
        import com.github.mikephil.charting.data.PieData;
        import com.github.mikephil.charting.data.PieDataSet;
        import com.github.mikephil.charting.data.PieEntry;
        import com.github.mikephil.charting.utils.ColorTemplate;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.Calendar;


public class ReportsFragment extends Fragment {
    LinearLayout linearLayout;
    private Button  barChart;
    private Button  pieChart;
    private String startingDate;
    private String endingDate;
    final ArrayList<String> location = new ArrayList<String>();
    final ArrayList<Integer> numberPerLocation = new ArrayList<Integer>();
    NetworkConnection connection = new NetworkConnection();
    private PieChart drawPie;
    private BarChart drawBar;
    private int pId;
    private String year;
    final ArrayList<String> monthName = new ArrayList<String>();
    final ArrayList<Integer> noPerMoth = new ArrayList<Integer>();
    final ArrayList<Integer> MonthNo = new ArrayList<Integer>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_reports, container, false);
        linearLayout = root.findViewById(R.id.report_content);
        barChart=root.findViewById(R.id.barchart);
        pieChart=root.findViewById(R.id.piechart);
        SharedPreferenceUtil spp = SharedPreferenceUtil.getInstance(getContext());
        pId = spp.getInt("pId");
        barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barChart();
            }
        });



        pieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChart();
            }
        });


        return root;
    }

    public void barChart(){
        LayoutInflater view2 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayout.removeAllViews();
        View vvvv = view2.inflate(R.layout.bar_chart, null);
        final Spinner sp = vvvv.findViewById(R.id.bar_selecY);
        final TextView tx = vvvv.findViewById(R.id.bar_startY);
        drawBar=vvvv.findViewById(R.id.Barchart);
        final Button confirm = vvvv.findViewById(R.id.bar_click);
         sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  year= (String)sp.getSelectedItem();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new drawBarD().execute("movie.memoir/task4_b_findNumMoviesPerMonth/"+pId + "/" + year);
            }
        });
        linearLayout.addView(vvvv);

    }

    public class drawBarD extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            return connection.getResults(strings[0],"");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList barStoere = new ArrayList();
            try{
                JSONArray jsonArray = new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    monthName.add(jsonObject.getString("MonthName"));
                    noPerMoth.add(jsonObject.getInt("numberOfMoviesPerMonth"));

                }
                Description description;
                Legend l;
                l=drawBar.getLegend();
                description=drawBar.getDescription();
                YAxis left = drawBar.getAxisLeft();
                YAxis right = drawBar.getAxisRight();
                XAxis bottom = drawBar.getXAxis();
              for(int p=0;p<monthName.size();p++){
                  if(monthName.get(p).equals("Mar")){
                      MonthNo.add(3);
                  }else if(monthName.get(p).equals("Jan")){
                      MonthNo.add(1);
                  }else if(monthName.get(p).equals("Feb")){
                      MonthNo.add(2);
                  }else if(monthName.get(p).equals("Apr")){
                      MonthNo.add(4);
                  }else if(monthName.get(p).equals("May")){
                      MonthNo.add(5);
                  }else if(monthName.get(p).equals("Jun")){
                      MonthNo.add(6);
                  }else if(monthName.get(p).equals("Jul")){
                      MonthNo.add(7);
                  }else if(monthName.get(p).equals("Aug")){
                      MonthNo.add(8);
                  }else if(monthName.get(p).equals("Sep")){
                      MonthNo.add(9);
                  }else if(monthName.get(p).equals("Oct")){
                      MonthNo.add(10);
                  }else if(monthName.get(p).equals("Dec")){
                      MonthNo.add(11);
                  }else{
                      MonthNo.add(12);
                  }

              }
              for(int yy=0;yy<MonthNo.size();yy++){
                  Log.e("yyyyyyyy",""+MonthNo.get(yy));
              }

             for(int oo=0;oo<MonthNo.size();oo++){
                 int iii = MonthNo.get(oo);
                 barStoere.add(new BarEntry(iii,noPerMoth.get(oo)));

             }
                BarDataSet barDataSet = new BarDataSet(barStoere,"the total number of movies watched per month for the selected year");
                BarData barData = new BarData(barDataSet);
                drawBar.setData(barData);
                drawBar.invalidate();
                drawBar.animateXY(3000,3000);

            }catch (Exception e){
                e.printStackTrace();

            }
        }
    }


    public void pieChart(){
        LayoutInflater view1 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayout.removeAllViews();
        final View vvv = view1.inflate(R.layout.pie_chart, null);
        final Button pickStart = vvv.findViewById(R.id.pick_start_date);
        final Button pickEnd =vvv.findViewById(R.id.pick_end_date);
        final Button click = vvv.findViewById(R.id.pick_click);
        drawPie = vvv.findViewById(R.id.Piechart);

        pickStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                     pickStart.setText(String.valueOf(month+1) + "/" + String.valueOf(dayOfMonth) + "/" + String.valueOf(year));
                     startingDate=String.valueOf(year)+"-"+String.valueOf(month+1) + "-" + String.valueOf(dayOfMonth);
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                dialog.show();
            }
        });

        pickEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                     pickEnd.setText(String.valueOf(month+1) + "/" + String.valueOf(dayOfMonth) + "/" + String.valueOf(year));
                     endingDate=String.valueOf(year)+"-"+String.valueOf(month+1) + "-" + String.valueOf(dayOfMonth);
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                dialog.show();
            }
        });
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("report Id",""+pId);
                Log.e("StartingDate",startingDate);
                Log.e("ending",endingDate);
                new searchNumPerlocation().execute("movie.memoir/task4_a_findNumMoviesPerPostcode/"+ String.valueOf(pId) + "/" + startingDate+ "/" +endingDate);
            }
        });
        linearLayout.addView(vvv);
    }

    public class searchNumPerlocation extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            return connection. getResults(strings[0],"");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList storePie = new ArrayList();
            try {
                JSONArray jsonArray = new JSONArray(s);
                for(int i=0; i< jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                     location.add(jsonObject.getString("location"));
                     numberPerLocation.add(jsonObject.getInt("numOfMovies"));
                     Log.e("nuooooooo",""+location.get(i));
                   }

                for(int l=0;l<location.size();l++){
                    int zz = numberPerLocation.get(l);
                    storePie.add(new PieEntry(zz,location.get(l)));
                }
                for(int r=0;r<storePie.size();r++){
                    Log.e("55555",storePie.get(r).toString());
                }
                PieDataSet dataset = new PieDataSet(storePie,"total number of movies watched per postcode");
                dataset.setColors(ColorTemplate.JOYFUL_COLORS);
                PieData data = new PieData(dataset);
                drawPie.setData(data);
                drawPie.animateXY(3000,3000);
                drawPie.setCenterTextSize(300);
                drawPie.invalidate();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


}
