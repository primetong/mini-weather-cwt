package chenwt.pku.edu.cn.myweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import chenwt.pku.edu.cn.app.MyApplication;
import chenwt.pku.edu.cn.bean.City;

public class SelectCity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);

        initView();     //初始化控件显示
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:   //点击左上角返回则关掉选择城市列表页面返回主页面
//                Intent iNewCD = new Intent();
//                iNewCD.putExtra("cityCode", "101160101");   //测试切换兰州数据
//                setResult(RESULT_OK, iNewCD);
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化控件内容函数，读取城市列表并显示↓
     * *@param void
     */
    private void initView() {
        ImageView mBackBtn = (ImageView) findViewById(R.id.title_back); //绑定返回的图片控件并设置监听事件
        mBackBtn.setOnClickListener(this);

        MyApplication myApplication = (MyApplication) getApplication();
        final List<City> mCityList = myApplication.getCityList();   //获取到在MyApplication中从数据库取得的城市列表
        ArrayList<String> mCityArrayList = new ArrayList<>();   //不new会指向空
        for (int i=0; i<mCityList.size(); i++){     //ArrayList使用索引循环来遍历效率最高，此外还有for-each方法和迭代器方法
            String cityName = mCityList.get(i).getCity();   //获取数据库中的城市名字列表
            mCityArrayList.add(cityName);
        }
        ListView mCityListLV = (ListView) findViewById(R.id.selectcity_listview);   //绑定ListView控件，绑定数据与ListView的适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectCity.this, android.R.layout.simple_list_item_1, mCityArrayList);
        mCityListLV.setAdapter(adapter);
        //添加ListView项的点击事件并绑定
        mCityListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent iNewCD = new Intent();
                iNewCD.putExtra("cityCode", mCityList.get(position).getNumber());   //通过点击事件的位置判断用户点击哪个城市，并将城市代码存入意图对象
                setResult(RESULT_OK, iNewCD);
                finish();
            }
        });
    }
}
