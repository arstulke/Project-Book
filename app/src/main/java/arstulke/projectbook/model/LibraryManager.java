package arstulke.projectbook.model;

import android.app.Application;
import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import arstulke.projectbook.utils.MyJSONParser;

public class LibraryManager extends ArrayList<Library> {

    private Application application;

    public LibraryManager(Library... libraries) {
        Collections.addAll(this, libraries);
    }

    public LibraryManager() {
    }

    public LibraryManager(Application application) {
        this.application = application;
    }

    public Library getLibrary(String selectedLibrary) {
        for (Library lib : this) {
            if (lib.getName().equals(selectedLibrary)) {
                return lib;
            }
        }

        return null;
    }

    public Comparator<Library> sortBy(sortType sort) {
        switch (sort) {
            case SIZE:
                return new Comparator<Library>() {
                    @Override
                    public int compare(Library o1, Library o2) {
                        if (o1.size() > o2.size())
                            return 1;
                        else if (o1.size() < o2.size())
                            return -1;
                        else
                            return 0;
                    }
                };
            case NAME:
                return new Comparator<Library>() {
                    @Override
                    public int compare(Library o1, Library o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                };
        }

        return null;
    }

    public void loadLibraries(String o) throws IOException {
        if (o == null) {
            this.clear();

            Library e5 = new Library("E5");
            e5.add(new Book("9781941222294", "The Cucumber for Java Book", "Seb Rose; Matt Wynne; Aslak Hellesoy", "O'Reilly UK Ltd.", "2015", "Teams working on the JVM can now say goodbye forever to misunderstood requirements, tedious manual acceptance tests, and out-of-date documentation. Cucumber - the popular, open-source tool that helps teams communicate more effectively with their customers - now has a Java version, and our bestselling Cucumber Book has been updated to match.", "http://bilder.buecher.de/produkte/41/41622/41622411z.jpg", 338));
            e5.add(new Book("9783842101586", "Windows 10", "Robert Klaßen", "Vierfarben", "2015", "Bedienen Sie Windows 10 ganz mühelos ! Windows-Experte Robert Klaßen zeigt Ihnen alle wichtigen Funktionen Bild für Bild . Folgen Sie einfach den Anleitungen, und surfen Sie im Internet, schreiben Sie Texte oder E-Mails, passen Sie den Desktop an und vieles andere mehr.", "http://bilder.buecher.de/produkte/42/42323/42323262z.jpg", 364));
            e5.add(new Book("e5_isbn3", "e5_title3", "e5_author3", "e5_publisher3", "e5_releaseDate3", "e5_description3", "e5_imageUrl3", 3));
            this.add(e5);

            Library apw = new Library("APW");
            apw.add(new Book("9781941222294", "The Cucumber for Java Book", "Seb Rose; Matt Wynne; Aslak Hellesoy", "O'Reilly UK Ltd.", "2015", "Teams working on the JVM can now say goodbye forever to misunderstood requirements, tedious manual acceptance tests, and out-of-date documentation. Cucumber - the popular, open-source tool that helps teams communicate more effectively with their customers - now has a Java version, and our bestselling Cucumber Book has been updated to match.", "http://bilder.buecher.de/produkte/41/41622/41622411z.jpg", 338));
            apw.add(new Book("apw_isbn2", "apw_title2", "apw_author2", "apw_publisher2", "apw_releaseDate2", "apw_description2", "http://bilder.buecher.de/produkte/44/44780/44780699z.jpg", 2));
            apw.add(new Book("apw_isbn3", "apw_title3", "apw_author3", "apw_publisher3", "apw_releaseDate3", "a\na\na\na\na\na\na\na\na\na\na\na\na\na\na\na\na\na\na", "apw_imageUrl3", 3));
            this.add(apw);
        } else {
            String fileName = "LibraryManager.txt";
            LibraryManager libraryManager = (LibraryManager) MyJSONParser.parseJSON(application.openFileInput(fileName), LibraryManager.class);
            this.addAll(libraryManager);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public enum sortType {
        SIZE, NAME
    }
}
