package com.skynet.styleset;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TraditionActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);标题
        //((TextView) findViewById(android.R.id.title)).setGravity(Gravity.CENTER);http://blog.csdn.net/sevenshal/article/details/10928547
        setContentView(R.layout.activity_tradition);
        setActionBar(getSupportActionBar());//题与AppCompatActivity https://www.jianshu.com/p/2f939a212de7
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TraditionActivity.this,CustomizationActivity.class));
            }
        });
    }
    private void setActionBar(ActionBar actionBar){//给AppCompatActivity的标题栏上加上返回按钮 http://blog.csdn.net/imtina/article/details/53667377
        if(null != actionBar){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this,MenuActivity.class));
                //finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
