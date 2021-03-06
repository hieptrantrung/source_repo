/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.example.cpulocal.myapplication_sample.samplesync.activities;

import com.example.cpulocal.myapplication_sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Activity to handle the view-group action. In a real app, this would show a rich view of the
 * group, like members, updates etc.
 */
public class ViewGroupActivity extends Activity {
    private static final String TAG = "ViewGroupActivity";

    private TextView mUriTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_group_activity);

        mUriTextView = (TextView) findViewById(R.id.view_group_uri);
        mUriTextView.setText(getIntent().getDataString());
    }
}
