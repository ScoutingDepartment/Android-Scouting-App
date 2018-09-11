package ca.warp7.android.scouting.components;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import ca.warp7.android.scouting.R;

/**
 * @since v0.4.1
 */

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        SettingsClickListener listener = new SettingsClickListener();
        addListener(R.string.pref_copy_assets_key, listener);
        addListener(R.string.pref_x_schedule_key, listener);
        addListener(R.string.pref_licenses_key, listener);

        try {
            PackageInfo packageInfo = getActivity().getPackageManager()
                    .getPackageInfo(getActivity().getPackageName(), 0);

            Preference aboutApp = findPreference(getString(R.string.pref_about_key));
            aboutApp.setIcon(R.mipmap.ic_launcher);
            aboutApp.setSummary("Version: " + packageInfo.versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addListener(int id, SettingsClickListener listener) {
        findPreference(getString(id)).setOnPreferenceClickListener(listener);
    }
}
