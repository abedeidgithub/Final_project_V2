package com.example.abedeid.myapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.adapters.ScheduleAdapter;
import com.example.abedeid.myapplication.model.users;
import com.example.abedeid.myapplication.utils.Session;


public class ScheduleFragment extends Fragment {
    private final String TAG = "ScheduleFragmentTAG";

    private ScheduleAdapter adapter;
    RecyclerView recycler_view;
    RelativeLayout rllt_loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        RelativeLayout r = (RelativeLayout) view.findViewById(R.id.Schedule_Re);
        users users = Session.getInstance().getUser();
        if (users != null) {
            Toast.makeText(getContext(), users.email, Toast.LENGTH_SHORT).show();
            Log.d("LoginActivityTAG",
                            users.name+"\n"
                            +users.email.toString()+"\n"
                            +users.password.toString()+"\n"
                            +users.id.toString()+"\n"
                            +users.year.toString()+"\n"
                            +users.section.toString()+"\n"
                            +users.department.toString()+"\n"
                            +users.user_flage.toString()+"\n"
                            +users.image+"\n"
                            +users.user_token+"\n"
            );
        }else{
            Toast.makeText(getContext(), "Null", Toast.LENGTH_SHORT).show();



        }
//        final ProgressDialog dialog = new ProgressDialog(getActivity());
//        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        dialog.setMessage("Loading ...");
//        dialog.setIndeterminate(true);
//        dialog.setCancelable(true);
//        recycler_view = (RecyclerView) view.findViewById((R.id.recycler_view));
//        rllt_loading = (RelativeLayout) view.findViewById((R.id.rllt_loading));
//        users users = Session.getInstance().getUser();
//        if (users != null) {
//            users user = Session.getInstance().getUser();
//            String studentYear = user.year+"";
//            String studentDepart = user.department;
//            String studentSection = user.section+"";
//            FirebaseMessaging.getInstance().subscribeToTopic(studentYear+studentDepart+studentSection);
//            Log.e("MessageR","sss");
//            WebService.getInstance().getApi().getSchedule(new Schedule(users.year+"", users.department, users.section+"")).enqueue(new Callback<List<Schedule>>() {
//                @Override
//                public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
//                    List<Schedule> scheduleList = response.body();
//                    adapter = new ScheduleAdapter(scheduleList, getContext());
//                    recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
//                    recycler_view.setItemAnimator(new DefaultItemAnimator());
//                    recycler_view.setAdapter(adapter);
//
//                    dialog.dismiss();
//
//                }
//
//                @Override
//                public void onFailure(Call<List<Schedule>> call, Throwable t) {
//
//                }
//            });
////            setNormalMode();
//        } else {
//            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
//        }


        return view;
    }


}
