/*
 * Copyright (C) 2014-2016 The Dirty Unicorns Project
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

package com.blaze.house.categories;

import android.content.Context;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.os.Bundle;
import android.graphics.Color;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;

import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;

import com.android.internal.logging.nano.MetricsProto;
import android.provider.Settings;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.blaze.house.colorpicker.ColorPickerPreference;

public class Themes extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "Themes";
    private String MONET_ENGINE_COLOR_OVERRIDE = "monet_engine_color_override";

	private ColorPickerPreference mMonetColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.themes);

        ContentResolver resolver = getActivity().getContentResolver();
	   mMonetColor = (ColorPickerPreference)findPreference(MONET_ENGINE_COLOR_OVERRIDE);
        int intColor = Settings.Secure.getInt(resolver, MONET_ENGINE_COLOR_OVERRIDE, Color.WHITE);
        String hexColor = String.format("#%08x", (0xffffff & intColor));
        mMonetColor.setNewPreviewColor(intColor);
        mMonetColor.setSummary(hexColor);
        mMonetColor.setOnPreferenceChangeListener(this);
    }


    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.BLAZE_HOUSE;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
	ContentResolver resolver = getActivity().getContentResolver();
	if (preference == mMonetColor) {
            String hex = ColorPickerPreference.convertToARGB(Integer
                .parseInt(String.valueOf(objValue)));
            preference.setSummary(hex);
            int intHex = ColorPickerPreference.convertToColorInt(hex);
            Settings.Secure.putInt(resolver,
                MONET_ENGINE_COLOR_OVERRIDE, intHex);
            return true;
        }
        final String key = preference.getKey();
        return true;
    }
}
