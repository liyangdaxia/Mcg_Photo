package com.mcg.vm.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
* 实现目标是输入匹配字段，显示本地或者本工程目录指定文件夹下的相关指定名开头的图片文件
**/



public class ShowPhotoesByNameServlet extends HttpServlet{

	private String bolid;
	private static final long serialVersionUID = 1L;
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,FileNotFoundException{
		// 遍历文件
		bolid =req.getParameter("bolid");
		req.setCharacterEncoding("utf-8");  //设置编码
        InputStream instream = new ShowPhotoesByNameServlet().getClass().getResourceAsStream("/properties/uploadfile.properties"); 
    	Properties prop = new Properties();
    	prop.load(instream);
		//注释的方式是从本地某盘下面读取图片到jsp页面展示，可用。
    	//String path= prop.getProperty("dir1")+File.separator +prop.getProperty("dir2")+File.separator +prop.getProperty("dir3");
    	String path=req.getSession().getServletContext().getRealPath("")+File.separator+"images";;
	    List<File> mlist = null;
	    List<String> list=new ArrayList<String>();
	    File mFile = new File(path);
		if("".equals(bolid)){
			list.add("timg.jpg");
		}else{
        if (mFile.exists() && mFile.isDirectory()) {
        	mlist= new ArrayList<File>();
            getAllFile(mFile, mlist,bolid);
            // 已经获取了所有图片
            if(mlist.isEmpty()){
            	list.add("timg.jpg");
            }else{
            	for (File file2 : mlist) {
                    list.add(file2.getName());
                }
            }
            
        }
		}
        HttpSession session=req.getSession();
        session.setAttribute("mlist",list);
        req.getRequestDispatcher("/showphotos.jsp").forward(req,resp);


	}
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
	
	 private static void getAllFile(File mFile, List<File> mlist,String bolid) {
	        // 1.获取子目录
	        File[] files = mFile.listFiles();
	        // 2.判断files是否是空的 否则程序崩溃
	        
	        if (files != null) {

	            for (File file : files) {
	                if (file.isDirectory()) {
	                    getAllFile(file, mlist,bolid);//调用递归的方式
	                } else {
	                    // 4. 添加到集合中去
	                    String fileName = file.getName();
	                    if (fileName.endsWith(".jpg")&&fileName.startsWith(bolid)) {
	                        mlist.add(file);//如果是这几种图片格式就添加进去
	                    }
	                }
	            }
	        }
	    }


}
