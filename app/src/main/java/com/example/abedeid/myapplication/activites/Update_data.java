package com.example.abedeid.myapplication.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;


import com.example.abedeid.myapplication.R;
 import com.example.abedeid.myapplication.model.users;
import com.example.abedeid.myapplication.utils.Session;
import com.example.abedeid.myapplication.webservices.WebService;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Update_data extends AppCompatActivity {
    public static final String TAG = "RegisterActivityTAG";
    public static String yearData, departData, sectionData;
    @BindView(R.id.img_header_logo)
    ImageView imgHeaderLogo;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_username_udate)
    EditText etUsername;
    @BindView(R.id.et_password_udate)
    EditText etPassword;
    @BindView(R.id.et_repeat_password_udate)
    EditText etRepeatPassword;
    @BindView(R.id.lnlt_inputs_container)
    LinearLayout lnltInputsContainer;
    @BindView(R.id.tv_already_have_account)
    TextView tvAlreadyHaveAccount;
    @BindView(R.id.updateBtn_st)
    Button btnUpdateSt;

    @BindView(R.id.rllt_body)
    RelativeLayout rlltBody;
    @BindView(R.id.prgs_loading)
    ProgressBar prgsLoading;
    @BindView(R.id.rllt_loading)
    RelativeLayout rlltLoading;
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;
    @BindView(R.id.yaer_udate)
    Spinner _year;
    @BindView(R.id.depart_udate)
    Spinner _depart;
    @BindView(R.id.section_udate)
    Spinner _section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        users user= Session.getInstance().getUser();
        Log.d("TAGs",user.user_flage.toString());
        super.onCreate(savedInstanceState);
     if(user.user_flage.equals("4")){
        setContentView(R.layout.activity_update_data_st);
        ButterKnife.bind(this);
         initCustomSpinner();
         btnUpdateSt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {



                 final users s = new users();
                 s.id=Session.getInstance().getUser().id;
                 s.name = etUsername.getText().toString();
                 s.password = etPassword.getText().toString();
                 s.year = _year.getSelectedItem().toString();
                 s.section = _section.getSelectedItem().toString();
                 s.department = _depart.getSelectedItem().toString();

                  update_St_Data(s);


             }
         });




        }else {


         setContentView(R.layout.activity_update_data_dr);
         final EditText site=(EditText) findViewById(R.id.et_usersite_udate_dr);
         btnUpdateSt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {



                 final users s = new users();
                 s.id=Session.getInstance().getUser().id;
                 s.name = etUsername.getText().toString();
                 s.password = etPassword.getText().toString();
                s.user_site=site.getText().toString();

                 update_St_Data(s);


             }
         });




     }






    }
/*
*    WebService.getInstance().getApi().registerStudent(s).enqueue(new Callback<MainResponse>() {
*
*    */
    private void update_St_Data(users s) {

        WebService.getInstance().getApi().updateUser(s).enqueue(new Callback<List<users>>() {
            @Override
            public void onResponse(Call<List<users>> call, Response<List<users>> response) {
                Toast.makeText(Update_data.this, "updated ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<users>> call, Throwable t) {
                Toast.makeText(Update_data.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }

    List<String> section = new ArrayList<String>();
    List<String> depart = new ArrayList<String>();

    private void initCustomSpinner() {
        section.add("section");
        depart.add("depart");


        // Spinner Drop down elements
        final List<String> year = new ArrayList<String>();
        year.add("year");
        year.add("1");
        year.add("2");
        year.add("3");
        year.add("4");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, year);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        _year.setAdapter(dataAdapter);
        _year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearData = parent.getItemAtPosition(position).toString();

                if (yearData.equals("1")||yearData.equals("2")) {
                    _section.setSelection(0);
                    _depart.setSelection(0);
                    depart.clear();
                    depart.add("depart");
                    depart.add("A");
                    depart.add("B");


                }else{

                    _section.setSelection(0);
                    _depart.setSelection(0);
                    depart.clear();
                    depart.add("depart");
                    depart.add("CS");
                    depart.add("IS");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Error choose year", Toast.LENGTH_SHORT).show();
            }
        });
        // Spinner Drop down elements



        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, depart);
        dataAdapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        _depart.setAdapter(dataAdapter2);
        _depart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                _section.setSelection(0);
                departData = parent.getItemAtPosition(position).toString();
                if(departData.equals("A")){
                    section.clear();
                    section.add("section");

                    section.add("1");
                    section.add("2");
                    section.add("3");
                    section.add("4");
                }else if(departData.equals("B")){
                    section.clear();
                    section.add("section");
                    section.add("5");
                    section.add("6");
                    section.add("7");
                    section.add("8");

                }else {
                    section.clear();
                    section.add("section");
                    section.add("1");
                    section.add("2");
                    section.add("3");

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Error choose department ", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, section);
        dataAdapter3.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        _section.setAdapter(dataAdapter3);
        _section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sectionData = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Error choose section", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
