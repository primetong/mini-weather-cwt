package chenwt.pku.edu.cn.myweather;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import chenwt.pku.edu.cn.bean.City;

/**
 * Created by Administrator on 2017/11/19.
 * 数据的适配器类，该类需要实现SectionIndexer接口，该接口是用来控制ListView分组的，该接口有三个方法：
 * getSectionForPosition(int position)，getPositionForSection(int section)，getSections()，只需要自行实现前面两个方法
 * ①getSectionForPosition(int position)是根据ListView的position来获取该位置上面的name的首字母char的ascii值，
 *     例如： 如果该position上面的name是阿妹，首字母就是A，那么此方法返回的就是'A'字母的ascii值，也就是65， 'B'是66，依次类推
 * ②getPositionForSection(int section)就是根据首字母的ascii值来获取在该ListView中第一次出现该首字母的位置，
 *     例如：从上面的效果图1中，如果section是66 ，也就是‘B’的ascii值，那么该方法返回的position就是2
 * 然后就是getView()方法，//如果要更改列表每一项的显示内容（省份城市或是什么的），在getView()里修改
 * 首先我们根据ListView的position调用getSectionForPosition(int position)来获取该位置上面name的首字母的ascii值,
 * 然后根据这个ascii值调用getPositionForSection(int section)来获取第一次出现该首字母的position，
 * 如果ListView的position 等于 根据这个ascii值调用getPositionForSection(int section)来获取第一次出现该首字母的position，则显示分类字母 否则隐藏
 */

public class SortAdapter extends BaseAdapter implements SectionIndexer{
    private List<City> list = null;
    private Context mContext;

    public SortAdapter(Context mContext, List<City> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     * @param list
     */
    public void updateListView(List<City> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final City mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.myadapter_item, null);    //绑定ListView的样式
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.myadapter_title);        //绑定ListView中普通项目的样式
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.myadapter_catalog);     //绑定ListView中目录（索引）的样式
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //根据position获取分类的首字母的char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if(position == getPositionForSection(section)){
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getFirstPY());
        }else{
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        //如果要更改列表每一项的显示内容（省份城市或是什么的），在这里修改
        viewHolder.tvTitle.setText("    " + this.list.get(position).getCity() + "    （省份：" +
                this.list.get(position).getProvince() + "）");

        return view;

    }



    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getFirstPY().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getFirstPY();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }


    @Override
    public Object[] getSections() {
        return null;
    }
}
