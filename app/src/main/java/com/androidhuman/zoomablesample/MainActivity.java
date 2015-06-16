/*
 * Copyright (C) 2015 Taeho Kim <jyte82@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.androidhuman.zoomablesample;

import com.facebook.common.util.UriUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvList = (RecyclerView) findViewById(R.id.rv_main);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        rvList.setAdapter(new MenuAdapter());

    }

    static class MenuAdapter extends RecyclerView.Adapter<ItemHolder> {

        String[] menu = new String[]{
                "ZoomableDraweeView (Local)",
                "ZoomableDraweeView (Network)",
                "Gallery"};

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            v.setBackgroundResource(R.drawable.abc_item_background_holo_light);
            return new ItemHolder(v);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, final int position) {
            holder.tvTitle.setText(menu[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Class cls;
                    Uri data = null;

                    switch (position) {
                        case 0:
                            cls = ZoomableActivity.class;
                            data = new Uri.Builder()
                                    .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                                    .path(String.valueOf(R.drawable.bbb_splash))
                                    .build();
                            break;
                        case 1:
                            cls = ZoomableActivity.class;
                            data = Uri.parse(
                                    "http://upload.wikimedia.org/wikipedia/commons/3/31/Big.Buck.Bunny.-.Frank.Bunny.png");
                            break;
                        case 2:
                            cls = ViewPagerActivity.class;
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid position: " + position);
                    }

                    Context ctx = v.getContext();
                    ctx.startActivity(
                            new Intent(ctx, cls).setData(data)
                                .putExtra(ZoomableActivity.KEY_TITLE, menu[position]));
                }
            });

        }

        @Override
        public int getItemCount() {
            return menu.length;
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public ItemHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(android.R.id.text1);
        }
    }
}
