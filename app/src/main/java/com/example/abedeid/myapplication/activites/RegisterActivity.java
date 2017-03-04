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


import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.model.MainResponse;
import com.example.abedeid.myapplication.model.users;
import com.example.abedeid.myapplication.webservices.WebService;
import com.fourhcode.forhutils.FUtilsValidation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "RegisterActivityTAG";
    public static String yearData, departData, sectionData;
    @BindView(R.id.img_header_logo)
    ImageView imgHeaderLogo;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_repeat_password)
    EditText etRepeatPassword;
    @BindView(R.id.lnlt_inputs_container)
    LinearLayout lnltInputsContainer;
    @BindView(R.id.tv_already_have_account)
    TextView tvAlreadyHaveAccount;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.rllt_body)
    RelativeLayout rlltBody;
    @BindView(R.id.prgs_loading)
    ProgressBar prgsLoading;
    @BindView(R.id.rllt_loading)
    RelativeLayout rlltLoading;
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;
    @BindView(R.id.yaer)
    Spinner _year;
    @BindView(R.id.depart)
    Spinner _depart;
    @BindView(R.id.section)
    Spinner _section;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initCustomSpinner();
    }

    @OnClick({R.id.tv_already_have_account, R.id.btn_signup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_already_have_account:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);

                break;
            case R.id.btn_signup:

                if (!FUtilsValidation.isEmpty(etUsername, getString(R.string.enter_username))
                        && !FUtilsValidation.isEmpty(etEmail, getString(R.string.enter_email))
                        && FUtilsValidation.isValidEmail(etEmail, getString(R.string.enter_valid_email))
                        && !FUtilsValidation.isEmpty(etPassword, getString(R.string.enter_password))
                        && !FUtilsValidation.isEmpty(etRepeatPassword, getString(R.string.enter_password_again))
                        && FUtilsValidation.isPasswordEqual(etPassword, etRepeatPassword, getString(R.string.password_isnt_equal))
                        && !FUtilsValidation.isSpinnerFirstItemSelected(_year, "Error choose year", RegisterActivity.this)
                        && !FUtilsValidation.isSpinnerFirstItemSelected(_depart, "Error choose department", RegisterActivity.this)
                        && !FUtilsValidation.isSpinnerFirstItemSelected(_section, "Error choose section", RegisterActivity.this)
                        ) {
                    setLoadingMode();
                    // create new user object and set data from editTexts
                    final users s = new users();

                    s.name = etUsername.getText().toString();
                    s.email = etEmail.getText().toString();
                    s.password = etPassword.getText().toString();
                    s.year = _year.getSelectedItem().toString();
                    s.section = _section.getSelectedItem().toString();
                    s.department = _depart.getSelectedItem().toString();

                    WebService.getInstance().getApi().registerStudent(s).enqueue(new Callback<MainResponse>() {
                        @Override
                        public void onResponse(Call<MainResponse> call, final Response<MainResponse> response) {
                            if (response.body().getStatus() == true) {
                                Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                Intent gotohome = new Intent(RegisterActivity.this, SplashActivity.class);
                                startActivity(gotohome);
                                finish();
                            } else {
                                Log.d(TAG, response.body().getMessage());
                                Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            setNormalMode();
                        }

                        @Override
                        public void onFailure(Call<MainResponse> call, Throwable t) {
                            Log.d(TAG, t.getLocalizedMessage());

                        }
                    });


                    break;
                }
        }
    }

    // set loading layout visible and hide body layout
    private void setLoadingMode() {
        rlltLoading.setVisibility(View.VISIBLE);
        rlltBody.setVisibility(View.GONE);
    }

    // set body layout visible and hide loading layout
    private void setNormalMode() {
        rlltLoading.setVisibility(View.GONE);
        rlltBody.setVisibility(View.VISIBLE);
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

