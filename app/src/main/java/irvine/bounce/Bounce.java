package irvine.bounce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Bounce extends AppCompatActivity {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounce);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(l);
    }

    OnClickListener l = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Bounce.this,BounceGame.class);
            startActivity(i);
        }
    };

}
