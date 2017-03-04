package com.example.abedeid.myapplication.Fragments;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.activites.Update_data;
import com.example.abedeid.myapplication.model.users;
import com.example.abedeid.myapplication.utils.Session;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CheckPasswordDialog extends DialogFragment {
    @BindView(R.id.et_password_update)
    EditText etPassWord_upadte;
    @BindView(R.id.rllt_body)
    RelativeLayout rlltBody;
    @BindView(R.id.prgs_loading)
    ProgressBar prgsLoading;
    @BindView(R.id.rllt_loading)
    RelativeLayout rlltLoading;

    @BindView(R.id.btn_update_check)
    Button btn_update_check;
    users user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         user= Session.getInstance().getUser();
        View view = inflater.inflate(R.layout.fragment_check_password_dialog, container, false);
        ButterKnife.bind(this, view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @OnClick(R.id.btn_update_check)
    public void onClick() {
        setLoadingMode();
      if(user.password.trim().equals(etPassWord_upadte.getText().toString().trim())){
        setNormalMode();
          this.dismiss();
          Intent intent = new Intent(getActivity(), Update_data.class);
          startActivity(intent);

      }else{
setNormalMode();
          Toast.makeText(getActivity(),"Error Password try Again", Toast.LENGTH_SHORT).show();
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
