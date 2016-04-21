package com.msd.frontend.mimprove;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.Deflater;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

//import com.example.takeimage.R;



/**
 * Created by singlasaahil on 4/8/2016.
 */


public class PictureQuestion extends Activity implements View.OnClickListener{

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Button btnSelect,submit;
    ImageView ivImage;
    byte[] image=null;
    boolean isImageSelected;
    EditText question,answer;

    static PictureQuestion activity=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_picture);
        btnSelect = (Button) findViewById(R.id.btnSelectPhoto);
        btnSelect.setOnClickListener(this);
activity=this;
        question=(EditText)(findViewById(R.id.editText));
        answer=(EditText)(findViewById(R.id.editText2));
        ivImage = (ImageView) findViewById(R.id.ivImage);
        ivImage.setAdjustViewBounds(true);
        submit=(Button)(findViewById(R.id.submit));
        submit.setOnClickListener(this);
    }

    public void onClick(View view){

        if(view.getId()==R.id.btnSelectPhoto){
            selectImage();
        }else if(view.getId()==R.id.submit){
            try{
            if(isImageSelected){
            ProgressDialog dialog=new ProgressDialog(this);
            dialog.setMessage("Please wait while you photo and question is being uploaded");
            dialog.show();
                getDataFromServer(dialog, Base64.encodeToString(image, Base64.DEFAULT /*| Base64.URL_SAFE*/), question.getText().toString(),
                        answer.getText().toString());
                return;
            }
            Toast.makeText(getApplicationContext(), "Please select a photo before submitting", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();}

        }
    }


    public JSONObject getJSONObject(String image,String ques,String ans){
        try{
        JSONObject object=new JSONObject();
        object.put("username",HomeScreen.userName);
        JSONArray array=new JSONArray();
        JSONObject obj=new JSONObject();
            obj.put("text",ques);
        obj.put("correct_answer",ans);
            obj.put("file",image);

            array.put(obj);
         object.put("pic_quiz",array);
            return object;
        }
        catch(Exception e){
            e.printStackTrace();
        }

return null;
    }
    private void getDataFromServer(final ProgressDialog progressDialog,String image,String question,String answer) throws JSONException, UnsupportedEncodingException {
        android.util.Log.e("Call made", "yes");
        AsyncHttpClient clientHandler = new AsyncHttpClient();
        JSONObject object=getJSONObject(image,question,answer);
        StringEntity entity = new StringEntity(object.toString());

        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        String url="picture/save_quiz";

         Log.e("Saahil",object.toString());

        clientHandler.post(PictureQuestion.this,"http://54.172.172.152/" + url, entity,"application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.e("Response succes", new String(responseBody));

                String string = new String(responseBody);
                String res = "" + string;
                org.json.JSONObject jsonObject = null;
                try {
                    jsonObject = new org.json.JSONObject(res);
                    if (jsonObject.getString("result").equalsIgnoreCase("save_successful")) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Question uploaded successfully", Toast.LENGTH_SHORT).show();
                       finish();
                    } else {
                        buildAlertDialog("Error saving image", "Unable to save the image. Please try again");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                progressDialog.dismiss();

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("Response fail", new String(responseBody));
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Sorry there was some problem", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static void buildAlertDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(PictureQuestion.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        isImageSelected=true;
        image=bytes.toByteArray();
        Deflater deflater = new Deflater();
        deflater.setInput(image);
        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 10, bytes);

        image=bytes.toByteArray();
        isImageSelected=true;
        Deflater deflater = new Deflater();
        deflater.setInput(image);
        ivImage.setImageBitmap(bm);
    }

}
