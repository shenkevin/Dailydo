package com.example.fancysherry.todolist;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.datetimepicker.date.DatePickerFragment;
import com.android.datetimepicker.date.DayPickerView;
import com.example.fancysherry.todolist.History.belowCalenderFragment;

import java.util.Calendar;

public class HistoryFragment extends Fragment implements DatePickerFragment.OnDateSetListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private DayPickerView calendarView;

    private Calendar ca = Calendar.getInstance();
    private int year = ca.get(Calendar.YEAR);//获取年份
    private int month=ca.get(Calendar.MONTH);//获取月份
    private int day=ca.get(Calendar.DATE);//获取日
    private String date_now=year+"-"+month+"-"+day;

    private View mView;




   /* private OnFragmentInteractionListener mListener;*/

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null)
                parent.removeView(mView);
        }
        try {
            mView = inflater.inflate(R.layout.fragment_history, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }


        //默认布局
        belowCalenderFragment mFragment_first=new belowCalenderFragment();
        FragmentTransaction ft_first=getFragmentManager().beginTransaction();
        Bundle mBundle=new Bundle();
        mBundle.putString("Date",date_now);
        mFragment_first.setArguments(mBundle);
        ft_first.replace(R.id.fragment_container,mFragment_first).commit();






//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                String date=String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
//               belowCalenderFragment mFragment=new belowCalenderFragment();
//               FragmentTransaction ft=getFragmentManager().beginTransaction();
//               Bundle mBundle=new Bundle();
//               mBundle.putString("Date",date);
//               mFragment.setArguments(mBundle);
//
//                ft.replace(R.id.fragment_container,mFragment).commit();
//
//
//
//            }
//        });


        return mView;
    }


    @Override
    public void onDateSet(DatePickerFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        Log.e("history_year",String.valueOf(year));
        Log.e("history_month",String.valueOf(monthOfYear));
        Log.e("history_dayofmonth",String.valueOf(dayOfMonth));
               String date=String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
               belowCalenderFragment mFragment=new belowCalenderFragment();
               FragmentTransaction ft=getFragmentManager().beginTransaction();
               Bundle mBundle=new Bundle();
               mBundle.putString("Date",date);
               mFragment.setArguments(mBundle);
               ft.replace(R.id.fragment_container,mFragment).commit();
    }
}
