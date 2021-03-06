package com.mandalalabs.chirp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mandalalabs.chirp.R;
import com.mandalalabs.chirp.UserSession;
import com.mandalalabs.chirp.adapter.ChirpsListAdapter;
import com.mandalalabs.chirp.utils.Constants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ChirpsFragment extends Fragment {

    private static final String TAG = Constants.LOG_TAG;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private RecyclerView rvChirps;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChirpsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ChirpsFragment newInstance(int columnCount) {
        ChirpsFragment fragment = new ChirpsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            // Do something with args
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chirps_list, container, false);

        if (UserSession.chirpsList == null) {
            UserSession.chirpsList = new ArrayList<ParseObject>();
        }
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            rvChirps = (RecyclerView) view;
            rvChirps.setLayoutManager(new LinearLayoutManager(context));
            rvChirps.setAdapter(new ChirpsListAdapter(UserSession.chirpsList, mListener));
        }

        getChirps();
        return view;
    }

    private void getChirps() {
        Log.d(TAG, "Getting chirps!!!");
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Constants.TABLE_CHIRPS);
        query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                UserSession.chirpsList.clear();
                UserSession.chirpsList.addAll(objects);
                Log.d(TAG, "List of chirps: size = " + objects.size());
                for (ParseObject object : objects) {
                    Log.d(TAG, "User ID:" + object.get(Constants.MESSAGE_KEY));
                    object.getParseObject(Constants.SENDER_KEY).fetchIfNeededInBackground();
                }
                rvChirps.getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
