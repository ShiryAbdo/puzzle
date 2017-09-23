package com.swaqny.crossword.puzzl.challenge.ui.gameplay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.swaqny.crossword.puzzl.challenge.R;
import com.swaqny.crossword.puzzl.challenge.adapters.WordSearchPagerAdapter;
import com.swaqny.crossword.puzzl.challenge.framework.WordSearchManager;
import com.swaqny.crossword.puzzl.challenge.models.GameDifficulty;
import com.swaqny.crossword.puzzl.challenge.models.GameState;
import com.swaqny.crossword.puzzl.challenge.ui.ResultsActivity;


public class WordSearchActivity extends Activity implements WordSearchGridView.WordFoundListener, PauseDialogFragment.PauseDialogListener, View.OnClickListener {

    private final static boolean ON_SKIP_HIGHLIGHT_WORD = true;
    private final static long ON_SKIP_HIGHLIGHT_WORD_DELAY_IN_MS = 500;

    private final static int TIMER_GRANULARITY_IN_MS = 50;

    /**
     * Current number of grid views that have been instantiated
     * Actual fragment position is currentItem - 2
     */
    public static int currentItem;
    private final PauseDialogFragment mPauseDialogFragment = new PauseDialogFragment();
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TextView mTimerTextView, mTimerTextViewplus;
    private TextView mScoreTextView;
    private CountDownTimer mCountDownTimer;
    private String mGameState;
    private long mTimeRemaining;
    private long mRoundTime;
    private int mScore;
    private int mSkipped;
    private WordSearchPagerAdapter mWordSearchPagerAdapter;
    int timer_pius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        categoryId = R.string.ga_gameplay_screen;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.wordsearch_activity);
        mGameState = GameState.START;
        findViewById(R.id.bSkip).setOnClickListener(this);
        findViewById(R.id.bPause).setOnClickListener(this);
        mTimerTextView = (TextView) findViewById(R.id.tvTimer);
        mTimerTextViewplus = (TextView) findViewById(R.id.tvTimerplus);

        mScoreTextView = (TextView) findViewById(R.id.tvScore);
        mScoreTextView.setText("0");
        currentItem = 0;
        mScore = 0;
        mSkipped = 0;
        // Create the adapter that will return a fragment for each of the
        // primary sections of the activity.
        /*
          The {@link android.support.v4.view.PagerAdapter} that will provide
          fragments for each of the sections. We use a
          {@link FragmentPagerAdapter} derivative, which will keep every
          loaded fragment in memory. If this becomes too memory intensive, it
          may be best to switch to a
          {@link android.support.v13.app.FragmentStatePagerAdapter}.
         */
        mWordSearchPagerAdapter = new WordSearchPagerAdapter(getFragmentManager(), getApplicationContext());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (WordSearchViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mWordSearchPagerAdapter);

        mRoundTime = WordSearchManager.getInstance().getGameMode().getTime();
        mTimeRemaining = mRoundTime;
        setupCountDownTimer(mTimeRemaining);
       startCountDownTimer();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String val = extras.getString("level");
            if (val != null) {
                switch (val) {
                    case GameDifficulty.Easy:
                        timer_pius = 2000;
                        break;
                    case GameDifficulty.Medium:
                        timer_pius = 3000;

                        break;
                    case GameDifficulty.Hard:
                        timer_pius = 5000;

                        break;
                    case GameDifficulty.Hard6:
                        timer_pius = 6000;

                        break;
                    case GameDifficulty.Hard7:
                        timer_pius = 8000;

                        break;
                    case GameDifficulty.Hard8:
                        timer_pius = 10000;

                        break;
                    case GameDifficulty.Hard9:
                        timer_pius = 13000;

                        break;
                    case GameDifficulty.Hard10:
                        timer_pius = 15000;

                        break;

                }
            }
        }
    }

    private void pauseGameplay() {
        if (mGameState.equals(GameState.PAUSE))
            return;
        mGameState = GameState.PAUSE;
        stopCountDownTimer();
        mPauseDialogFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bSkip:
//                analyticsTrackEvent(R.string.ga_click_skip);
                if (ON_SKIP_HIGHLIGHT_WORD) {
                    ((WordSearchFragment) mWordSearchPagerAdapter.getFragmentFromCurrentItem(currentItem)).highlightWord();
                    (new CountDownTimer(ON_SKIP_HIGHLIGHT_WORD_DELAY_IN_MS, TIMER_GRANULARITY_IN_MS) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            mViewPager.setCurrentItem(currentItem);
                            mSkipped++;
                        }
                    }).start();
                } else {
                    mViewPager.setCurrentItem(currentItem);
                    mSkipped++;
                }


                break;
            case R.id.bPause:
//                analyticsTrackEvent(R.string.ga_click_pause);
                pauseGameplay();
                break;
        }
    }

    @Override
    public void notifyWordFound() {
        mViewPager.setCurrentItem(currentItem);
        mScoreTextView.setText(Integer.toString(++mScore));
        //   long plustime= getTimeRemaining()+5000;
        stopCountDownTimer();
        mTimeRemaining = mTimeRemaining + timer_pius;


//        Animation anim = new AlphaAnimation(1.0f, 0.0f);
//        anim.setDuration(200);
//        anim.setStartOffset(0);
//        anim.setRepeatMode(Animation.REVERSE);
//        anim.setRepeatCount(4);
//        mTimerTextViewplus.setText("+"+5);
//        mTimerTextViewplus.setTextColor(Color.GREEN);
//        mTimerTextViewplus.startAnimation(anim);
//        anim.setAnimationListener(new Animation.AnimationListener()
//        {
//
//            @Override
//            public void onAnimationStart(Animation animation) { }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) { }
//
//            @Override
//            public void onAnimationEnd(Animation animation)
//            {
//                mTimerTextViewplus.setVisibility(View.GONE);
//
//            }
//        });

        animate(mTimerTextViewplus, R.anim.rec);
        // mTimerTextViewplus.setVisibility(View.GONE);
        //  mTimerTextView.setTextColor(Color.BLACK);

        setupCountDownTimer(mTimeRemaining);
        startCountDownTimer();

    }


    Animation animAnticipateOvershoot;

    public void animate(final View viewHolder, int s) {
        animAnticipateOvershoot = AnimationUtils.loadAnimation(this, s);
        mTimerTextViewplus.setText("+" + timer_pius/1000);

        mTimerTextViewplus.setTextColor(Color.GREEN);

        viewHolder.setAnimation(animAnticipateOvershoot);


        animAnticipateOvershoot.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewHolder.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onDialogQuit() {
        finish();
    }

    @Override
    public void onDialogResume() {
        mGameState = GameState.PLAY;
        setupCountDownTimer(mTimeRemaining);
        startCountDownTimer();
//        setFullscreen();
    }

    @Override
    public void onDialogRestart() {
        mGameState = GameState.PLAY;
        restart();
    }

    private void restart() {
        mScore = 0;
        mSkipped = 0;
        mTimeRemaining = mRoundTime;
        setupCountDownTimer(mTimeRemaining);
         startCountDownTimer();
//        setFullscreen();
        mScoreTextView.setText("0");
        mViewPager.setCurrentItem(currentItem);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        analyticsTrackScreen(getString(categoryId));
        if (mGameState.equals(GameState.START) || mGameState.equals(GameState.FINISHED))
            mGameState = GameState.PLAY;
        else
            pauseGameplay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopCountDownTimer();
    }

    @Override
    public void onBackPressed() {
        pauseGameplay();
    }

    private void setupCountDownTimer(final long timeinMS) {
        mCountDownTimer = new CountDownTimer(timeinMS, TIMER_GRANULARITY_IN_MS) {


            public void onTick(long millisUntilFinished) {
                mTimeRemaining = millisUntilFinished;
                long secondsRemaining = mTimeRemaining / 1000 + 1;
                mTimerTextView.setText(Long.toString(secondsRemaining));
                if (secondsRemaining <= 10) {
                    mTimerTextView.setTextColor(getResources().getColor(R.color.red));
                } else {
                    mTimerTextView.setTextColor(Color.BLACK);

                }
            }

            public void onFinish() {
                mGameState = GameState.FINISHED;
                mTimerTextView.setText("0");
                ((WordSearchFragment) mWordSearchPagerAdapter.getFragmentFromCurrentItem(currentItem)).highlightWord();
                (new CountDownTimer(ON_SKIP_HIGHLIGHT_WORD_DELAY_IN_MS, TIMER_GRANULARITY_IN_MS) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        Intent i = new Intent(getApplicationContext(), ResultsActivity.class);
                        i.putExtra("score", mScore);
                        i.putExtra("skipped", mSkipped);
                        startActivity(i);
                        finish();
                    }
                }).start();
            }
        };
    }

    private void startCountDownTimer() {
        if (mCountDownTimer != null)
            mCountDownTimer.start();
    }

    private void stopCountDownTimer() {
        if (mCountDownTimer != null)
            mCountDownTimer.cancel();
    }

    public long getTimeRemaining() {
        return mTimeRemaining;
    }

    public int getScore() {
        return mScore;
    }

}
