/*
 * ConnectBot: simple, powerful, open-source SSH client for Android
 * Copyright 2007 Kenny Root, Jeffrey Sharkey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.connectbot;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import org.connectbot.util.VolumePreference;

public class SettingsActivity extends AppCompatActivity {
	private static final String TAG = "CB.Settings";

	public static class FragmentSettings extends PreferenceFragmentCompat {

		@Override
		public void onCreatePreferences(Bundle bundle, String s) {
			try {
				addPreferencesFromResource(R.xml.preferences);
			} catch (ClassCastException e) {
				Log.e(TAG, "Shared preferences are corrupt! Resetting to default values.");

				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

				// Blow away all the preferences
				SharedPreferences.Editor editor = preferences.edit();
				editor.clear();
				editor.commit();

				PreferenceManager.setDefaultValues(getContext(), R.xml.preferences, true);

				addPreferencesFromResource(R.xml.preferences);
			}

			// TODO: add parse checking here to make sure we have integer value for scrollback
		}

		@Override
		public void onDisplayPreferenceDialog(Preference preference) {
			if (preference instanceof VolumePreference) {
				VolumePreference.VolumePreferenceDialogFragment f = VolumePreference.VolumePreferenceDialogFragment.newInstance(preference.getKey());
				f.setTargetFragment(this, 0);
				f.show(getFragmentManager(), "android.support.v7.preference.PreferenceFragment.DIALOG");
			}
			else {
				super.onDisplayPreferenceDialog(preference);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(android.R.id.content, new FragmentSettings())
				.commit();
	}
}
