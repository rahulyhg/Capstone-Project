package com.android.akshitgupta.capstoneproject.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.android.akshitgupta.capstoneproject.R;
import com.android.akshitgupta.capstoneproject.enums.Numerology;
import com.android.akshitgupta.capstoneproject.numerology.NumerologyDescriptionActivity;
import com.android.akshitgupta.capstoneproject.numerology.table.NumeroTableActivity;
import com.android.akshitgupta.capstoneproject.object.User;

public class AstroDashBoardActivity extends AppCompatActivity implements View.OnClickListener {


    //ImageView dailyPredView;
    ImageView numReportView;
    ImageView favMantraView;
    ImageView lordView;
    ImageView favTimeView;
    ImageView numDetailsView;
    ImageView favVastuView;
    User userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        this.userProfile = (User) intent.getSerializableExtra("userProfile");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astro_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //  dailyPredView = (ImageView) findViewById(R.id.dash_daily_pred);
        numReportView = (ImageView) findViewById(R.id.dash_num_report);
        favMantraView = (ImageView) findViewById(R.id.dash_fav_mantra);
        lordView = (ImageView) findViewById(R.id.dash_lord);
        favTimeView = (ImageView) findViewById(R.id.dash_fav_time);
        numDetailsView = (ImageView) findViewById(R.id.dash_num_details);
        favVastuView = (ImageView) findViewById(R.id.dash_fav_vastu);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        //  dailyPredView.setOnClickListener(this);
        numReportView.setOnClickListener(this);
        favMantraView.setOnClickListener(this);
        lordView.setOnClickListener(this);
        favTimeView.setOnClickListener(this);
        numDetailsView.setOnClickListener(this);
        favVastuView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
     /*   if (view == dailyPredView) {
            view.setBackgroundColor(Color.GRAY);
            Intent intent = new Intent(AstroDashBoardActivity.this, DailyPredictionActivity.class);
            intent.putExtra("userProfile", userProfile);
            startActivity(intent);
            //    view.setBackgroundColor(Color.TRANSPARENT);

        } else*/
        if (view == numReportView) {
            view.setBackgroundColor(Color.LTGRAY);
            Intent intent = new Intent(AstroDashBoardActivity.this, NumeroTableActivity.class);
            intent.putExtra("userProfile", userProfile);
            intent.putExtra("astroURL", Numerology.GENERAL_STATS.getCode());
            startActivity(intent);
            //  view.setBackgroundColor(Color.TRANSPARENT);

        } else if (view == favMantraView) {
            view.setBackgroundColor(Color.LTGRAY);
            Intent intent = new Intent(AstroDashBoardActivity.this, NumerologyDescriptionActivity.class);
            intent.putExtra("userProfile", userProfile);
            intent.putExtra("astroURL", Numerology.FAV_MANTRA.getCode());
            startActivity(intent);
            //   view.setBackgroundColor(Color.TRANSPARENT);

        } else if (view == lordView) {
            view.setBackgroundColor(Color.LTGRAY);
            Intent intent = new Intent(AstroDashBoardActivity.this, NumerologyDescriptionActivity.class);
            intent.putExtra("userProfile", userProfile);
            intent.putExtra("astroURL", Numerology.FAV_LORD.getCode());
            startActivity(intent);
            //   view.setBackgroundColor(Color.TRANSPARENT);

        } else if (view == favTimeView) {
            view.setBackgroundColor(Color.LTGRAY);
            Intent intent = new Intent(AstroDashBoardActivity.this, NumerologyDescriptionActivity.class);
            intent.putExtra("userProfile", userProfile);
            intent.putExtra("astroURL", Numerology.FAV_TIME.getCode());
            startActivity(intent);
            //   view.setBackgroundColor(Color.TRANSPARENT);

        } else if (view == numDetailsView) {
            view.setBackgroundColor(Color.LTGRAY);
            Intent intent = new Intent(AstroDashBoardActivity.this, NumerologyDescriptionActivity.class);
            intent.putExtra("userProfile", userProfile);
            intent.putExtra("astroURL", Numerology.NUMBER_DETAILS.getCode());
            startActivity(intent);
            //    view.setBackgroundColor(Color.TRANSPARENT);

        } else if (view == favVastuView) {
            view.setBackgroundColor(Color.LTGRAY);
            Intent intent = new Intent(AstroDashBoardActivity.this, NumerologyDescriptionActivity.class);
            intent.putExtra("userProfile", userProfile);
            intent.putExtra("astroURL", Numerology.FAV_VASTU.getCode());
            startActivity(intent);
            //  view.setBackgroundColor(Color.TRANSPARENT);

        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        if (favVastuView != null) {
            favVastuView.setBackgroundColor(Color.TRANSPARENT);
        }

        if (numDetailsView != null) {
            numDetailsView.setBackgroundColor(Color.TRANSPARENT);
        }

        if (favTimeView != null) {
            favTimeView.setBackgroundColor(Color.TRANSPARENT);
        }

        if (lordView != null) {
            lordView.setBackgroundColor(Color.TRANSPARENT);
        }

        if (favMantraView != null) {
            favMantraView.setBackgroundColor(Color.TRANSPARENT);
        }
        if(numReportView !=null)
        {
            favMantraView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

}
