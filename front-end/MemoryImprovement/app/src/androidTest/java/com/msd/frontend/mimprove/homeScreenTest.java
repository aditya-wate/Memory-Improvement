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
        Button bt = (Button) getActivity().findViewById(R.id.angry_bt);
        assertNotNull(bt);

    }

    public void testButton2()
    {
        Button bt = (Button) getActivity().findViewById(R.id.angry_b);
        assertNotNull(bt);

    }
    public void testButton3()
    {
        Button bt = (Button) getActivity().findViewById(R.id.angry_btn);
        assertNotNull(bt);

    }
    public void testButton4()
    {
        Button bt = (Button) getActivity().findViewById(R.id.angryb);
        assertNotNull(bt);

    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
