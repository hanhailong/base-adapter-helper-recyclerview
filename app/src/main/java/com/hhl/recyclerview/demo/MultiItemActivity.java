package com.hhl.recyclerview.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hhl.adapter.BaseAdapterHelper;
import com.hhl.adapter.MultiItemTypeSupport;
import com.hhl.adapter.QuickAdapter;
import com.hhl.recyclerview.demo.entity.News;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by HanHailong on 15/9/7.
 */
public class MultiItemActivity extends AppCompatActivity {

    private QuickAdapter<News> mQuickAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);
        getSupportActionBar().setTitle("多种类型的Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        MultiItemTypeSupport<News> multiItemTypeSupport = new MultiItemTypeSupport<News>() {
            @Override
            public int getLayoutId(int viewType) {
                if (viewType == News.ITEM_TYPE_TEXT) {
                    return R.layout.multi_item_text;
                } else if (viewType == News.ITEM_TYPE_BUTTON) {
                    return R.layout.multi_item_button;
                } else if (viewType == News.ITEM_TYPE_IMAGE) {
                    return R.layout.multi_item_image;
                }
                //默认返回是文本
                return News.ITEM_TYPE_TEXT;
            }

            @Override
            public int getItemViewType(int position, News news) {
                return news.getItemType();
            }
        };

        mQuickAdapter = new QuickAdapter<News>(this, multiItemTypeSupport) {
            @Override
            protected void convert(final BaseAdapterHelper helper, News item) {
                switch (helper.getItemViewType()) {
                    case News.ITEM_TYPE_TEXT:
                        helper.setText(R.id.tv_text, item.getText());
                        break;
                    case News.ITEM_TYPE_BUTTON:
                        helper.setText(R.id.btn_click, item.getButton());
                        helper.setOnClickListener(R.id.btn_click, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MultiItemActivity.this, "你点击了按钮" + helper.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case News.ITEM_TYPE_IMAGE:
                        ImageLoader.getInstance().displayImage(item.getImage(), helper.getImageView(R.id.iv_image));
                        break;
                }
            }
        };

        recyclerView.setAdapter(mQuickAdapter);

        initData();
    }

    private void initData() {
        List<News> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            News news = new News();
            int index = random.nextInt(100) % 3;
            if (index == 0) {
                news.setText("新闻标题" + i);
                news.setItemType(News.ITEM_TYPE_TEXT);
            } else if (index == 1) {
                news.setButton("点击我" + i);
                news.setItemType(News.ITEM_TYPE_BUTTON);
            } else if (index == 2) {
                news.setImage("http://www.208206.com/uploads/allimg/150907/20224914K-0.jpg");
                news.setItemType(News.ITEM_TYPE_IMAGE);
            }
            list.add(news);
        }

        mQuickAdapter.addAll(list);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
