package com.sparknetwork.editprofile.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sparknetwork.editprofile.bus.RxBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    //Keep a reference to Context.
    //Setting Context from onAttach makes sure that context not
    //running a risk of being null
    protected Context context;
    private Unbinder unbinder;

    protected abstract @LayoutRes
    int getLayout();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //setting context here to avoid null pointers
        this.context = context;
    }

    @Override
    public void onStop() {
        super.onStop();

        //Stop this fragment from receiving events
        RxBus.unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
