package com.outsource.danding.qingdaoic.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import com.outsource.danding.qingdaoic.R;

public abstract class BaseTabActivity extends BaseActivity {

    //当前选中的fragment
    protected Fragment currentFragment;

    @BindView(R.id.content)
    LinearLayout fragmentContent;

    @BindView(R.id.linear_tab)
    LinearLayout linearTab;

    //当前选中的位置
    private int currentItem = -1;

    //初始化位置
    private int firstPosition;

    //tab文字
    private String[] strings;
    //tabFragment实例
    protected Fragment[] fragments;
    //tabIcon
    private int[] icons;
    //tabFragment类
    private Class<? extends Fragment>[] fragmentClasses;

    private int textColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_tab);
        initData();
        initView();
    }

    private void initData() {
        firstPosition = firstPosition();
        textColor = getTextColor();
        strings = getStrings();
        icons = getIcons();
        fragmentClasses = getFragmentClasses();
        fragments = getFragments();
        if (fragments == null)
            fragments = new Fragment[fragmentClasses.length];
        //当activity横竖屏转换时或意外销毁重建时拿出缓存的fragment实例
//        for (int i = 0; i < fragments.length; i++) {
//            fragments[i] = getSupportFragmentManager().findFragmentByTag(strings[i]);
//        }
    }

    private void initView() {
        //如果没有缓存就重建fragment
        if (fragments[firstPosition] == null) {
            try {
                fragments[firstPosition] = fragmentClasses[firstPosition].newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!fragments[firstPosition].isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, fragments[firstPosition], strings[firstPosition]).show(fragments[firstPosition])
                    .commit();
        } else {
            //如果是缓存的fragment就一定是被add过的，所以直接show
            getSupportFragmentManager().beginTransaction()
                    .show(fragments[firstPosition])
                    .commit();
        }
        currentFragment = fragments[firstPosition];
        for (int i = 0; i < fragments.length; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout linear = (LinearLayout) getLayoutInflater().inflate(R.layout.item_tab, linearLayout, false);
            ImageView imgTab = ((ImageView) linear.findViewById(R.id.img_tab));
            TextView textTab = ((TextView) linear.findViewById(R.id.text_tab));
            imgTab.setImageResource(icons[i]);
            if (textColor != 0)
                textTab.setTextColor(getResources().getColorStateList(textColor));
            textTab.setText(strings[i]);
            linearTab.addView(linear);
            final int finalI = i;
            linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isTabCanClick(finalI))
                        onTabClick(finalI);
                }
            });
        }

        selectTab(firstPosition);
    }

    protected boolean isTabCanClick(int position) {
        return true;
    }

    public void onTabClick(int position) {
        if (fragments[position] == null)
            try {
                fragments[position] = fragmentClasses[position].newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        selectTab(position);
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),
                fragments[position], strings[position]);
    }

    //添加或显示fragment
    public void addOrShowFragment(FragmentTransaction transaction, Fragment fragment, String tag) {
        if (currentFragment == fragment) return;
        if (fragment.isAdded())
            //如果fragment已经添加到fragment管理器中就显示出来
            transaction.hide(currentFragment).show(fragment).commit();
        else
            //如果未添加，就添加到fragment管理器中
            transaction.hide(currentFragment).add(R.id.content, fragment, tag).show(fragment).commit();
        currentFragment = fragment;
    }

    //选中底部tab
    public void selectTab(int position) {
        if (currentItem == position) return;
        int count = linearTab.getChildCount();
        for (int i = 0; i < count; i++) {
            LinearLayout linear = (LinearLayout) linearTab.getChildAt(i);
            linear.getChildAt(0).setSelected(false);
            linear.getChildAt(1).setSelected(false);
        }
        currentItem = position;
        LinearLayout linear = (LinearLayout) linearTab.getChildAt(currentItem);
        linear.getChildAt(0).setSelected(true);
        linear.getChildAt(1).setSelected(true);
        onTabSelected(position);
    }

    //获取fragment类
    protected abstract Class[] getFragmentClasses();

    //获取tab标题
    protected abstract String[] getStrings();

    //获取tab图片
    protected abstract int[] getIcons();

    //获取tab标题颜色
    protected abstract int getTextColor();

    //获取fragment实例，用于fragment的构造器非默认构造器时
    protected Fragment[] getFragments() {
        return null;
    }

    //获取首次tab的显示位置
    protected abstract int firstPosition();

    //当前被选中的位置回调
    protected void onTabSelected(int position) {

    }

}
