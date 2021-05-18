package com.spisoft.verticalfilterlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.spisoft.verticalmenu.SPMenuList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView SText = findViewById(R.id.txt);
        SPMenuList SPL = findViewById(R.id.splist);

        SPL.SetOnItemSelectListener(new SPMenuList.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position) {
                SText.setText(""+position);
            }
        });

        List<SPMenuList.sitems> mm = new ArrayList<>();
        mm.add(new SPMenuList.sitems(R.drawable.ic_launcher_background, "Text1"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_android_black_24dp, "Text2"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_baseline_run_circle_24, "Text3 lojnasdf utfasduh sds"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_baseline_run_circle_24, "Text4"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_baseline_run_circle_24, "Text5"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_baseline_run_circle_24, "Text6"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_baseline_run_circle_24, "Text7"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_baseline_run_circle_24, "Text8"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_baseline_run_circle_24, "Text9"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_baseline_run_circle_24, "Text 1"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_baseline_run_circle_24, "Text 2"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_baseline_run_circle_24, "Text 3"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_baseline_run_circle_24, "Text 4"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_baseline_run_circle_24, "Text9"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_baseline_run_circle_24, "Text9"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_baseline_run_circle_24, "Text9"));
        mm.add(new SPMenuList.sitems(R.drawable.ic_baseline_run_circle_24, "Text9"));
        SPL.listItems(mm, 5);


    }
}