package com.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogPanel extends JPanel {
	private static Log log = LogFactory.getLog(LogPanel.class);
	/**
	 * 把日期转为String类型的格式
	 */
	    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	   /**
	    * 表格模式
	    */
	    static DefaultTableModel logModel = null;
	    /**
	     * 表格定义
	     */
	    static JTable logtable=null;
	    /**
	     * 表头定义
	     */
	    String[] headers={"序号","当前时间","任务编号","任务名称","日志级别","日志信息"};
	    /**
	     * 已插入行数
	     */
	    static int index=0;
	    
	    DefaultTableCellRenderer logtcr=null;
	/**
	 * Create the panel.
	 */
	public LogPanel(LogFrame frame) {
		try {
			jbInit();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	private void jbInit() throws Exception {
		//this.setLayout(null);
		this.setBackground(new Color(176, 224, 230));
		//定义滚动容器
		 JScrollPane  scrollPane = new JScrollPane();
	    	//定义表格模式
	        logModel=new DefaultTableModel(null, headers){
	        	//只读
	        	 public boolean isCellEditable(int row, int column) {
	 	            return false;
	 	        }
	        };
	        this.setLayout(new BorderLayout(0, 0));
	        logtable=new JTable(logModel);
	        this.add(scrollPane);
	        this.setVisible(true);//可视化
	        scrollPane.setViewportView(logtable);
	        scrollPane.setVisible(true);//可视化
	        logtable.setVisible(true);//可视化
	        //表头不可移动
	        logtable.getTableHeader().setReorderingAllowed(false) ;
	}
	/**
	 * 插入日志记录
	 * 不可以直接调用，要通过LogListener日志监听器来调用
	 * @param map
	 * map的字段有：serverno,servername,logtype,loginfo
	 * serverno :服务器编号
	 * servername：服务器名称
	 * logtype：日志类型，1是正常，2是错误
	 * loginfo：日志信息
	 * add lhy
	 */
	 public static   void InsertLog(Map map){
	    	
	    	String serverno=map.get("serverno").toString();
	    	String servername=map.get("servername").toString();
	    	String logtype="正常";
	    	if(map.get("logtype").toString().trim().equals("2")) {
	    		logtype="错误";
	    	}else if(map.get("logtype").toString().trim().equals("3")){
	    		logtype="警告";
	    	}
	    	 //String loginfo=map.get("loginfo").toString();
	    	index++;
	    	
	    	Object[] row = new Object[6];
			row[logModel.findColumn("序号")] = index+"";
			row[logModel.findColumn("当前时间")] =sdf.format( new Date());
			row[logModel.findColumn("任务编号")] = serverno;
			row[logModel.findColumn("任务名称")]=servername;
			row[logModel.findColumn("日志级别")]=logtype;
			row[logModel.findColumn("日志信息")]=map.get("loginfo");
			logModel.insertRow(0, row);
			
			if(logModel.getRowCount() > 1200){
				logModel.removeRow(200);
			}
			makeFace(logtable);
	    	
	    }
      /**
       * 改变行的背景色
       * @param table
       * add lhy
       */
	    public static void makeFace(JTable table) {
		    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
		        public Component getTableCellRendererComponent(JTable table, Object value,  boolean isSelected, boolean hasFocus, int row, int column) {
		        	
		              if (table.getModel().getValueAt(row,logModel.findColumn("日志级别")).toString().trim().equalsIgnoreCase("错误")) {
		            	  setBackground(Color.red); 
		              }else if (table.getModel().getValueAt(row,logModel.findColumn("日志级别")).toString().trim().equalsIgnoreCase("警告")) {
		            	  setBackground(Color.yellow); 
		              }else {  
		            	  setBackground(Color.white); 
		              }
		               return super.getTableCellRendererComponent(table, value,
		                                isSelected, hasFocus, row, column);
		        }};
		        for (int i = 0; i < table.getColumnCount(); i++) {
		                table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
		        }
		}
}
