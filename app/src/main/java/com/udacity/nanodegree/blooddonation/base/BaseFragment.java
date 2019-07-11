package com.udacity.nanodegree.blooddonation.base;

import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
//import androidx.core.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class BaseFragment extends Fragment {

  protected ViewDataBinding mBinding = null;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mBinding = null;
  }
}
