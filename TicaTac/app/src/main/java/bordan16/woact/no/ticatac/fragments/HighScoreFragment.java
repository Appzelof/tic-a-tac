package bordan16.woact.no.ticatac.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bordan16.woact.no.ticatac.R;
import bordan16.woact.no.ticatac.adapters.HighScoreAdapter;
import bordan16.woact.no.ticatac.services.UserServices;

public class HighScoreFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    private OnHighScoreFragmentInteractionListener mListener;

    public HighScoreFragment() {
        // Required empty public constructor
    }

    public static HighScoreFragment newInstance(String param1, String param2) {
        HighScoreFragment fragment = new HighScoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View v = inflater.inflate(R.layout.fragment_high_score, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.my_recyclerview);
        recyclerView.setHasFixedSize(true);
        HighScoreAdapter highScoreAdapter = new HighScoreAdapter(UserServices.getInstance().getUserList());
        recyclerView.setAdapter(highScoreAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.scrollToPosition(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        return v;
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onHighScoreFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHighScoreFragmentInteractionListener) {
            mListener = (OnHighScoreFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnHighScoreFragmentInteractionListener {
        // TODO: Update argument type and name
        void onHighScoreFragmentInteraction();
    }
}
