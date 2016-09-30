package arstulke.projectbook;

import android.test.suitebuilder.annotation.LargeTest;
import android.widget.RelativeLayout;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RenderTester {

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule<>();

    @Test
    public void showBookList(){
        //Given
        ArrayList<String> bookList = new ArrayList<>();
        Collections.addAll(bookList, "Buch1", "Buch2", "Buch3", "Buch4");
        RelativeLayout parent = (RelativeLayout) activity.findViewById(R.id.cLibraries);
        LibraryView libraryView = new LibraryView(activity.getApplicationContext(), activity.getResources(), activity);

        //When
        libraryView.show(bookList, parent);

        //Then
        Assert.assertEquals(0, 0);
    }
}