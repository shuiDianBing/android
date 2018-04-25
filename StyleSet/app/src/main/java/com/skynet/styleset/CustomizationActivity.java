package com.skynet.styleset;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * Created by Administrator on 2018/2/3.
 * include方式title
 */

public class CustomizationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        * Android 软键盘弹出时把布局顶上去，控件乱套解决方法 http://www.cnblogs.com/zhujiabin/p/5853083.html
        * 方法1.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        * 方法2.在AndroidManifest文件activity中添加android:windowSoftInputMode="stateVisible|adjustResize"这样会让屏幕整体上移
        * adjustPan这样键盘就会覆盖屏幕
        *方法2.把顶级的layout替换成ScrollView，或者说在顶级的Layout上面再加一层ScrollView的封装。
        * 这样就会把软键盘和输入框一起滚动了，软键盘会一直处于底部
        * getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);进来的的时候隐藏键盘
        * 但是点击了EditText弹出键盘后从其他Activity返回来后键盘还是显示
        * requestWindowFeature(Window.FEATURE_NO_TITLE);//去除title
        * */
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_customization);
        //setStates();
        //setTranslucentStatus(false);
        //concealNavigation();
        findViewById(R.id.image_goBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    /*
      * android状态栏颜色修改 http://www.cnblogs.com/leon-hm/p/5131323.html
      * android状态栏一体化(沉浸式状态栏) http://blog.csdn.net/jdsjlzx/article/details/41643587
      * Android 5.0 如何实现将布局的内容延伸到状态栏? https://www.zhihu.com/question/31468556
      * 去掉Activity上面的状态栏getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
      * Android 沉浸式状态栏完美实现 http://blog.csdn.net/tyzlmjj/article/details/50890847
      * <item name="android:fitsSystemWindows">true</item>如果你想让一个View的图像显示在状态栏下,那么就在View的XML布局文件中添加属性android:fitsSystemWindows="true"
      *  <item name="android:windowNoTitle">true</item>//去除title另一种方式requestWindowFeature(Window.FEATURE_NO_TITLE);
      *  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉Activity上面的状态栏
      * */
    protected void setStates(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            Window window = getWindow();
            //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.setStatusBarColor(ContextCompat.getColor(this,android.R.color.transparent));
            window.setNavigationBarColor(0x00000000);
        }
        /*ViewGroup contentView = findViewById(Window.ID_ANDROID_CONTENT);
        View childView = contentView.getChildAt(0);
        if(null != childView)
            childView.setFitsSystemWindows(true);//ViewCompat.setFitsSystemWindows(childView,true);*/
    }
    protected void setStatus(){
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(android.R.color.transparent);//状态栏设置为透明色
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setNavigationBarTintResource(Color.TRANSPARENT);//导航栏设置为透明色
        }*/
    }
    /*
     * 【Android】隐藏底部虚拟按键 https://www.cnblogs.com/lanlengran/p/6415946.html
     * Android App 隐藏标题栏+状态栏+导航栏 http://blog.csdn.net/myarrow/article/details/25606653
     * Android之framework修改底部导航栏NavigationBar动态显示和隐藏 http://blog.csdn.net/way_ping_li/article/details/45727335
     * Android隐藏虚拟按键（底部导航栏）http://blog.csdn.net/z191726501/article/details/48783703
     * 隐藏底部虚拟键Navigation Bar实现全屏有两种情况 https://www.cnblogs.com/evolutionoflicorice/p/5746056.html
     * 以下三行代码虽为全屏但是点击一下激活虚拟按键然后在点击有效
     * WindowManager.LayoutParams params = getWindow().getAttributes();
     * params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
     * getWindow().setAttributes(params);
     */
    protected void concealNavigation(){
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);不自动影藏且背景透明
        //方式1
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                /*| View.SYSTEM_UI_FLAG_FULLSCREEN*/ | View.SYSTEM_UI_FLAG_IMMERSIVE);
        //方式2
        /*WindowManager.LayoutParams params = getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_FULLSCREEN;
        getWindow().setAttributes(params);*/
        //设置屏幕始终在前面，不然点击鼠标，重新出现虚拟按键
        /*getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        //| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        //| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                        // bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);*/
    }
    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    private boolean isDisplayHideInput(View view,MotionEvent event){//触控在View上返回true否则false
        if(null != view && (view instanceof EditText)){
            int[] leftTop = {0,0};
            view.getLocationInWindow(leftTop);
            view.clearFocus();
            return event.getX()> leftTop[0] && event.getX()< leftTop[0]+ view.getWidth()&& event.getY()> leftTop[1]&& event.getY()< leftTop[0]+ view.getHeight()?
                    false : true;
        }
        return false;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        /*if(MotionEvent.ACTION_DOWN == ev.getAction()) {
            View view = getCurrentFocus();
            *//*if (isDisplayHideInput(view, ev)) {//点击EditText隐藏键盘--->方式1
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (null != imm)
                    imm.hideSoftInputFromWindow(view.getWindowToken(),0);
            }*
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);//方式2
            if (null !=  view) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                view.clearFocus();
            }
            return super.dispatchTouchEvent(ev);
        }
        if(getWindow().superDispatchTouchEvent(ev))// 必不可少，否则所有的组件都不会有TouchEvent了
            return true;*/
        return super.dispatchTouchEvent(ev);
    }
}
