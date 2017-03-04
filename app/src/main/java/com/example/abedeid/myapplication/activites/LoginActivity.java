package com.example.abedeid.myapplication.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.model.MainResponse;
import com.example.abedeid.myapplication.model.users;
import com.example.abedeid.myapplication.utils.Session;
import com.example.abedeid.myapplication.webservices.WebService;
import com.fourhcode.forhutils.FUtilsValidation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivityTAG";
    @BindView(R.id.img_header_logo)
    ImageView imgHeaderLogo;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_email_login)
    EditText etEmail;
    @BindView(R.id.et_password_login)
    EditText etPassword;
    @BindView(R.id.lnlt_inputs_container)
    LinearLayout lnltInputsContainer;
    @BindView(R.id.tv_dont_have_account)
    TextView tvDontHaveAccount;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.rllt_body)
    RelativeLayout rlltBody;
    @BindView(R.id.prgs_loading)
    ProgressBar prgsLoading;
    @BindView(R.id.rllt_loading)
    RelativeLayout rlltLoading;
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_dont_have_account, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dont_have_account:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                // override default transation of activity
                this.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);

                break;

            case R.id.btn_login:

                if (!FUtilsValidation.isEmpty(etEmail, getString(R.string.enter_email))
                        && FUtilsValidation.isValidEmail(etEmail, getString(R.string.enter_valid_email))
                        && !FUtilsValidation.isEmpty(etPassword, getString(R.string.enter_password))
                        ) {
                    setLoadingMode();

                    users s = new users();
                    s.email = etEmail.getText().toString();
                    s.password = etPassword.getText().toString();

//                    // login User using Retrofit

                    WebService.getInstance().getApi().loginUsers(s).enqueue(new Callback<List<MainResponse>>() {
                        @Override
                        public void onResponse(Call<List<MainResponse>> call, Response<List<MainResponse>> response) {
                            if (response.body().get(0).getStatus() == false) {
                                setNormalMode();
                                Toast.makeText(LoginActivity.this, response.body().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                if (response.body().get(0).verify.equals("true")) {
                                    Log.d(TAG,
                                            response.body().get(0).name + "\n"
                                                    + response.body().get(0).email.toString() + "\n"
                                                    + response.body().get(0).password.toString() + "\n"
                                                    + response.body().get(0).id.toString() + "\n"
                                                    + response.body().get(0).year.toString() + "\n"
                                                    + response.body().get(0).section.toString() + "\n"
                                                    + response.body().get(0).department.toString() + "\n"
                                                    + response.body().get(0).user_flage.toString() + "\n"
                                                    + response.body().get(0).image + "\n"
                                                    + response.body().get(0).user_token + "\n"
                                    );
                                    final users s = new users();
                                    s.email = (response.body().get(0).email.toString());
                                    s.id = (response.body().get(0).id);
                                    s.name = (response.body().get(0).name.toString());
                                    s.password = (response.body().get(0).password.toString());
                                    s.year = (response.body().get(0).year);
                                    s.section = (response.body().get(0).section.toString());
                                    s.department = (response.body().get(0).department.toString());
                                    s.image = (response.body().get(0).image);
                                    s.user_flage = (response.body().get(0).user_flage.toString());
                                    s.remeber_token = (response.body().get(0).remeber_token);
                                    s.user_token = (response.body().get(0).user_token);
                                    Session.getInstance().loginUser(s);
                                    setNormalMode();
                                    Intent gotohome = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(gotohome);
                                    finish();


                                } else {
                                    setNormalMode();
                                    Toast.makeText(LoginActivity.this, response.body().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<List<MainResponse>> call, Throwable t) {
                            setNormalMode();
                            Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                    break;

                }
        }

    }

    // set loading layout visible and hide body layout
    public void setLoadingMode() {
        rlltLoading.setVisibility(View.VISIBLE);
        rlltBody.setVisibility(View.GONE);
    }

    // set body layout visible and hide loading layout
    public void setNormalMode() {
        rlltLoading.setVisibility(View.GONE);
        rlltBody.setVisibility(View.VISIBLE);
    }
}

