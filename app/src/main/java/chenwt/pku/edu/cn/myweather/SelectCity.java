package chenwt.pku.edu.cn.myweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SelectCity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);

        ImageView mBackBtn = (ImageView) findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                Intent iNewCD = new Intent();
                iNewCD.putExtra("cityCode", "101160101");   //测试切换兰州数据
                setResult(RESULT_OK, iNewCD);
                finish();
                break;
            default:
                break;
        }
    }
}
