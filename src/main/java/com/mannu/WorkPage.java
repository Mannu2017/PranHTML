package com.mannu;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WorkPage {
	private Connection conn;
	String ackno,drnno,upldate,status;
	private String[] args;

	public WorkPage() {
		conn=DbCon.getConnection();
		File sorfol=new File("C:\\Pran HTML\\");
		File[] htmlfiles=sorfol.listFiles();
		for(File html : htmlfiles) {
			if(html.getName().endsWith(".html") || html.getName().endsWith(".HTML")) {
				System.out.println("File Name: "+html.getName());
				File input=html.getAbsoluteFile();
				try {
					Document doc = Jsoup.parse(input, "UTF-8");
					Elements trs=doc.select("TR");
					for (int i = 0; i < trs.size(); i++) {
						Elements tds=trs.get(i).select("TD");
						if(tds.size()>5 && tds.get(7).text().length()==17) {
							ackno=tds.get(7).text();
							drnno=tds.get(2).text();
							String[] upld= tds.get(5).text().split("\\ ");
							if(upld.length>1) {
								upldate=upld[2].substring(4, 8)+"-"+upld[2].substring(2, 4)+"-"+upld[2].substring(0, 2);
							} else {
								upldate=tds.get(5).text();
							}
							
							status=tds.get(12).text();
							System.out.println("Mannu Data: "+trs.size()+"   Ack NO:"+ackno+"   DRN: "+drnno+"  Upload Date:"+upldate);
							try {
								if(conn.isClosed()) {
									conn=DbCon.getConnection();
								}
								PreparedStatement ps=conn.prepareStatement("PranHTMLins '"+ackno+"','"+drnno+"','"+upldate+"','"+html.getName()+"','"+status+"'");
								ps.execute();
								ps.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					doc.clone();
					DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
					String cd=df.format(new Date());
					if(!new File(sorfol+"//"+cd).exists()) {
						new File(sorfol+"//"+cd).mkdir();
					}
					
					File mv=new File(html.getAbsolutePath());
					mv.renameTo(new File(sorfol+"//"+cd+"//"+html.getName()));
					
				} catch (Exception e) {
					System.out.println("Error: "+e);
				}
				
			}
			
		}
		
	}
	
	public void setArgs(String[] args) {
		this.args=args;
	}

}
