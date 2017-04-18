package armcurl.armcurltest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class StartActivity extends AppCompatActivity {
    static final int START_COUNT_RIGHT = 1, START_COUNT_LEFT =2;
    TextView rightTextView, leftTextView;

    final String myTag = "DocsUpload";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        rightTextView = (TextView) findViewById(R.id.right_text_view);
        leftTextView = (TextView) findViewById(R.id.left_text_view);
    }

    public void startCurlRight(View view){
        Intent intent = new Intent(this, CurlActivity.class);
        startActivityForResult(intent, START_COUNT_RIGHT);
    }

    public void startCurlLeft(View view){
        Intent intent = new Intent(this, CurlActivity.class);
        startActivityForResult(intent, START_COUNT_LEFT);
    }



    public void postData(int leftArm, int rightArm) {

        String fullUrl = "https://docs.google.com/forms/d/e/1FAIpQLSfEkWAzWBf2V6JRqYnv2K8VpF9gbkb127bDAFwKUrBNzCqEjg/formResponse";
        HttpRequest mReq = new HttpRequest();
        String col1 = Integer.toString(leftArm);
        String col2 = Integer.toString(rightArm);

        String data = null;
        try {
            data = "entry.1451805253=" + URLEncoder.encode(col1,"UTF-8") + "&" +
                    "entry.8104665=" + URLEncoder.encode(col2,"UTF-8") ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String response = mReq.sendPost(fullUrl, data);
        Log.i(myTag, response);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        int leftScore = 0;
        int rightScore = 0;
        if(resultCode == RESULT_OK && data != null) {
            String text = data.getStringExtra("score");
            if(requestCode == START_COUNT_RIGHT){
                rightScore = Integer.parseInt(text);
                rightTextView.setText("Right Arm Score: " + text);

            }else if(requestCode == START_COUNT_LEFT){
                leftScore = Integer.parseInt(text);
                leftTextView.setText("Left Arm Score: " + text);
            }
        }
        final int finalLeftScore = leftScore;
        final int finalRightScore = rightScore;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                postData(finalLeftScore, finalRightScore);

            }
        });
        t.start();
    }
}

