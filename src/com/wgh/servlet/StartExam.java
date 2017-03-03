package com.wgh.servlet;

import com.wgh.tools.StringUtil;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author administrator
 */
public class StartExam extends HttpServlet {

    protected int examTime=10;   //考试时间
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=GBK");
        String action = request.getParameter("action");
        if("startExam".equals(action)){
            this.startExam(request,response);   //开始考试
        }else if("showStartTime".equals(action)){//显示考试时间
			this.showStartTime(request,response);
		}else if("showRemainTime".equals(action)){//显示剩余时间
			this.showRemainTime(request,response);
		}
    }
    public void startExam(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
            HttpSession session = request.getSession();
            request.setAttribute("time", examTime);     //保存考试时间
            session.setAttribute("startTime",new java.util.Date().getTime());	//保存当前时间的毫秒数
            request.getRequestDispatcher("/startExam.jsp").forward(request, response);
    }
    // 显示考试计时

    public void showStartTime(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=GBK");
		HttpSession session = request.getSession();
		String startTime=session.getAttribute("startTime").toString();
        StringUtil su=new StringUtil();
		long a=Long.parseLong(startTime);		//将开始时间转换为毫秒数
		long b=new java.util.Date().getTime();	//获取当前时间的毫秒数
		int h=(int)Math.abs((b-a)/3600000);		//获取小时
		String hour=su.formatNO(h,2);			//将小时格式化为两位
		int m=(int)(b-a)%3600000/60000;			//获取分钟
		String minute=su.formatNO(m,2);			//将分钟格式化为两位
		int s=(int)((b-a)%3600000)%60000/1000;	//获取秒数
		String second=su.formatNO(s,2);			//将秒数格式化为两位
		String time=hour+":"+minute+":"+second;	//组合已用时间
		request.setAttribute("showStartTime",time);//将生成的时间保存到showStartTime参数中
        request.getRequestDispatcher("/showStartTime.jsp").forward(request, response);	//重定向页面
    }

    public void showRemainTime(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=GBK");
		HttpSession session = request.getSession();
		String startTime=session.getAttribute("startTime").toString();
        StringUtil su=new StringUtil();
		long a=Long.parseLong(startTime);		//获取开始时间的毫秒数
		long b=new java.util.Date().getTime();	//获取当前时间的毫秒数
		long r=examTime*60000-(b-a-1000);		//计算考试剩余时间的毫秒数
		int h=(int)Math.abs(r/3600000);			//计算小时
		String hour=su.formatNO(h,2);			//将小时格式化为两位
		int m=(int)(r)%3600000/60000;		//计算分钟
		String minute=su.formatNO(m,2);		//将分钟格式化为两位
		int s=(int)((r)%3600000)%60000/1000;	//计算秒数
		String second=su.formatNO(s,2);			//将秒数格式化为两位小数
		String time=hour+":"+minute+":"+second;		//组合剩余时间
		request.setAttribute("showRemainTime",time);	//将生成的时间保存到showRemainTime参数中
        request.getRequestDispatcher("/showRemainTime.jsp").forward(request, response);		//重定向页面
    }
        public void init() throws ServletException {
        examTime=Integer.parseInt(getInitParameter("examTime"));        //获取配置文件中设置的考试时间
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,
            IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,
            IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    }
