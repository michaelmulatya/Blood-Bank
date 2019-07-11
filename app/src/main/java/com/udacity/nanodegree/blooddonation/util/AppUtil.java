package com.udacity.nanodegree.blooddonation.util;

//import androidx.core.app.Fragment;
//import androidx.core.app.FragmentManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
/**
 * Created by riteshksingh on Apr, 2018
 */
public final class AppUtil {
    private AppUtil() {
    }

    public static void replaceFragmentInActivity(FragmentManager fragmentManager, Fragment fragment,
            int containerId) {
        fragmentManager.beginTransaction().replace(containerId, fragment).commit();
    }
}
