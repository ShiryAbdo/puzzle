package com.swaqny.crossword.puzzl.challenge.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import com.swaqny.crossword.puzzl.challenge.R;
import com.swaqny.crossword.puzzl.challenge.framework.WordSearchManager;
import com.swaqny.crossword.puzzl.challenge.models.GameDifficulty;
import com.swaqny.crossword.puzzl.challenge.models.GameMode;
import com.swaqny.crossword.puzzl.challenge.models.GameType;
import com.swaqny.crossword.puzzl.challenge.ui.gameplay.WordSearchActivity;


public class MenuActivity extends Activity implements View.OnClickListener {

    private final static String MENU_PREF_NAME = "menu_prefs";
    private final static String FIRST_TIME = "first_time";

    private final static long ROUND_TIME_IN_MS = 60000;
LinearLayout liner_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // categoryId = R.string.ga_menu_screen;
        // Check if first time opening app, show splash screen
        SharedPreferences prefs = getSharedPreferences(MENU_PREF_NAME, MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean(FIRST_TIME, true);
        if (isFirstTime) {
            SharedPreferences.Editor editor = getSharedPreferences(MENU_PREF_NAME, MODE_PRIVATE).edit();
            editor.putBoolean(FIRST_TIME, false);
            editor.apply();

            Intent i = new Intent(getApplicationContext(), SplashActivity.class);
            startActivity(i);
        }

        setContentView(R.layout.activity_menu);
        liner_button = (LinearLayout)findViewById(R.id.liner_button);
        animate(findViewById(R.id.bMenuEasy3),R.anim.left_in);
        animate(findViewById(R.id.bMenuMedium4),R.anim.left_in2);
        animate(findViewById(R.id.bMenuHard5),R.anim.left_in3);
        animate(findViewById(R.id.bMenuHard6),R.anim.left_in4);
        animate(findViewById(R.id.bMenuHard7),R.anim.left_in5);
        animate(findViewById(R.id.bMenuHard8),R.anim.left_in6);
        animate(findViewById(R.id.bMenuHard9),R.anim.left_in7);
        animate(findViewById(R.id.bMenuHard10),R.anim.left_in8);
        findViewById(R.id.bMenuEasy3).setOnClickListener(this);
        findViewById(R.id.bMenuMedium4).setOnClickListener(this);
        findViewById(R.id.bMenuHard5).setOnClickListener(this);
        findViewById(R.id.bMenuHard6).setOnClickListener(this);
        findViewById(R.id.bMenuHard7).setOnClickListener(this);
        findViewById(R.id.bMenuHard8).setOnClickListener(this);
        findViewById(R.id.bMenuHard9).setOnClickListener(this);
        findViewById(R.id.bMenuHard10).setOnClickListener(this);
        //TODO: Reimplement advanced after more efficient way of drawing out the grid
//        findViewById(R.id.bMenuAdvanced).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bMenuSignIn) {
         //   mInSignInFlow = true;
          //  mSignInClicked = true;
          //  mGoogleApiClient.connect();
            return;
        }
        String level;
        String gd = null;
        int ga_button_id = -1;
        switch (view.getId()) {
            case R.id.bMenuEasy3:
                gd = GameDifficulty.Easy;
                ga_button_id = R.string.ga_click_easy;
                level=gd;
                break;
            case R.id.bMenuMedium4:
                gd = GameDifficulty.Medium;
                ga_button_id = R.string.ga_click_medium;
                level=gd;
                break;
            case R.id.bMenuHard5:
                gd = GameDifficulty.Hard;
                ga_button_id = R.string.ga_click_hard;
                level=gd;
                break;
            case R.id.bMenuHard6:
                gd = GameDifficulty.Hard6;
                ga_button_id = R.string.ga_click_hard6;
                level=gd;
                break;
            case R.id.bMenuHard7:
                gd = GameDifficulty.Hard7;
                ga_button_id = R.string.ga_click_hard7;
                level=gd;
                break;
            case R.id.bMenuHard8:
                gd = GameDifficulty.Hard8;
                ga_button_id = R.string.ga_click_hard8;
                break;
            case R.id.bMenuHard9:
                gd = GameDifficulty.Hard9;
                ga_button_id = R.string.ga_click_hard9;
                level=gd;
                break;
            case R.id.bMenuHard10:
                gd = GameDifficulty.Hard10;
                ga_button_id = R.string.ga_click_hard10;
                level=gd;
                break;
//            case R.id.bMenuAdvanced:
//                gd = GameDifficulty.Advanced;
//                break;
        }
     //   analyticsTrackEvent(ga_button_id);
        WordSearchManager wsm = WordSearchManager.getInstance();
        wsm.Initialize(new GameMode(GameType.Timed, gd, ROUND_TIME_IN_MS), getApplicationContext());
        wsm.buildWordSearches();
        Intent i = new Intent(getApplicationContext(), WordSearchActivity.class);
        i.putExtra("level",gd);
        startActivity(i);
    }
    public void animate(View viewHolder,int s) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(this, s);
        viewHolder.setAnimation(animAnticipateOvershoot);
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  analyticsTrackScreen(getString(categoryId));
        WordSearchManager.nullify();
    }




}
