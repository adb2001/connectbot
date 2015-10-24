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

package org.connectbot.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.PreferenceDialogFragmentCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

import org.connectbot.R;

/**
 * @author kenny & alescdb
 */
public class VolumePreference extends DialogPreference {

	@SuppressWarnings("unused")
	public VolumePreference(Context context) {
		super(context);
	}

	@SuppressWarnings("unused")
	public VolumePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@SuppressWarnings("unused")
	public VolumePreference(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@SuppressWarnings("unused")
	public VolumePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public int getVolume() {
		return (int) (getPersistedFloat(0.0f) * 100.0f);
	}

	public void setVolume(float volume) {
		persistFloat(volume / 100f);
	}

	public static class VolumePreferenceDialogFragment extends PreferenceDialogFragmentCompat {

		private SeekBar mVolumeBar;

		public VolumePreferenceDialogFragment() {
		}

		public static VolumePreferenceDialogFragment newInstance(String key) {
			VolumePreferenceDialogFragment fragment = new VolumePreferenceDialogFragment();
			Bundle b = new Bundle(1);
			b.putString("key", key);
			fragment.setArguments(b);
			return fragment;
		}

		@Override
		protected void onBindDialogView(View view) {
			super.onBindDialogView(view);

			mVolumeBar = (SeekBar) view.findViewById(R.id.volume_bar);
			mVolumeBar.setProgress(getVolumePreference().getVolume());
		}

		private VolumePreference getVolumePreference() {
			return (VolumePreference) this.getPreference();
		}

		public void onDialogClosed(boolean positiveResult) {
			if (positiveResult) {
				int volume = mVolumeBar.getProgress();
				if (this.getVolumePreference().callChangeListener(volume)) {
					this.getVolumePreference().setVolume(volume);
				}
			}
		}
	}
}
