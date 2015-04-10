package com.example.fancysherry.todolist.History;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.fancysherry.todolist.Adapter.HistoryListViewAdapter;
import com.example.fancysherry.todolist.Database.DatabaseManager;
import com.example.fancysherry.todolist.Database.Task;
import com.example.fancysherry.todolist.R;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingRightInAnimationAdapter;

import java.util.ArrayList;
import java.util.List;


public class belowCalenderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String Date = "Date";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String newfragment_date;

    private String mParam2;

    private ListView below_calender_listview;
    private HistoryListViewAdapter below_calender_adapter;
    private List<Task> finish_tasks = new ArrayList<Task>();//目前finish_tasks为空
    private DatabaseManager dbManage;


//    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment belowCalenderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static belowCalenderFragment newInstance(String param1, String param2) {
        belowCalenderFragment fragment = new belowCalenderFragment();
//        Bundle args = new Bundle();
//        args.putString(Date, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    public belowCalenderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newfragment_date = getArguments().getString(Date);
//            Log.e("newfragment_date",newfragment_date);
            dbManage = new DatabaseManager(getActivity(), "today");
            finish_tasks = dbManage.queryAll(newfragment_date);


            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_below_calender, container, false);

        below_calender_listview = (ListView) mView.findViewById(R.id.below_calender_listview);
        below_calender_adapter = new HistoryListViewAdapter(getActivity(), finish_tasks);
        setLeftAdapter();


        return mView;


    }

    private void setLeftAdapter() {
        AnimationAdapter animAdapter = new SwingLeftInAnimationAdapter(below_calender_adapter);
        animAdapter.setAbsListView(below_calender_listview);
        below_calender_listview.setAdapter(animAdapter);
    }

    private void setRightAdapter() {
        AnimationAdapter animAdapter = new SwingRightInAnimationAdapter(below_calender_adapter);
        animAdapter.setAbsListView(below_calender_listview);
        below_calender_listview.setAdapter(animAdapter);
    }


}