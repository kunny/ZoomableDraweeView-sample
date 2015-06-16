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

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.samples.zoomable.ZoomableDraweeView;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerActivity extends AppCompatActivity {

    ViewPager vpGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);

        setContentView(R.layout.activity_view_pager);

        vpGallery = (ViewPager) findViewById(R.id.vp_gallery);
        vpGallery.setAdapter(new GalleryAdapter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Fresco.shutDown();
    }

    static class GalleryAdapter extends PagerAdapter {

        String[] items = new String[]{
                "https://peach.blender.org/wp-content/uploads/bbb-splash.png",
                "https://peach.blender.org/wp-content/uploads/rodents.png",
                "https://peach.blender.org/wp-content/uploads/evil-frank.png",
                "https://peach.blender.org/wp-content/uploads/bunny-bow.png",
                "https://peach.blender.org/wp-content/uploads/rinkysplash.jpg",
                "https://peach.blender.org/wp-content/uploads/its-a-trap.png"
        };

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ZoomableDraweeView view = new ZoomableDraweeView(container.getContext());
            view.setController(
                    Fresco.newDraweeControllerBuilder()
                            .setUri(Uri.parse(items[position]))
                            .build());

            GenericDraweeHierarchy hierarchy =
                    new GenericDraweeHierarchyBuilder(container.getResources())
                            .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                            .setProgressBarImage(new ProgressBarDrawable())
                            .build();

            view.setHierarchy(hierarchy);

            container.addView(view,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
