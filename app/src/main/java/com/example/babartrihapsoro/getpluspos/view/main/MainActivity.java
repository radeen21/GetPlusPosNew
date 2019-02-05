package com.example.babartrihapsoro.getpluspos.view.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.babartrihapsoro.getpluspos.R;
import com.example.babartrihapsoro.getpluspos.base.BaseActivity;
import com.example.babartrihapsoro.getpluspos.helper.ItemOffsetDecoration;
import com.example.babartrihapsoro.getpluspos.view.adapter.MainAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainAdapter.ItemClickListener{

    @BindView(R.id.rec_main)
    RecyclerView recMain;

    private GridLayoutManager gridLayoutManager;

    private String titleMain[] = {
        "Cek Jumlah Point", "Tukar Point", "Beri Poin",
            "Pembayaran poin", "eVoucher"
    };

    private int iconMain[] = {
         R.drawable.coins, R.drawable.barter, R.drawable.present,
             R.drawable.cash, R.drawable.coupon
    };

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void setup() {
        initMain();
    }

    void initMain() {
        gridLayoutManager = new GridLayoutManager(this, 2);
        recMain.setHasFixedSize(true);
        recMain.setLayoutManager(gridLayoutManager);
        recMain.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.five_dp));
        MainAdapter mainAdapter = new MainAdapter(this, iconMain, titleMain);
        mainAdapter.setClickListener(this);
        recMain.setAdapter(mainAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        final Intent intent;
        switch (position){
            case 0:
//                intent =  new Intent(this, FirstActivity.class);
                Toast.makeText(this, "click oii 0", Toast.LENGTH_SHORT).show();
                break;

            case 1:
                Toast.makeText(this, "click oii 1", Toast.LENGTH_SHORT).show();
//                intent =  new Intent(this, SecondActivity.class);
                break;
            case 2:
                Toast.makeText(this, "click oii 2", Toast.LENGTH_SHORT).show();
//                intent =  new Intent(this, SecondActivity.class);
                break;
            case 3:
                Toast.makeText(this, "click oii 3", Toast.LENGTH_SHORT).show();
//                intent =  new Intent(this, SecondActivity.class);
                break;
            case 4:
                Toast.makeText(this, "click oii 4", Toast.LENGTH_SHORT).show();
//                intent =  new Intent(this, SecondActivity.class);
                break;

        }
//        this.startActivity(intent);
    }
}
