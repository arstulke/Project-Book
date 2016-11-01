package arstulke.projectbook.utils;

import android.os.Environment;

@SuppressWarnings("WeakerAccess")
public class IOUtils {


    //region read/write Config.json
    /*public static String readConfigFile(Context context) {
        if (isExternalStorageReadable()) {
            String settingsJson = "";
            try {
                File file = PropertyUtils.getConfigFile(context);
                if (file.exists()) {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;

                    while ((line = br.readLine()) != null) {
                        settingsJson += line;
                    }
                    br.close();
                    settingsJson = settingsJson.replace("null", "\"false\"");
                } else {
                    settingsJson = "{\"properties\":[{\"name\":\"showHTML\",\"value\":\"false\"},{\"name\":\"downloadWithoutWifi\",\"value\":\"false\"}]}";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return settingsJson;
        }
        return null;
    }

    @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
    public static void writeConfigFile(Context context, String output) throws IOException {
        if (isExternalStorageWritable()) {
            File file = PropertyUtils.getConfigFile(context);
            if (!file.exists())
                file.createNewFile();
            FileWriter fw = new FileWriter(file);
            fw.write(output);
            fw.flush();
            fw.close();
        }
    }
    */
    //endregion

    //region storageAccess
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
    //endregion
}