package com.ui;

import javax.swing.JInternalFrame;

import com.MainFrame;

public class TaskFrame extends JInternalFrame {
    MainFrame parent = null;
    TaskPanel panel = null;

    public TaskFrame(MainFrame mainFrame)throws Exception{
        super("任务管理",
              true, //可变尺寸
              true, //有关闭按钮
              true, //有最大化按钮
              true); //最小化按钮

        //给内部窗体添加一个文本域
        TaskPanel j = new TaskPanel(this);
        getContentPane().add(j);

        //设置内部窗体的大小
        setSize(300, 300);

        //设置内部窗体的显示位置
        setLocation(0, 0);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.panel = j;

        this.parent = mainFrame;
    }
}
