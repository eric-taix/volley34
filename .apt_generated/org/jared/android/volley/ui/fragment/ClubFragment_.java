//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package org.jared.android.volley.ui.fragment;

import java.sql.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import org.jared.android.volley.R.layout;
import org.jared.android.volley.VolleyApplication;
import org.jared.android.volley.model.Club;
import org.jared.android.volley.model.Equipe;
import org.jared.android.volley.model.Event;
import org.jared.android.volley.model.Update;
import org.jared.android.volley.repository.VolleyDatabaseHelper;

public final class ClubFragment_
    extends ClubFragment
{

    private View contentView_;
    private ConnectionSource connectionSource_;
    private Handler handler_ = new Handler();

    private void init_(Bundle savedInstanceState) {
        application = ((VolleyApplication) getActivity().getApplication());
        connectionSource_ = OpenHelperManager.getHelper(getActivity(), VolleyDatabaseHelper.class).getConnectionSource();
        try {
            clubDao = DaoManager.createDao(connectionSource_, Club.class);
        } catch (SQLException e) {
            Log.e("ClubFragment_", "Could not create DAO", e);
        }
        try {
            updateDao = DaoManager.createDao(connectionSource_, Update.class);
        } catch (SQLException e) {
            Log.e("ClubFragment_", "Could not create DAO", e);
        }
        try {
            equipeDao = DaoManager.createDao(connectionSource_, Equipe.class);
        } catch (SQLException e) {
            Log.e("ClubFragment_", "Could not create DAO", e);
        }
        try {
            eventDao = DaoManager.createDao(connectionSource_, Event.class);
        } catch (SQLException e) {
            Log.e("ClubFragment_", "Could not create DAO", e);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_() {
        favorite = ((ImageView) findViewById(org.jared.android.volley.R.id.favorite));
        listView = ((ListView) findViewById(org.jared.android.volley.R.id.listView));
        progressBar = ((ProgressBar) findViewById(org.jared.android.volley.R.id.progressBar));
        logo = ((ImageView) findViewById(org.jared.android.volley.R.id.logo));
        maj = ((TextView) findViewById(org.jared.android.volley.R.id.maj));
        title = ((TextView) findViewById(org.jared.android.volley.R.id.title));
        {
            View view = findViewById(org.jared.android.volley.R.id.favorite);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ClubFragment_.this.favoriteClicked();
                    }

                }
                );
            }
        }
        afterViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.club_detail_layout, container, false);
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

    public static ClubFragment_.FragmentBuilder_ builder() {
        return new ClubFragment_.FragmentBuilder_();
    }

    @Override
    public void updateUI() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                try {
                    ClubFragment_.super.updateUI();
                } catch (RuntimeException e) {
                    Log.e("ClubFragment_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void updateClub(final Club clubToUpdate) {
        BackgroundExecutor.execute(new Runnable() {


            @Override
            public void run() {
                try {
                    ClubFragment_.super.updateClub(clubToUpdate);
                } catch (RuntimeException e) {
                    Log.e("ClubFragment_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void updateFromNetwork() {
        BackgroundExecutor.execute(new Runnable() {


            @Override
            public void run() {
                try {
                    ClubFragment_.super.updateFromNetwork();
                } catch (RuntimeException e) {
                    Log.e("ClubFragment_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public ClubFragment build() {
            ClubFragment_ fragment_ = new ClubFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
