package com.mostafabor3e.mychat.model;

import androidx.fragment.app.Fragment;

public class Mytab {
    private String tab_name;
    private Fragment fragment;

    public Mytab(String tab_name, Fragment fragment) {
        this.tab_name = tab_name;
        this.fragment = fragment;
    }

    public String getTab_name() {
        return tab_name;
    }

    public void setTab_name(String tab_name) {
        this.tab_name = tab_name;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
