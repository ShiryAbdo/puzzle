package com.swaqny.crossword.puzzl.challenge.framework;

import android.content.Context;
import android.os.AsyncTask;

import com.swaqny.crossword.puzzl.challenge.models.GameDifficulty;
import com.swaqny.crossword.puzzl.challenge.models.GameMode;
import com.swaqny.crossword.puzzl.challenge.wordSearchGenerator.generators.WordSearchGenerator;
import com.swaqny.crossword.puzzl.challenge.wordSearchGenerator.models.FillType;

import java.util.Random;



public class WordSearchManager {

    //easy level
    public final static int EASY_MIN_WORDLENGTH = 3;
    public final static int EASY_MAX_WORDLENGTH = 3;
    public final static int EASY_MIN_DIMENSION_OFFSET = 0;
    public final static int EASY_MAX_DIMENSION_OFFSET = 0;

    // medium level
    public final static int MEDIUM_MIN_WORDLENGTH = 4;
    public final static int MEDIUM_MAX_WORDLENGTH = 4;
    public final static int MEDIUM_MIN_DIMENSION_OFFSET = 0;
    public final static int MEDIUM_MAX_DIMENSION_OFFSET = 0;

    // hard level
    public final static int HARD_MIN_WORDLENGTH = 5;
    public final static int HARD_MAX_WORDLENGTH = 5;
    public final static int HARD_MIN_DIMENSION_OFFSET = 0;
    public final static int HARD_MAX_DIMENSION_OFFSET = 0;

    // hard6 level
    public final static int HARD6_MIN_WORDLENGTH = 6;
    public final static int HARD6_MAX_WORDLENGTH = 6;
    public final static int HARD6_MIN_DIMENSION_OFFSET = 0;
    public final static int HARD6_MAX_DIMENSION_OFFSET = 0;

    // hard7 level
    public final static int HARD7_MIN_WORDLENGTH = 7;
    public final static int HARD7_MAX_WORDLENGTH = 7;
    public final static int HARD7_MIN_DIMENSION_OFFSET = 0;
    public final static int HARD7_MAX_DIMENSION_OFFSET = 0;

    // hard8 level
    public final static int HARD8_MIN_WORDLENGTH = 8;
    public final static int HARD8_MAX_WORDLENGTH = 8;
    public final static int HARD8_MIN_DIMENSION_OFFSET = 0;
    public final static int HARD8_MAX_DIMENSION_OFFSET = 0;

    // hard9 level
    public final static int HARD9_MIN_WORDLENGTH = 9;
    public final static int HARD9_MAX_WORDLENGTH = 9;
    public final static int HARD9_MIN_DIMENSION_OFFSET = 0;
    public final static int HARD9_MAX_DIMENSION_OFFSET = 0;

    // hard10 level
    public final static int HARD10_MIN_WORDLENGTH = 10;
    public final static int HARD10_MAX_WORDLENGTH = 10;
    public final static int HARD10_MIN_DIMENSION_OFFSET = 0;
    public final static int HARD10_MAX_DIMENSION_OFFSET = 0;


    // advancid level

    public final static int ADVANCED_MIN_WORDLENGTH = 9;
    public final static int ADVANCED_MAX_WORDLENGTH = 11;
    public final static int ADVANCED_MIN_DIMENSION_OFFSET = 0;
    public final static int ADVANCED_MAX_DIMENSION_OFFSET = 0;

    private final static int SIZE = 2;
    private static WordSearchManager mInstance;

    /**
     * Dimension offset is the size of the word search relative to the chosen word length
     */
    private int mMinDimensionOffset;
    private int mMaxDimensionOffset;
    private GameMode mGameMode;
    private Random mRandom;
    private WordSearchGenerator[] mWordSearchArray;
    private WordProvider mWordProvider;

    private WordSearchManager() {
        mRandom = new Random();
        mWordSearchArray = new WordSearchGenerator[SIZE];
    }

    public static WordSearchManager getInstance() {
        if (mInstance == null)
            mInstance = new WordSearchManager();
        return mInstance;
    }

    public static void nullify() {
        mInstance = null;
    }

    public void buildWordSearches() {
        new BuildWordSearchArrayTask().execute();
    }

    public WordSearchGenerator getWordSearch(final int i) {
        if (i < 0)
            return buildWordSearch();
        WordSearchGenerator ret = mWordSearchArray[i % SIZE];
        new BuildWordSearchTask(i % SIZE).execute();
        return ret;
    }

    private WordSearchGenerator buildWordSearch() {
        String word = null;
        while (word == null) {
            word = mWordProvider.getWord();
        }
        int dimen = word.length() + mMinDimensionOffset;
        int offsetDifference = mMaxDimensionOffset - mMinDimensionOffset;
        if (offsetDifference > 0)
            dimen += mRandom.nextInt(offsetDifference + 1);

        WordSearchGenerator gen = new WordSearchGenerator(dimen, dimen, word, FillType.CharactersOfTheWord);
        gen.build();
        return gen;
    }

    public GameMode getGameMode() {
        return mGameMode;
    }

    public void Initialize(GameMode gameMode, Context context) {
        this.mGameMode = gameMode;
        if (mGameMode.getDifficulty().equals(GameDifficulty.Easy)) {
            mWordProvider = new WordProvider(context, EASY_MIN_WORDLENGTH, EASY_MAX_WORDLENGTH);
            mMinDimensionOffset = EASY_MIN_DIMENSION_OFFSET;
            mMaxDimensionOffset = EASY_MAX_DIMENSION_OFFSET;
        } else if (mGameMode.getDifficulty().equals(GameDifficulty.Medium)) {
            mWordProvider = new WordProvider(context, MEDIUM_MIN_WORDLENGTH, MEDIUM_MAX_WORDLENGTH);
            mMinDimensionOffset = MEDIUM_MIN_DIMENSION_OFFSET;
            mMaxDimensionOffset = MEDIUM_MAX_DIMENSION_OFFSET;
        } else if (mGameMode.getDifficulty().equals(GameDifficulty.Hard)) {
            mWordProvider = new WordProvider(context, HARD_MIN_WORDLENGTH, HARD_MAX_WORDLENGTH);
            mMinDimensionOffset = HARD_MIN_DIMENSION_OFFSET;
            mMaxDimensionOffset = HARD_MAX_DIMENSION_OFFSET;
        } else if (mGameMode.getDifficulty().equals(GameDifficulty.Advanced)) {
            mWordProvider = new WordProvider(context, ADVANCED_MIN_WORDLENGTH, ADVANCED_MAX_WORDLENGTH);
            mMinDimensionOffset = ADVANCED_MIN_DIMENSION_OFFSET;
            mMaxDimensionOffset = ADVANCED_MAX_DIMENSION_OFFSET;
        }  else if (mGameMode.getDifficulty().equals(GameDifficulty.Hard6)) {
            mWordProvider = new WordProvider(context, HARD6_MIN_WORDLENGTH, HARD6_MAX_WORDLENGTH);
            mMinDimensionOffset = HARD6_MIN_DIMENSION_OFFSET;
            mMaxDimensionOffset = HARD6_MAX_DIMENSION_OFFSET;
        }else if (mGameMode.getDifficulty().equals(GameDifficulty.Hard7)) {
            mWordProvider = new WordProvider(context, HARD7_MIN_WORDLENGTH, HARD7_MAX_WORDLENGTH);
            mMinDimensionOffset = HARD7_MIN_DIMENSION_OFFSET;
            mMaxDimensionOffset = HARD7_MAX_DIMENSION_OFFSET;
        }else if (mGameMode.getDifficulty().equals(GameDifficulty.Hard8)) {
            mWordProvider = new WordProvider(context, HARD8_MIN_WORDLENGTH, HARD8_MAX_WORDLENGTH);
            mMinDimensionOffset = HARD8_MIN_DIMENSION_OFFSET;
            mMaxDimensionOffset = HARD8_MAX_DIMENSION_OFFSET;
        }else if (mGameMode.getDifficulty().equals(GameDifficulty.Hard9)) {
            mWordProvider = new WordProvider(context, HARD9_MIN_WORDLENGTH, HARD9_MAX_WORDLENGTH);
            mMinDimensionOffset = HARD9_MIN_DIMENSION_OFFSET;
            mMaxDimensionOffset = HARD9_MAX_DIMENSION_OFFSET;
        }else if (mGameMode.getDifficulty().equals(GameDifficulty.Hard10)) {
            mWordProvider = new WordProvider(context, HARD10_MIN_WORDLENGTH, HARD10_MAX_WORDLENGTH);
            mMinDimensionOffset = HARD10_MIN_DIMENSION_OFFSET;
            mMaxDimensionOffset = HARD10_MAX_DIMENSION_OFFSET;
        }
    }

    private class BuildWordSearchArrayTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < SIZE; i++) {
                mWordSearchArray[i] = buildWordSearch();
            }
            return null;
        }
    }

    private class BuildWordSearchTask extends AsyncTask<Void, Void, Void> {

        private int i;

        public BuildWordSearchTask(int i) {
            this.i = i;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mWordSearchArray[i] = buildWordSearch();
            return null;
        }
    }
}
