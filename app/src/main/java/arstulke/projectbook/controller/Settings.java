package arstulke.projectbook.controller;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import arstulke.projectbook.activities.SettingsActivity;

public class Settings {

    public static class Preference {
        private String key;
        private Object defaultValue;

        private Preference(String key, Object defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public String getKey() {
            return key;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }
    }

    public static final Preference DOWNLOAD_WITHOUT_WIFI = new Preference("downloadWithoutWifi", false);
    public static final Preference SHOW_ADVERTISEMENT_LINKS = new Preference("showAdvertisementLinks", true);
    public static final Preference SHOW_HTML = new Preference("showHTML", false);

    public static boolean useEthernet(Application application) {
        ConnectivityManager connManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean preference = SettingsActivity.getBooleanPreference(DOWNLOAD_WITHOUT_WIFI, application.getApplicationContext());
        return wifi.isConnected() || preference;
    }

    public static boolean showAdvertisementLinks(Application application) {
        return SettingsActivity.getBooleanPreference(SHOW_ADVERTISEMENT_LINKS, application.getApplicationContext());
    }

    public static boolean showHTML(Application application) {
        return SettingsActivity.getBooleanPreference(SHOW_HTML, application.getApplicationContext());
    }
}