//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package org.jared.android.volley.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.jared.android.volley.R.layout;

public final class NotAvailableFragment_
    extends NotAvailableFragment
{

    private View contentView_;

    private void init_(Bundle savedInstanceState) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.notavailable_layout, container, false);
        }
        afterSetContentView_();
        return contentView_;
    }

    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    public static NotAvailableFragment_.FragmentBuilder_ builder() {
        return new NotAvailableFragment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public NotAvailableFragment build() {
            NotAvailableFragment_ fragment_ = new NotAvailableFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
