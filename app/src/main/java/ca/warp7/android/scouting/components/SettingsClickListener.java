package ca.warp7.android.scouting.components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.preference.Preference;

import ca.warp7.android.scouting.LicensesActivity;
import ca.warp7.android.scouting.R;
import ca.warp7.android.scouting.ScheduleActivity;
import ca.warp7.android.scouting.res.AppResources;

/**
 * @since v0.4.1
 */

class SettingsClickListener implements Preference.OnPreferenceClickListener {
    @Override
    public boolean onPreferenceClick(Preference preference) {
        Context context = preference.getContext();
        String key = preference.getKey();
        if (key.equals(context.getString(R.string.pref_copy_assets_key))) {
            onCopyAssets(context);
        } else if (key.equals(context.getString(R.string.pref_x_schedule_key))) {
            onScheduleActivityIntent(context);
        } else if (key.equals(context.getString(R.string.pref_licenses_key))) {
            onLicensesIntent(context);
        } else {
            return false;
        }
        return true;
    }

    private void onScheduleActivityIntent(Context context) {
        Intent intent;
        intent = new Intent(context, ScheduleActivity.class);
        context.startActivity(intent);
    }

    private void onLicensesIntent(Context context) {
        Intent intent;
        intent = new Intent(context, LicensesActivity.class);
        context.startActivity(intent);
    }

    private void onCopyAssets(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Are you sure?")
                .setMessage("Any files stored at \""
                        + AppResources.getSpecsRoot().getAbsolutePath()
                        + "\" and \""
                        + AppResources.getEventsRoot().getAbsolutePath()
                        + "\" will be overwritten.")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", (dialog, which) -> {
                    AppResources.copySpecsAssets(context);
                    AppResources.copyEventAssets(context);
                })
                .create().show();
    }
}
