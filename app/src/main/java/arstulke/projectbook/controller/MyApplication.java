package arstulke.projectbook.controller;

import arstulke.projectbook.model.Book;
import arstulke.projectbook.model.LibraryManager;

@SuppressWarnings("deprecation")
public class MyApplication extends android.app.Application {
    //region removable
    public static Book book = null;
    public static String libName = null;
    public static boolean newBook = false;
    public static LibraryManager libraryManager = null;
    public static String search = "";
    public static boolean searchWithScan = false;
    //endregion
}
