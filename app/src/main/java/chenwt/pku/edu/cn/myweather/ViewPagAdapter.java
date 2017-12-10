package chenwt.pku.edu.cn.myweather;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by witt on 2017/11/24.
 * ViewPager的适配器
 */

public class ViewPagAdapter extends PagerAdapter{

    private List<View> viewList;    //数据源
    private List<String> titles;//标题

    public ViewPagAdapter(List<View> viewList, List<String> titles){  //构造函数传入viewList
        this.viewList = viewList;
        this.titles = titles;
    }

    @Override
    //数据源的数目
    public int getCount() {
        return viewList.size();
    }

    @Override
    //view是否由对象产生，官方写返回view == object即可
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    //对应页卡添加上数据
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));      //添加到container
        return viewList.get(position);
    }

    @Override
    //销毁一个页卡(即ViewPager的一个item)
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    //为对应的页卡设置标题
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

}
