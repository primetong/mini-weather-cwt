package chenwt.pku.edu.cn.myweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chenwt.pku.edu.cn.app.MyApplication;
import chenwt.pku.edu.cn.bean.City;

public class SelectCity extends AppCompatActivity implements View.OnClickListener{
    private TextView selectTitleTv, mDialogLetterTv;
    private ClearEditText mClearEditText;
    private SideBar mSideBar;
    private SortAdapter mAdapter;
    private ListView mCityListLV;

    private List<City> mCityList;

    private List<City> filterDataList = new ArrayList<City>();

    private SharedPreferences sp; //实例化SharedPreference对象，用于读取更新SelectCityActivity的“当前城市：”标题

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
        selectTitleTv = (TextView) findViewById(R.id.title_name);
        sp = getSharedPreferences("config", MODE_PRIVATE);  //获得sp实例对象
        String selectedcityname = sp.getString("CITY", "N/A");
        selectTitleTv.setText("当前城市：" + selectedcityname);

        ImageView mBackBtn = (ImageView) findViewById(R.id.title_back); //绑定返回的图片控件并设置监听事件
        mBackBtn.setOnClickListener(this);

        mClearEditText = (ClearEditText) findViewById(R.id.search_city);
        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则显示为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSideBar = (SideBar) findViewById(R.id.listview_sidebar);
        mDialogLetterTv = (TextView) findViewById(R.id.listview_dialog);
        mSideBar.setTextView(mDialogLetterTv);
        //设置右侧触摸监听
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1){
                    mCityListLV.setSelection(position);
                }
            }
        });

        MyApplication myApplication = (MyApplication) getApplication();
        mCityList = myApplication.getCityList();   //获取到在MyApplication中从数据库取得的城市列表
        //如果要更改列表每一项的显示内容（省份城市或是什么的），到SortAdapter类中找到对应的TextView修改
        for (City city : mCityList){
            filterDataList.add(city);
        }
        mCityListLV = (ListView) findViewById(R.id.selectcity_listview);   //绑定ListView控件，绑定数据与ListView的适配器
        mAdapter = new SortAdapter(SelectCity.this, filterDataList);
        mCityListLV.setAdapter(mAdapter);

//        ArrayList<String> mCityArrayList = new ArrayList<>();   //不new会指向空
//        for (int i=0; i<mCityList.size(); i++){     //ArrayList使用索引循环来遍历效率最高，此外还有for-each方法和迭代器方法
////            String noNumber = Integer.toString(i + 1);
//            String cityCode = mCityList.get(i).getNumber();
//            String provinceName = mCityList.get(i).getProvince();
//            String cityName = mCityList.get(i).getCity();   //获取数据库中的城市名字列表
////            mCityArrayList.add("NO." + noNumber + ":" + cityCode + " - " + provinceName + " - " + cityName);
//            mCityArrayList.add(provinceName + " - " + cityName + "(" + cityCode + ")");
//        }
//        mCityListLV = (ListView) findViewById(R.id.selectcity_listview);   //绑定ListView控件，绑定数据与ListView的适配器
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectCity.this, android.R.layout.simple_list_item_1, mCityArrayList);
//        mCityListLV.setAdapter(adapter);

        //添加ListView项的点击事件并绑定
        mCityListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent iNewCD = new Intent();
                iNewCD.putExtra("cityCode", filterDataList.get(position).getNumber());   //通过点击事件的位置判断用户点击哪个城市，并将城市代码存入意图对象
                setResult(RESULT_OK, iNewCD);
                finish();
            }
        });
    }

    /**
     * 根据ClearEditText中的搜索关键字过滤显示的列表，当输入框里面的值为空，更新为原来的列表，否则显示为过滤数据列表
     */
    private void filterData(String filterStr) {
        Log.d("myFilter", filterStr);
        String upperFilterStr = filterStr.toUpperCase();

//        Log.d("myFilter", String.valueOf(TextUtils.isEmpty(filterStr)));
        if (TextUtils.isEmpty(filterStr)){
            filterDataList.clear();
            for (City city : mCityList){
                filterDataList.add(city);
            }
        }
        else {
            filterDataList.clear();
            for (City city : mCityList){
                // 匹配中文城市名、全拼首字母和第一个字拼音首字母
                if (city.getCity().indexOf(upperFilterStr) > -1
                        || city.getFirstPY().indexOf(upperFilterStr) > -1
                        || city.getAllFristPY().indexOf(upperFilterStr) > -1){
                    filterDataList.add(city);
                }
            }
        }
        mAdapter.updateListView(filterDataList);
        if (filterDataList.isEmpty()){  //如果关键字找到的列表为空（即找不到对应项目）抖动ClearEditText并给出提示信息
            mClearEditText.setStartShakeAnimation();
            Toast.makeText(SelectCity.this, "找不到对应城市哟！请检查搜索词！" + "\n" + "支持中文/拼音首字母的模糊搜索~~~", Toast.LENGTH_SHORT).show();
        }
    }
}
