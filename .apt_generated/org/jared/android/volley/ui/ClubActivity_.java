//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package org.jared.android.volley.ui;

import java.sql.SQLException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.actionbarsherlock.view.MenuItem;
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
import org.jared.android.volley.ui.widget.quickaction.Action;

public final class ClubActivity_
    extends ClubActivity
{

    private ConnectionSource connectionSource_;
    private Handler handler_ = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.club_detail_layout);
    }

    private void init_(Bundle savedInstanceState) {
        application = ((VolleyApplication) this.getApplication());
        connectionSource_ = OpenHelperManager.getHelper(this, VolleyDatabaseHelper.class).getConnectionSource();
        try {
            equipeDao = DaoManager.createDao(connectionSource_, Equipe.class);
        } catch (SQLException e) {
            Log.e("ClubActivity_", "Could not create DAO", e);
        }
        try {
            eventDao = DaoManager.createDao(connectionSource_, Event.class);
        } catch (SQLException e) {
            Log.e("ClubActivity_", "Could not create DAO", e);
        }
        try {
            clubDao = DaoManager.createDao(connectionSource_, Club.class);
        } catch (SQLException e) {
            Log.e("ClubActivity_", "Could not create DAO", e);
        }
        try {
            updateDao = DaoManager.createDao(connectionSource_, Update.class);
        } catch (SQLException e) {
            Log.e("ClubActivity_", "Could not create DAO", e);
        }
    }

    private void afterSetContentView_() {
        progressBar = ((ProgressBar) findViewById(org.jared.android.volley.R.id.progressBar));
        title = ((TextView) findViewById(org.jared.android.volley.R.id.title));
        listView = ((ListView) findViewById(org.jared.android.volley.R.id.listView));
        maj = ((TextView) findViewById(org.jared.android.volley.R.id.maj));
        favorite = ((ImageView) findViewById(org.jared.android.volley.R.id.favorite));
        logo = ((ImageView) findViewById(org.jared.android.volley.R.id.logo));
        {
            View view = findViewById(org.jared.android.volley.R.id.favorite);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ClubActivity_.this.favoriteClicked();
                    }

                }
                );
            }
        }
        afterViews();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        afterSetContentView_();
    }

    public static ClubActivity_.IntentBuilder_ intent(Context context) {
        return new ClubActivity_.IntentBuilder_(context);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = super.onOptionsItemSelected(item);
        if (handled) {
            return true;
        }
        int itemId_ = item.getItemId();
        if (itemId_ == android.R.id.home) {
            return backHome();
        }
        return false;
    }

    @Override
    public void updateUI() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                try {
                    ClubActivity_.super.updateUI();
                } catch (RuntimeException e) {
                    Log.e("ClubActivity_", "A runtime exception was thrown while executing code in a runnable", e);
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
                    ClubActivity_.super.updateFromNetwork();
                } catch (RuntimeException e) {
                    Log.e("ClubActivity_", "A runtime exception was thrown while executing code in a runnable", e);
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
                    ClubActivity_.super.updateClub(clubToUpdate);
                } catch (RuntimeException e) {
                    Log.e("ClubActivity_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void executeAction(final Action action) {
        BackgroundExecutor.execute(new Runnable() {


            @Override
            public void run() {
                try {
                    ClubActivity_.super.executeAction(action);
                } catch (RuntimeException e) {
                    Log.e("ClubActivity_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ClubActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ClubActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (context_ instanceof Activity) {
                ((Activity) context_).startActivityForResult(intent_, requestCode);
            } else {
                context_.startActivity(intent_);
            }
        }

    }

}
