package com.ui;

import java.awt.AWTEvent;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MainMenuBar extends JMenuBar {
	public static Log log = LogFactory.getLog(MainMenuBar.class);
	JFrame parent;
	
    JMenu regMenu = new JMenu(" 菜单 ");
    JMenuItem regItem = new JMenuItem("注册");
    JMenuItem exitItem = new JMenuItem("退出 ");
    
    JMenu configMenu = new JMenu(" 配置 ");
    JMenuItem dbMenu = new JMenuItem("数据库配置");
    
    JMenu helpMenu = new JMenu(" 帮助 ");
    JMenuItem helpItem = new JMenuItem("关于 ");

	public MainMenuBar(JFrame parent){
		this.parent = parent;
		
		add(regMenu);
        regMenu.setFont(new Font("华文细黑", Font.PLAIN, 15));

        regMenu.add(regItem);
        regItem.setFont(new Font("宋体", Font.PLAIN, 13));
        regItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regItemActionPerformed(e);
            }
        });
        
        regMenu.addSeparator();
        regMenu.add(exitItem);
        exitItem.setFont(new Font("宋体", Font.PLAIN, 13));
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitItemActionPerformed(e);
            }
        });
                
        add(configMenu);
        configMenu.setFont(new Font("华文细黑", Font.PLAIN, 15));

        configMenu.add(dbMenu);
        dbMenu.setFont(new Font("宋体", Font.PLAIN, 13));
        dbMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dbMenuActionPerformed(e);
            }
        });
        
        add(helpMenu);
        helpMenu.setFont(new Font("华文细黑", Font.PLAIN, 15));

        helpMenu.add(helpItem);
        helpItem.setFont(new Font("宋体", Font.PLAIN, 13));
        helpItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                helpItemActionPerformed(e);
            }
        });

	}
	
	private void regItemActionPerformed(ActionEvent e) {

    	/*RegisterDialog registerDialog = new RegisterDialog(parent, "注册", true);
        registerDialog.setLocationRelativeTo(parent);

        registerDialog.setVisible(true);*/
    }

    private void exitItemActionPerformed(ActionEvent e) {
        System.exit(0);
    }
    
    private void dbMenuActionPerformed(ActionEvent e) {
  /*      ConfigPWDialog cpwDialog = new ConfigPWDialog(parent, "配置密码", true);
        cpwDialog.setLocationRelativeTo(parent);
        cpwDialog.setVisible(true);
        if (!cpwDialog.isConfigable()) {
            JOptionPane.showMessageDialog(parent, "密码错误,不允许修改配置！");
            return;
        }

        DBConfigDialog dbConfigDialog = new DBConfigDialog(parent, "数据库配置", true);
        dbConfigDialog.setLocationRelativeTo(parent);

        dbConfigDialog.setVisible(true);*/
    }
    
    private void helpItemActionPerformed(AWTEvent e) {
    /*	HelpDialog helpDialog = new HelpDialog(parent, "关于EBIG", true);
        helpDialog.setLocationRelativeTo(parent);

        helpDialog.setVisible(true);*/
    }

}
