package com.msd.frontend.mimprove;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;

/**
 * Created by singlasaahil on 3/25/2016.
 */
public class homeScreenTest extends ActivityInstrumentationTestCase2<HomeScreen> {

    public homeScreenTest() {
        super(HomeScreen.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    @SmallTest
    public void testButton1()
    {
        Button bt = (Button) getActivity().findViewById(R.id.button);
        assertNotNull(bt);

    }

    public void testButton2()
    {
        Button bt = (Button) getActivity().findViewById(R.id.button2);
        assertNotNull(bt);

    }

    public void testButton3()
    {
        Button bt = (Button) getActivity().findViewById(R.id.button3);
        assertNotNull(bt);

    }
    public void testButton4()
    {
        Button bt = (Button) getActivity().findViewById(R.id.button4);
        assertNotNull(bt);

    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
