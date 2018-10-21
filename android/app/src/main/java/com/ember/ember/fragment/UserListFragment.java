package com.ember.ember.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ember.ember.R;
import com.ember.ember.activity.MainActivity;
import com.ember.ember.adapter.UserRecyclerViewAdapter;
import com.ember.ember.helper.ErrorHelper;
import com.ember.ember.helper.http.HttpHelper;
import com.ember.ember.model.UserDetails;
import com.ember.ember.model.UserDetailsList;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class UserListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_IS_MATCHED = "is-matched";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private boolean isMatched;
    private Context context;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static UserListFragment newInstance(int columnCount, boolean isMatched) {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putBoolean(ARG_IS_MATCHED, isMatched);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            isMatched = getArguments().getBoolean(ARG_IS_MATCHED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        getOtherUsers(recyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getOtherUsers(getActivity().findViewById(R.id.list));
    }

    private void getOtherUsers(RecyclerView recyclerView) {
        UserDetails userDetails = ((MainActivity) getActivity()).getUserDetails();
        Call<UserDetailsList> call = isMatched ?
                HttpHelper.getMatches(userDetails.getUsername())
                : HttpHelper.getPotentialMatches(userDetails.getUsername());
        call.enqueue(new Callback<UserDetailsList>() {
            @Override
            public void onResponse(Call<UserDetailsList> call, Response<UserDetailsList> response) {
                if (response.isSuccessful()) {
                    List<UserDetails> users = response.body().getData();
                    if (isMatched && users.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        getActivity().findViewById(R.id.no_match_alert).setVisibility(View.VISIBLE);
                    }
                    for (UserDetails user : users) {
                        user.setProfilePic();
                    }
                    recyclerView.setAdapter(new UserRecyclerViewAdapter(users, mListener, isMatched));
                }
            }

            @Override
            public void onFailure(Call<UserDetailsList> call, Throwable t) {
                ErrorHelper.raiseToast(context, ErrorHelper.Problem.CALL_FAILED);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(UserDetails item);
        void onListButtonFragmentInteraction(UserDetails match);
        void onListChatFragmentInteraction(UserDetails item);
    }
}
