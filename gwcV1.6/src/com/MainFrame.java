/*
 * MainFrame.java
 *
 * Created on __DATE__, __TIME__
 */

package com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.constant.AppConstant;
import com.listener.LogListener;
import com.task.TasKExecutors;
import com.ui.LogFrame;
import com.ui.MainMenuBar;
import com.ui.TaskFrame;

/**
 *
 * @author __USER__
 */
public class MainFrame extends javax.swing.JFrame {
	public static Log log = LogFactory.getLog(MainFrame.class);

	BorderLayout borderLayout1 = new BorderLayout();
	JDesktopPane mainPanel = new JDesktopPane();
	public static TasKExecutors tasKExecutors ;
	private JToolBar toolBar = new JToolBar();
	private ImageIcon logImg = new ImageIcon("./ico/log.png");
	private JButton logBtn = new JButton(logImg);
	private ImageIcon uploadImg = new ImageIcon("./ico/export.png");
	private JButton uploadBtn = new JButton(uploadImg);
	private ImageIcon monitorImg = new ImageIcon("./ico/monitor.png");
	private JButton monitorBtn = new JButton(monitorImg);
	private ImageIcon chartImg = new ImageIcon("./ico/chart.png");
	private JButton chartBtn = new JButton(chartImg);
	private ImageIcon basesetImg = new ImageIcon("./ico/baseset.png");
	private JButton basesetBtn = new JButton(basesetImg);
	private ImageIcon paramsetImg = new ImageIcon("./ico/paramset.png");
	private JButton paramsetBtn = new JButton(paramsetImg);
	private ImageIcon efficiencyImg = new ImageIcon("./ico/efficiency.png");
	private JButton efficiencyBtn = new JButton(efficiencyImg);
	private ImageIcon amountImg = new ImageIcon("./ico/amount.png");
	private JButton amountBtn = new JButton(amountImg);
	private ImageIcon startImg = new ImageIcon("./ico/start.png");
	private JButton startBtn = new JButton(startImg);
	private ImageIcon stopImg = new ImageIcon("./ico/stop.png");
	private JButton stopBtn = new JButton(stopImg);
	// private ImageIcon switchImg = new ImageIcon("./ico/switch.png");
	// private JButton switchBtn = new JButton(switchImg);
	private ImageIcon simulateImg = new ImageIcon("./ico/set.png");
	private JButton simulateBtn = new JButton(simulateImg);

	private LogFrame logFrame;
	private TaskFrame taskFrame;
	// private UploadFrame uploadFrame;
	// private ChartFrame chartFrame;


	/** Creates new form MainFrame */
	public MainFrame() {
		try {
			initComponents();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 * 
	 * @throws Exception
	 */
	// GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() throws Exception {
		setTitle("广东省公务车信息流向工具");

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		MainMenuBar mainMenuBar = new MainMenuBar(this);
		this.setJMenuBar(mainMenuBar);

		getContentPane().setLayout(borderLayout1);

		setToolBar();
		initFrame();

		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setBackground(new Color(255, 248, 220));

		setIconImage(new ImageIcon("ebig_small.jpg").getImage());

		// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		pack();

	}

	private void initFrame() throws Exception {
		logFrame = new LogFrame(this);
		taskFrame = new TaskFrame(this);
		
		mainPanel.add(logFrame);
		mainPanel.add(taskFrame);

		this.logFrame.setMaximum(true);
		this.taskFrame.setMaximum(true);
	}

	/**
	 * setToolBar
	 */
	private void setToolBar() {

		logBtn.setToolTipText("运行日志");
		toolBar.add(logBtn);
		logBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.logBtn_actionPerformed(e);
			}

		});
		
		monitorBtn.setToolTipText("任务管理");
		toolBar.add(monitorBtn);
		monitorBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				MainFrame.this.monitorBtn_actionPerformed(e);
			}

		});
		startBtn.setToolTipText("启动任务");
		toolBar.add(startBtn);
		startBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				MainFrame.this.startBtn_actionPerformed();
				// 接口控制
				MainFrame.this.switchBtn_actionPerformed();
				//DllClientBA.client.startReceiveService();
			}
		});

		if(AppConstant.autoRun) {
			MainFrame.this.startBtn_actionPerformed();
			// 接口控制
			MainFrame.this.switchBtn_actionPerformed();
		}else {
			stopBtn.setEnabled(false);
			startBtn.setEnabled(true);
		}
		
		// toolBar.addSeparator();

		stopBtn.setToolTipText("停止任务");
		toolBar.add(stopBtn);
		stopBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				MainFrame.this.stopBtn_actionPerformed();
				// 接口控制
				MainFrame.this.switchBtn_actionPerformed();

				//DllClientBA.client.stopReceiveService();
			}

		});
		
		
		

		getContentPane().add(toolBar, BorderLayout.NORTH); // 将工具栏加入窗口的北部

	}

	protected void simulateBtn_actionPerformed(ActionEvent e) {
		try {
			/*this.simulateFrame.setVisible(true);
			this.simulateFrame.setMaximum(true);
			this.simulateFrame.setSelected(true);*/
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}
	protected void startBtn_actionPerformed() {
		if (AppConstant.isRun) {
			JOptionPane.showMessageDialog(this, "任务已经启动！", "注意", JOptionPane.WARNING_MESSAGE);
		} else {
			tasKExecutors=new TasKExecutors();
			tasKExecutors.init();
		}
		stopBtn.setEnabled(true);
		startBtn.setEnabled(false);
	}
	
	protected void stopBtn_actionPerformed() {
		if (AppConstant.isRun) {
			tasKExecutors.closeThreadPool();
		}
		stopBtn.setEnabled(false);
		startBtn.setEnabled(true);
	}
	protected synchronized void switchBtn_actionPerformed() {
	}

	protected void logBtn_actionPerformed(ActionEvent e) {
		try {
			this.logFrame.setVisible(true);
			this.logFrame.setMaximum(true);
			this.logFrame.setSelected(true);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	protected void monitorBtn_actionPerformed(ActionEvent e) {
		try {
			this.taskFrame.setVisible(true);
			this.taskFrame.setMaximum(true);
			this.taskFrame.setSelected(true);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		try {
			LogListener.start();
		} catch (Exception e) {
			log.error("初始化出现异常", e);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				MainFrame framemain = new MainFrame();
				framemain.pack();
				framemain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				framemain.setExtendedState(JFrame.MAXIMIZED_BOTH);

				framemain.addWindowListener(new WindowAdapter() {

				});

				framemain.setVisible(true);
			}
		});
	}


}