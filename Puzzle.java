import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by singlasaahil on 2016-03-25.
 */
public class Puzzle extends TestCase{

    Bitmap theImage = getImage();
    Puzzle puzzle = new Puzzle("Please arrange this picture", theImage);


    public void setUp() throws Exception {
        super.setUp();
        this.puzzle.isCorrect(theImage);

    }

    public void tearDown() throws Exception {

    }

    public void testisCorrect() throws Exception {
        /*
        Need to check if the image is correct or not.

         */


    }

    public Bitmap getImage() throws Exception {
        Cursor cursor = db.selectDataToShow(DataBase.Table_Img, DataBase.IMG_SRC);

        byte[] imageByteArray=cursor.getBlob(cursor.getColumnIndex(DataBase.IMG_SRC));
        cursor.close();

        ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        return theImage;

    }


}