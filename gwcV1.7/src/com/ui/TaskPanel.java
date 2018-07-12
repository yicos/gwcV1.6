package com.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.constant.AppConstant;
import com.eltima.components.ui.DatePicker;
import com.task.TaskSelect;


public class TaskPanel extends JPanel implements ActionListener {
	
	static int index=0;
	private static Log log = LogFactory.getLog(TaskPanel.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final int screenWidth = (int) Toolkit.getDefaultToolkit()
			.getScreenSize().getWidth();
	private static final int screeHeight = (int) Toolkit.getDefaultToolkit()
			.getScreenSize().getHeight();

	private int labelWidth = 100;
	private int labelHeight = 40;
	private int textWidth = 150;
	private int textHeight = 30;
	private int buttonWidth = 100;
	private int buttonHeight = 30;

	private JLabel exceptionLab, CmdNameLab, startTimeLab, finishTimeLab,
			toteNoLab, statusLab;
	private JTextField exceptionFld, toteNoFld;
	private JButton queryBtn, stopBtn, exportBtn,comfrimBtn;
	private JTable taskMsgTable;
	private JComboBox CmdNameList, statusList;
	private String[] taskMsgColNames = {"序号","操作类型" ,"任务名称","传输次数","失败原因"};
	private DatePicker startTime, finishTime;
	private Font font = new Font("宋体", 0, 15);
	private Font labelFont = new Font("宋体", 0, 20);
	private Date date = new Date();
	private DefaultTableModel taskModel;

	/**
	 * Create the panel.
	 */
	public TaskPanel(TaskFrame frame) {
		try {
			jbInit();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(null);
		this.setBackground(new Color(176, 224, 230));
		JPanel CenterPanel = new JPanel();
		CenterPanel.setBounds(0, 0, screenWidth, screeHeight);
		CenterPanel.setLayout(null);
		exceptionLab = new JLabel("失败原因：");
		exceptionLab.setBounds(10, (int) (screeHeight * 0.02), labelWidth,
				labelHeight);
		exceptionLab.setFont(labelFont);
		CenterPanel.add(exceptionLab);
		exceptionFld = new JTextField();
		exceptionFld.setBounds((int) (screenWidth * 0.12) - 50,
				(int) (screeHeight * 0.02) + 5, textWidth, textHeight);
		exceptionFld.setFont(font);
		CenterPanel.add(exceptionFld);

		
		CmdNameLab = new JLabel("功能命令：");
		CmdNameLab.setBounds((int) (screenWidth * 0.22),
				(int) (screeHeight * 0.02), labelWidth, labelHeight);
		CmdNameLab.setFont(labelFont);
		CenterPanel.add(CmdNameLab);
		CmdNameList = new JComboBox();
		CmdNameList.setBounds((int) (screenWidth * 0.35) - 60,
				(int) (screeHeight * 0.02) + 5, textWidth, textHeight);
		CmdNameList.setFont(font);
		CmdNameList.addItem("");
		Vector<String> dataitems = TaskSelect.queryTaskNames();
		for (int i = 0; i < dataitems.size(); i++) {
			CmdNameList.addItem(dataitems.get(i));
		}
		CenterPanel.add(CmdNameList);

		queryBtn = new JButton("查   询");
		queryBtn.setBounds((int) (screenWidth * 0.75),
				(int) (screeHeight * 0.02) + 5, buttonWidth, buttonHeight);
		queryBtn.setFont(font);
		queryBtn.addActionListener(this);
		CenterPanel.add(queryBtn);

		exportBtn = new JButton("导  出");
		exportBtn.setBounds((int) (screenWidth * 0.85),
				(int) (screeHeight * 0.02) + 5, buttonWidth, buttonHeight);
		exportBtn.setFont(font);
		exportBtn.addActionListener(this);
		CenterPanel.add(exportBtn);

		stopBtn = new JButton("终止");
		stopBtn.setBounds((int) (screenWidth * 0.75),
				(int) (screeHeight * 0.1) + 5, buttonWidth, buttonHeight);
		stopBtn.setFont(font);
		stopBtn.addActionListener(this);
		CenterPanel.add(stopBtn);
		
		comfrimBtn = new JButton("确认");
		comfrimBtn.setBounds((int) (screenWidth * 0.85),
				(int) (screeHeight * 0.1) + 5, buttonWidth, buttonHeight);
		comfrimBtn.setFont(font);
		comfrimBtn.addActionListener(this);
		CenterPanel.add(comfrimBtn);

		//设置表格可否编辑
		taskModel = new DefaultTableModel(){
			public boolean isCellEditable(int row, int column) {
	            return false;
		}};
		// taskModel.addTableModelListener(this);
		taskMsgTable = new JTable(taskModel);
		taskMsgTable.setRowHeight(25);
		JScrollPane panel_1 = new JScrollPane(taskMsgTable);

		initTaskMsgTable(null, null);
		panel_1.setBounds(0, (int) (screeHeight * 0.15),
				(int) (screenWidth * 0.99), (int) (screeHeight * 0.6));
		add(panel_1);
		taskMsgTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

			}
		});
		add(CenterPanel);
	}

	private void initTaskMsgTable(String exceptionMsg, String cmd) {
		// taskMsgTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// taskMsgTable.getColumn(1).setMaxWidth(150);

		Vector taskColNames = new Vector();
		for (int i = 0; i < taskMsgColNames.length; i++) {

			taskColNames.add(taskMsgColNames[i]);
		}
		
		Vector taskData = new Vector();
		if(!StringUtils.isBlank(cmd)) {
			cmd=cmd.substring(1, cmd.length()-1);
		}
		taskData = new TaskSelect().queryTask(exceptionMsg,cmd);
		
		taskModel.setDataVector(taskData, taskColNames);
		repaint();

	}

	private void QueryTask() {
		String exceptionMsg = exceptionFld.getText();
		String cmd = CmdNameList.getSelectedItem().toString();
		
		initTaskMsgTable(exceptionMsg, cmd);
		
	}

	private void StopTask() {

		int selectIndexs[] = taskMsgTable.getSelectedRows();
		// JOptionPane.showMessageDialog(null, selectIndexs.length);
		Vector stopData = new Vector();
		for (int i = 0; i < selectIndexs.length; i++) {
			stopData.add(taskMsgTable.getValueAt(selectIndexs[i], 2));
		}
		TaskSelect.stopTask(stopData);
		initTaskMsgTable(null, null);
	}
	private void StopTask(Object flag) {
		
		int selectIndexs[] = taskMsgTable.getSelectedRows();
		// JOptionPane.showMessageDialog(null, selectIndexs.length);
		Vector stopData = new Vector();
		List<Map<Object, Object>> mapList=new ArrayList<Map<Object,Object>>();
		for (int i = 0; i < selectIndexs.length; i++) {
			Map<Object, Object> tempMap = new HashMap<Object, Object>();
			stopData.add(taskMsgTable.getValueAt(selectIndexs[i], 2));
			tempMap.put("locno", taskMsgTable.getValueAt(selectIndexs[i], 14));
			tempMap.put("carton_nbr", taskMsgTable.getValueAt(selectIndexs[i], 15));
			mapList.add(tempMap);
		}
		TaskSelect.stopTask(stopData,mapList,flag);
		initTaskMsgTable(null, null);
	}

	// 导出Excel功能
	private void ExportTask() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
		String name = dateformat.format(new Date());
		name = "T-report1" + name + ".xls";
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setMultiSelectionEnabled(false);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setDialogTitle("保存单位数据文件");

		// 取得文件名输入框冰设置指定格式
		fc.setSelectedFile(new File(name));
		// 添加文件过滤器
		fc.addChoosableFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return true;
			}

			public String getDescription() {
				return "所有文件(*.*)";
			}
		});
		fc.addChoosableFileFilter(new FileFilter() {
			public boolean accept(File f) {
				if (f.getName().endsWith("xls") || f.isDirectory()) {
					return true;
				} else {
					return false;
				}
			}

			public String getDescription() {
				return "Excel文件(*.xls)";
			}
		});
		// 打开对话框
		int result = fc.showSaveDialog(null);
		String outPath = null;

		// 文件确定
		if (result == JFileChooser.APPROVE_OPTION) {
			outPath = fc.getSelectedFile().getAbsolutePath();
			if (new File(outPath).exists()) {
				if (JOptionPane.showConfirmDialog(null, "文件已经存在,是否要覆盖该文件?") == 0) {

				} else {
					JOptionPane.showMessageDialog(null, "导出取消！");
					return;
				}
			}
			File file = fc.getSelectedFile();
			int columnNumber = taskMsgTable.getColumnCount();
			int rowNumber = taskMsgTable.getRowCount();
			try {
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet();
				sheet.setColumnWidth(2, 5000);
				sheet.setColumnWidth(3, 5000);
				workbook.setSheetName(0, "firstSheet");
				HSSFRow row = sheet.createRow(0);
				for (int i = 0; i < taskMsgColNames.length; i++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(taskMsgColNames[i]);
				}
				for (int n = 0; n < rowNumber; n++) {
					row = sheet.createRow(n + 1);
					for (int c = 0; c < columnNumber; c++) {
						HSSFCell cell = row.createCell(c);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue((String.valueOf(taskMsgTable
								.getValueAt(n, c))));
					}
				}
				FileOutputStream fOut = new FileOutputStream(outPath);
				workbook.write(fOut);
				fOut.flush();
				fOut.close();
				JOptionPane.showMessageDialog(null, "导出成功！");
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (result == 0) {
			if (JOptionPane.showConfirmDialog(null, "是否立即打开？") == 0) {
				// path = D:\\Backup\\我的文档\\2012-11-09_110848.xls
				String fileName = outPath.replace('\\', '/');
				StringTokenizer st = new StringTokenizer(fileName, "/");
				while (st.hasMoreTokens()) {
					String sub = st.nextToken();
					// 过滤特殊字符
					if ((sub.indexOf(' ') != -1) || (sub.indexOf('&') != -1)
							|| (sub.indexOf('(') != -1)
							|| (sub.indexOf(')') != -1)
							|| (sub.indexOf('[') != -1)
							|| (sub.indexOf(']') != -1)
							|| (sub.indexOf('{') != -1)
							|| (sub.indexOf('}') != -1)
							|| (sub.indexOf('^') != -1)
							|| (sub.indexOf('=') != -1)
							|| (sub.indexOf(';') != -1)
							|| (sub.indexOf('!') != -1)
							|| (sub.indexOf('\'') != -1)
							|| (sub.indexOf('+') != -1)
							|| (sub.indexOf(',') != -1)
							|| (sub.indexOf('`') != -1)
							|| (sub.indexOf('~') != -1))
					// 过滤掉特殊字符
					{
						fileName = fileName
								.replaceFirst(sub, "\"" + sub + "\"");
					}
				} // fileName = D:/Backup/我的文档/2012-11-09_110848.xls
				try {
					Runtime.getRuntime().exec("cmd /E:ON /c start " + fileName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == queryBtn) {
			QueryTask();
		} else if (e.getSource() == stopBtn) {
			StopTask();
		} else if (e.getSource() == exportBtn) {
			ExportTask();
		}else if(e.getSource() == comfrimBtn){
			if (JOptionPane.showConfirmDialog(null, "是否手工确认？确认后会发送消息至wms") == 0) {
				StopTask(0);
			}
		}

	}

}
