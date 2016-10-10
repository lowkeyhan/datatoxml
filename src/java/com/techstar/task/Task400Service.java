package com.techstar.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techstar.sys.config.Global;

public class Task400Service extends TimerTask{

	@Autowired
	private LocalContainerEntityManagerFactoryBean entityManagerFactory;
	
	public void excute () throws Exception  {	
		
		
	}
	public void getupdatexml() throws IOException, DocumentException{		
  		Connection conn = null;
  		PreparedStatement pstm = null;
  		ResultSet rs =null;
  		//获得当前时间
  		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH时mm分ss秒");
		String dateString = formatter.format(currentTime);
  		try {
  			StringBuffer sql = new StringBuffer("select * from EnergyReport100");
  			conn = this.entityManagerFactory.getDataSource().getConnection();
  			pstm = conn.prepareStatement(sql.toString());
  			rs = pstm.executeQuery();
  			String[] tablcol=Global.getConfig("tablecol").split(",");
  			Document document = DocumentHelper.createDocument();
  			Element root = document.addElement("root");
  			while(rs.next()){
  				//Ids+="," + rs.getObject(1).toString();
  				 for (String colname : tablcol) {
  					Element author = root.addElement(colname.replace("/", ""));
    				  author.addText(rs.getString(colname));
					
				}
  				//保存时间功率数据
  				//Document timedataDocument=DocumentHelper.createDocument();
  				Document timedataDocument=xmlhelper.WriteFiletodoc();
  				Element rootElement=timedataDocument.getRootElement();
  				Element RowDataElement = rootElement.addElement("RowData");
  				Element TimeElement = RowDataElement.addElement("Time");
  				TimeElement.addText(dateString);
  				Element DataElement = RowDataElement.addElement("Data");
  				DataElement.addText(rs.getString("AA14/K"));
  				 xmlhelper.XMLWrite(timedataDocument,Global.getConfig("timedatapath"));
  				/*RowMetaAndData
  				 <RowData>
  				<Time>16时00分00秒</Time>
  				<Data>0</Data>
  			  </RowData>*/
  				 
  				 
  			}
  			 xmlhelper.XMLWrite(document);
  			System.out.println("写入数据完成"+new Date());
  		} catch (SQLException e) {
  			e.printStackTrace();
  		} finally{
  			try {
  				if(rs!=null)
  					rs.close();
  				if(pstm!=null)
  					pstm.close();
  				if(conn!=null)
  					conn.close();
  			} catch (Exception e2) {
  				e2.printStackTrace();
  			}
  		}
  		
  	}
	@Override
	public void run() {
		try{
			System.out.println("定时任务开始"+new Date());
			getupdatexml();
			System.out.println("定时任务结束"+new Date());
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally{

		}
	}



}