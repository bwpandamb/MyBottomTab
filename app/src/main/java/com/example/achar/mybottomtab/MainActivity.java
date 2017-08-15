package com.example.achar.mybottomtab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView helloText;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helloText = (TextView) findViewById(R.id.hello);
        btn = (Button) findViewById(R.id.btn);
        loggerTest();

        //注册EventBus
        EventBus.getDefault().register(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBusBeanEvent event = new EventBusBeanEvent();

                event.setName("测试一下！！！");
                EventBus.getDefault().post(event);
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void 这个方法名随便啦(EventBusBeanEvent beanEvent){

        Logger.i(beanEvent.getName());

        helloText.setText(beanEvent.getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().isRegistered(this);
    }

    private void loggerTest() {
        initLogger();
        testMetheod();
        log4Bean();
    }

    private void initLogger() {
        Settings setting = Logger.init("Charles");
        setting.logLevel(LogLevel.FULL) //  显示全部日志，LogLevel.NONE不显示日志，默认是Full
                .methodCount(20)         //  方法栈打印的个数，默认是2
                .methodOffset(0);     //  设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
    }

    private void log4Bean() {
        String[] names = {"Jerry", "Emily", "小五", "hongyang", "七猫"};
        Logger.d(names);  // 打印字符数组
        List<User> users = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            User user = new User(names[i], 10 + i);
            users.add(user);
        }
        Logger.d(users);  // 打印List


    }

    private void testMetheod() {
        Logger.d("hello----d");
        Logger.e("hello----e");
        Logger.w("hello----w");
        Logger.v("hello----v");
        Logger.wtf("hello----wtf");
// 打印json格式
        String json = createJson().toString();
        Logger.json(json);
// 打印xml格式
//        Logger.xml(getResources().getXml(R.xml.txml));
// 打印自定义级别、tag、信息等格式日志
//        Logger.log(DEBUG, "tag", "message", throwable);

// 创建json数据


    }

    private JSONObject createJson() {
        try {
            JSONObject person = new JSONObject();
            person.put("phone", "12315");
            JSONObject address = new JSONObject();
            address.put("country", "china");
            address.put("province", "fujian");
            address.put("city", "xiamen");
            person.put("address", address);
            person.put("married", true);
            return person;
        } catch (JSONException e) {
            Logger.e(e, "create json error occured");
        }
        return null;
    }
}
