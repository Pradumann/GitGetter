package com.mappr.gitgetter.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mappr.gitgetter.Global.GitGetterSingleton;
import com.mappr.gitgetter.Interfaces.FilterCallBack;
import com.mappr.gitgetter.Pojos.StarGrazer;
import com.mappr.gitgetter.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Filter extends AppCompatActivity {

    private Button applyFilterBTN;

    private CheckBox
            //language
            assemblyCB,
            javaScriptCB,
            swiftCB,
            javaCB,
            shellCB,
            htmlCB,
            cCB,
            elmCB,
            goCB,

            // stargrazers
            star0To50CB,
                    star51To100CB,
                    start101To200CB,
                    star201To500CB,
                    star501To1000CB,
            star1001To5000CB;

    private RadioGroup forkRG,scoreRG, sizeRG, watchersRG;

    private RadioButton

            // fork
            forkM0RB,
            forkM20RB,
            forkM50RB,
            forkM100RB,
            forkM200RB,
            forkM350RB,
            forkM500RB,

            // score
            scoreM0RB,
            scoreM5RB,
                    scoreM10RB,
                    scoreM15RB,
                    scoreM20RB,
                    scoreM25RB,
                    scoreM35RB,
                    scoreM50RB,

            //size
            sizeL20000RB,
                    sizeL10000RB,
                    sizeL5000RB,
                    sizeL2000RB,
                    sizeL1500RB,
                    sizeL1000RB,
                    sizeL500RB,
                    sizeL350RB,
                    sizeL200RB,
                    sizeL100RB,


            //watchers
            watchersM00RB,
                    watchersM10RB,
                    watchersM25RB,
                    watchersM50RB,
                    watchersM100RB,
                    watchersM200RB,
                    watchersM500RB,
                    watchersM1000RB,
                    watchersM5000RB,
                    watchersM10000RB;



    private int watchersCount = 0;
    private int size = 20000;
    private double score = 0.0;
    private int forkCount = 0;
    private List<String> languageList = new ArrayList<>();
    private List<StarGrazer> starGrazerList = new ArrayList<>();

    private HashMap<String, Object> filterHashMap = new HashMap<>();
    private HashMap<String, Object> oldFilterMap = new HashMap<>();
    private FilterCallBack callBack;
    private GitGetterSingleton gitGetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setInstances();
        setViews();
        setOnClickListeners();
        initializeFilter();
    }

    private void setInstances(){
        gitGetter = GitGetterSingleton.getInstance();
        callBack = gitGetter.getFilterCallBack();
    }

    private void setViews(){
        applyFilterBTN = (Button) findViewById(R.id.apply_filter_button);

                assemblyCB = (CheckBox) findViewById(R.id.language_assembly_check_box);
                javaScriptCB = (CheckBox) findViewById(R.id.language_java_script_check_box);
                swiftCB = (CheckBox) findViewById(R.id.language_swift_script_check_box);
                javaCB = (CheckBox) findViewById(R.id.language_java_check_box);
                shellCB = (CheckBox) findViewById(R.id.language_shell_check_box);
                htmlCB = (CheckBox) findViewById(R.id.language_html_check_box);
                cCB = (CheckBox) findViewById(R.id.language_c_plus_plus_check_box);
                elmCB = (CheckBox) findViewById(R.id.language_elm_check_box);
                goCB = (CheckBox) findViewById(R.id.language_go_check_box);

                // stargrazers
                star0To50CB = (CheckBox) findViewById(R.id.star_g_0_50_check_box);
                star51To100CB = (CheckBox) findViewById(R.id.star_g_51_100_check_box);
                start101To200CB = (CheckBox) findViewById(R.id.star_g_101_200_check_box);
                star201To500CB = (CheckBox) findViewById(R.id.star_g_201_500_check_box);
                star501To1000CB = (CheckBox) findViewById(R.id.star_g_501_1000_check_box);
                star1001To5000CB = (CheckBox) findViewById(R.id.star_g_1001_2000_check_box);

        forkRG = (RadioGroup) findViewById(R.id.forks_radio_group);
        scoreRG = (RadioGroup) findViewById(R.id.score_radio_group);
        sizeRG = (RadioGroup) findViewById(R.id.size_radio_group);
        watchersRG = (RadioGroup) findViewById(R.id.watchers_radio_group);



        // fork
        forkM0RB = (RadioButton) findViewById(R.id.fork_mt_0_rb);
                forkM20RB = (RadioButton) findViewById(R.id.fork_mt_20_rb);
                forkM50RB = (RadioButton) findViewById(R.id.fork_mt_50_rb);
                forkM100RB = (RadioButton) findViewById(R.id.fork_mt_100_rb);
                forkM200RB = (RadioButton) findViewById(R.id.fork_mt_200_rb);
                forkM350RB = (RadioButton) findViewById(R.id.fork_mt_350_rb);
                forkM500RB = (RadioButton) findViewById(R.id.fork_mt_500_rb);

                // score
                scoreM0RB = (RadioButton) findViewById(R.id.score_mt_0_rb);
                scoreM5RB = (RadioButton)findViewById(R.id.score_mt_5_rb);
                scoreM10RB = (RadioButton) findViewById(R.id.score_mt_10_rb);
                scoreM15RB = (RadioButton) findViewById(R.id.score_mt_15_rb);
                scoreM20RB = (RadioButton) findViewById(R.id.score_mt_20_rb);
                scoreM25RB = (RadioButton) findViewById(R.id.score_mt_25_rb);
                scoreM35RB = (RadioButton) findViewById(R.id.score_mt_35_rb);
                scoreM50RB = (RadioButton) findViewById(R.id.score_mt_50_rb);

                //size
                sizeL20000RB = (RadioButton) findViewById(R.id.size_lt_20000_rb);
                sizeL10000RB  = (RadioButton) findViewById(R.id.size_lt_10000_rb);
                sizeL5000RB = (RadioButton) findViewById(R.id.size_lt_5000_rb);
                sizeL2000RB = (RadioButton) findViewById(R.id.size_lt_2000_rb);
                sizeL1500RB = (RadioButton) findViewById(R.id.size_lt_1500_rb);
                sizeL1000RB = (RadioButton) findViewById(R.id.size_lt_1000_rb);
                sizeL500RB = (RadioButton) findViewById(R.id.size_lt_500_rb);
                sizeL350RB = (RadioButton) findViewById(R.id.size_lt_350_rb);
                sizeL200RB = (RadioButton) findViewById(R.id.size_lt_200_rb);
                sizeL100RB = (RadioButton) findViewById(R.id.size_lt_100_rb);


                //watchers
                watchersM00RB = (RadioButton) findViewById(R.id.watchers_mt_0_rb);
                watchersM10RB = (RadioButton) findViewById(R.id.watchers_mt_10_rb);
                watchersM25RB = (RadioButton) findViewById(R.id.watchers_mt_25_rb);
                watchersM50RB = (RadioButton) findViewById(R.id.watchers_mt_50_rb);
                watchersM100RB = (RadioButton) findViewById(R.id.watchers_mt_100_rb);
                watchersM200RB = (RadioButton) findViewById(R.id.watchers_mt_200_rb);
                watchersM500RB = (RadioButton) findViewById(R.id.watchers_mt_500_rb);
                watchersM1000RB= (RadioButton) findViewById(R.id.watchers_mt_1000_rb);
                watchersM5000RB = (RadioButton) findViewById(R.id.watchers_mt_5000_rb);
                watchersM10000RB = (RadioButton) findViewById(R.id.watchers_mt_10000_rb);

    }

    private void setOnClickListeners(){
        applyFilterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setMapAndUpdateFilters();
            }
        });

        forkRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedButtonID) {

                if(checkedButtonID == forkM0RB.getId()){
                    forkCount = 0;
                }
                if(checkedButtonID == forkM20RB.getId()){
                    forkCount = 20;
                }
                if(checkedButtonID == forkM50RB.getId()){
                    forkCount = 50;
                }
                if(checkedButtonID == forkM100RB.getId()){
                    forkCount = 100;
                }
                if(checkedButtonID == forkM200RB.getId()){
                    forkCount = 200;
                }
                if(checkedButtonID == forkM350RB.getId()){
                    forkCount = 350;
                }
                if(checkedButtonID == forkM500RB.getId()){
                    forkCount = 500;
                }

            }
        });

        scoreRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedButtonId) {

                if(checkedButtonId == scoreM0RB.getId()){
                    score = 0.0;
                }
                if(checkedButtonId == scoreM5RB.getId()){
                    score = 5.0;
                }
                if(checkedButtonId == scoreM10RB.getId()){
                    score = 10.0;
                }
                if(checkedButtonId == scoreM15RB.getId()){
                    score = 15.0;
                }
                if(checkedButtonId == scoreM20RB.getId()){
                    score = 20.0;
                }
                if(checkedButtonId == scoreM25RB.getId()){
                    score = 25.0;
                }
                if(checkedButtonId == scoreM35RB.getId()){
                    score = 35.0;
                }
                if(checkedButtonId == scoreM50RB.getId()){
                    score = 50.0;
                }

            }
        });

        sizeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkButtonId) {

                if(checkButtonId == sizeL20000RB.getId()){
                    size = 20000;
                }
                if(checkButtonId == sizeL10000RB.getId()){
                    size = 10000;
                }

                if(checkButtonId == sizeL5000RB.getId()){
                    size = 5000;
                }

                if(checkButtonId == sizeL2000RB.getId()){
                    size = 2000;
                }

                if(checkButtonId == sizeL1500RB.getId()){
                    size = 1500;
                }

                if(checkButtonId == sizeL1000RB.getId()){
                    size = 1000;
                }

                if(checkButtonId == sizeL500RB.getId()){
                    size = 500;
                }

                if(checkButtonId == sizeL350RB.getId()){
                    size = 3500;
                }

                if(checkButtonId == sizeL200RB.getId()){
                    size = 200;
                }

                if(checkButtonId == sizeL100RB.getId()){
                    size = 100;
                }

            }
        });

        watchersRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkButtonID) {

                if(checkButtonID == watchersM00RB.getId()){
                    watchersCount = 0;
                }
                if(checkButtonID == watchersM10RB.getId()){
                    watchersCount = 10;
                }if(checkButtonID == watchersM25RB.getId()){
                    watchersCount = 25;
                }if(checkButtonID == watchersM50RB.getId()){
                    watchersCount = 50;
                }if(checkButtonID == watchersM100RB.getId()){
                    watchersCount = 100;
                }if(checkButtonID == watchersM200RB.getId()){
                    watchersCount = 200;
                }if(checkButtonID == watchersM500RB.getId()){
                    watchersCount = 500;
                }if(checkButtonID == watchersM1000RB.getId()){
                    watchersCount = 1000;
                }if(checkButtonID == watchersM5000RB.getId()){
                    watchersCount = 5000;
                }if(checkButtonID == watchersM10000RB.getId()){
                    watchersCount = 10000;
                }
            }
        });
    }

    private void initializeFilter(){
        oldFilterMap = gitGetter.getFilterMap();

        if(oldFilterMap.size() > 0){

            Iterator iterator = oldFilterMap.keySet().iterator();

            while (iterator.hasNext()){

                String key = (String) iterator.next();

                if(key == gitGetter.getF_K_LANGUAGE()){

                   setLanguageCheckBoxes();
                }

                if(key == gitGetter.getF_K_WATCHERS()){

                   setWatchersCount();

                }

                if(key == gitGetter.getF_K_FORKS()){
                    setForksCount();
                }

                if(key == gitGetter.getF_K_SCORE()){

                    setScore();

                }

                if(key == gitGetter.getF_K_SIZE()){

                    setSize();

                }

                if(key == gitGetter.getF_K_STARGRAZERS()){

                    setStarGazer();

                }


            }

        }
    }

    private void setLanguageCheckBoxes(){

        List<String> languageList = (List<String>) oldFilterMap.get(gitGetter.getF_K_LANGUAGE());

        for(String language : languageList){

            if(language.equalsIgnoreCase(gitGetter.getLANG_ASSMBLY())){
                assemblyCB.setChecked(true);
            }
            if(language.equalsIgnoreCase(gitGetter.getLANG_JS())){
                javaScriptCB.setChecked(true);
            }
            if(language.equalsIgnoreCase(gitGetter.getLANG_SWT())){
                swiftCB.setChecked(true);
            }
            if(language.equalsIgnoreCase(gitGetter.getLANG_JAVA())){
                javaCB.setChecked(true);
            }
            if(language.equalsIgnoreCase(gitGetter.getLANG_SHELL())){
                shellCB.setChecked(true);
            }
            if(language.equalsIgnoreCase(gitGetter.getLANG_HTML())){
                htmlCB.setChecked(true);
            }
            if(language.equalsIgnoreCase(gitGetter.getLANG_GO())){
                goCB.setChecked(true);
            }
            if(language.equalsIgnoreCase(gitGetter.getLANG_C_PLS_PLS())){
                cCB.setChecked(true);
            }
            if(language.equalsIgnoreCase(gitGetter.getLANG_ELM())){
                elmCB.setChecked(true);
            }

        }

    }

    private void setWatchersCount(){
        int watchersCount = (int) oldFilterMap.get(gitGetter.getF_K_WATCHERS());

        if(watchersCount == 0){
            watchersM00RB.setSelected(true);
        }
        if(watchersCount == 10){
            watchersM10RB.setChecked(true);
        }
        if(watchersCount == 25){
            watchersM25RB.setChecked(true);
        }
        if(watchersCount == 50){
            watchersM50RB.setChecked(true);
        }
        if(watchersCount == 100){
            watchersM100RB.setChecked(true);
        }
        if(watchersCount == 200){
            watchersM200RB.setChecked(true);
        }
        if(watchersCount == 500){
            watchersM500RB.setChecked(true);
        }
        if(watchersCount == 1000){
            watchersM1000RB.setChecked(true);
        }
        if(watchersCount == 5000){
            watchersM5000RB.setChecked(true);
        }
        if(watchersCount == 10000){
            watchersM10000RB.setChecked(true);
        }

    }

    private void setScore(){

        double score = (double) oldFilterMap.get(gitGetter.getF_K_SCORE());

        if(score == 0.0){
            scoreM0RB.setChecked(true);
        }
        if(score == 5.0){
            scoreM5RB.setChecked(true);
        }
        if(score == 10.0){
            scoreM10RB.setChecked(true);
        }
        if(score == 15.0){
            scoreM15RB.setChecked(true);
        }
        if(score == 20.0){
            scoreM20RB.setChecked(true);
        }
        if(score == 25.0){
            scoreM25RB.setChecked(true);
        }
        if(score == 35.0){
            scoreM35RB.setChecked(true);
        }
        if(score == 50.0){
            scoreM50RB.setChecked(true);
        }

    }

    private void setForksCount(){

        int forks = (int) oldFilterMap.get(gitGetter.getF_K_FORKS());

        if(forks == 0){
            forkM0RB.setChecked(true);
        }
        if(forks == 20){
            forkM20RB.setChecked(true);
        }
        if(forks == 50){
            forkM50RB.setChecked(true);
        }
        if(forks == 100){
            forkM100RB.setChecked(true);
        }
        if(forks == 200){
            forkM200RB.setChecked(true);
        }
        if(forks == 350){
            forkM350RB.setChecked(true);
        }
        if(forks == 500){
            forkM500RB.setChecked(true);
        }

    }

    private void setSize(){
        int size = (int) oldFilterMap.get(gitGetter.getF_K_SIZE());

        if(size == 20000){
            sizeL20000RB.setChecked(true);
        }
        if(size == 10000){
            sizeL10000RB.setChecked(true);
        }
        if(size == 5000){
            sizeL5000RB.setChecked(true);
        }
        if(size == 2000){
            sizeL2000RB.setChecked(true);
        }
        if(size == 1500){
            sizeL1500RB.setChecked(true);
        }
        if(size == 1000){
            sizeL1000RB.setChecked(true);
        }
        if(size == 500){
            sizeL500RB.setChecked(true);
        }
        if(size == 350){
            sizeL350RB.setChecked(true);
        }
        if(size == 200){
            sizeL200RB.setChecked(true);
        }
        if(size == 100){
            sizeL100RB.setChecked(true);
        }
    }

    private void setStarGazer(){

        List<StarGrazer> starGrazerList = (List<StarGrazer>) oldFilterMap.get(gitGetter.getF_K_STARGRAZERS());

        for(StarGrazer starGrazer : starGrazerList){

            int min = starGrazer.getMin();

            if(min == 0){
                star0To50CB.setChecked(true);
            }
            if(min == 51){
                star51To100CB.setChecked(true);
            }
            if(min == 101){
                start101To200CB.setChecked(true);
            }
            if(min == 201){
                star201To500CB.setChecked(true);
            }
            if(min == 501){
                star501To1000CB.setChecked(true);
            }
            if(min == 1001){
                star1001To5000CB.setChecked(true);
            }

        }

    }

    private void setMapAndUpdateFilters(){

        setLanguageAndStarGList();

        if(watchersCount != 0){
            filterHashMap.put(gitGetter.getF_K_WATCHERS(), watchersCount);
        }
        if(size != 20000){
            filterHashMap.put(gitGetter.getF_K_SIZE(), size);
        }

        if(score != 0.0){
            filterHashMap.put(gitGetter.getF_K_SCORE(), score);
        }

        if(forkCount != 0){
            filterHashMap.put(gitGetter.getF_K_FORKS(), forkCount);
        }

        if(languageList.size() != 0){
            filterHashMap.put(gitGetter.getF_K_LANGUAGE(), languageList);
        }

        if(starGrazerList.size() != 0){
            filterHashMap.put(gitGetter.getF_K_STARGRAZERS(), starGrazerList);
        }

        callBack.updateFilters(filterHashMap);
        gitGetter.setFilterMap(filterHashMap);
        Filter.this.finish();
        overridePendingTransition(R.anim.slide_in_right , R.anim.slide_out_left);
    }


    private void setLanguageAndStarGList(){

        if(assemblyCB.isChecked()){
            languageList.add(gitGetter.getLANG_ASSMBLY());
        }

        if(javaScriptCB.isChecked()){
            languageList.add(gitGetter.getLANG_JS());
        }
        if(swiftCB.isChecked()){
            languageList.add(gitGetter.getLANG_SWT());
        }
        if(javaCB.isChecked()){
            languageList.add(gitGetter.getLANG_JAVA());
        }
        if(shellCB.isChecked()){
            languageList.add(gitGetter.getLANG_SHELL());
        }
        if(htmlCB.isChecked()){
            languageList.add(gitGetter.getLANG_HTML());
        }
        if(cCB.isChecked()){
            languageList.add(gitGetter.getLANG_C_PLS_PLS());
        }
        if(elmCB.isChecked()){
            languageList.add(gitGetter.getLANG_ELM());
        }
        if(goCB.isChecked()){
            languageList.add(gitGetter.getLANG_GO());
        }


        // stargrazers
        if(star0To50CB.isChecked()){
            starGrazerList.add(new StarGrazer(0,50));
        }
        if(star51To100CB.isChecked()){
            starGrazerList.add(new StarGrazer(51, 100));
        }
        if(start101To200CB.isChecked()){
            starGrazerList.add(new StarGrazer(101,200));
        }
        if(star201To500CB.isChecked()){
            starGrazerList.add(new StarGrazer(201,500));
        }
        if(star501To1000CB.isChecked()){
            starGrazerList.add(new StarGrazer(501,1000));
        }
        if(star1001To5000CB.isChecked()){
            starGrazerList.add(new StarGrazer(1001,5000));
        }

    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            finish();
            overridePendingTransition(R.anim.slide_in_right , R.anim.slide_out_left);
        }
        return true;
    }
}
