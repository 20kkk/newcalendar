package newcalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
 
public class MyCalendar extends JFrame implements ActionListener {
    private static final String Default = null;
	private JPanel p1,p2,p3; //三个面板 p1最上面 p2中间 p3下面
    private JLabel yearStr,monthStr; //标签
    private JTextField inputYear,inputMonth;
    private JButton confirm;  //确认
    private JButton lastMonth;
    private JButton nextMonth;
    private JLabel dayText;//文本框
    private JLabel TimeText;//文本框
    //p2面板里控件的声明
    private String[] week = {"日","一","二","三","四","五","六"};
    private JLabel[] weekLable = new JLabel[week.length];//数组的声明
    //p3面板的42个按钮声明
    private JButton[] dayBtn = new JButton[42];
 
    private Calendar nowDate = new GregorianCalendar(); //Calendar是抽象类 new不出 用直接子类
    private int nowYear = nowDate.get(Calendar.YEAR);
    private int nowMonth = nowDate.get(Calendar.MONTH);
 
    public MyCalendar() throws HeadlessException {
        p1=new JPanel();
        p2=new JPanel();
        p3=new JPanel();
        yearStr = new JLabel("年份:");
        //设置字体大小
        yearStr.setFont(new Font("宋体", Font.BOLD, 25));
        //设置文本框的长度为6
        inputYear = new JTextField(6);
        //设置文本框的大小
        inputYear.setPreferredSize(new Dimension (6,35));
        inputYear.setFont(new Font("宋体", Font.BOLD, 25));
       // JPasswordField mmk = new JPasswordField(6);
        monthStr = new JLabel("月份:");
        //设置字体大小
        monthStr.setFont(new Font("宋体", Font.BOLD, 25));
        //创建文本框
        inputMonth = new JTextField(6);
      //设置文本框的大小
        inputMonth.setPreferredSize(new Dimension (6,35));
        inputMonth.setFont(new Font("宋体", Font.BOLD, 25));
        confirm = new JButton("确认");
       confirm.setFont(new Font("宋体", Font.BOLD, 25));
        lastMonth = new JButton("上月");
        lastMonth.setFont(new Font("宋体", Font.BOLD, 25));
        nextMonth = new JButton("下月");
        nextMonth.setFont(new Font("宋体", Font.BOLD, 25));
 
        dayText = new JLabel();
        TimeText = new JLabel();
 
        new Thread(){    //线程内部类用来实时显示时间   
            public void run(){
                while(true) {
                	//从系统时钟获取当前的日期时间
                    LocalDateTime dateTime = LocalDateTime.now();
                    //格式化显示(自定义格式)
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); //大写的HH是24小时制的
                    //定义nowTime为定义的格式
                    String nowTime = dateTime.format(formatter);
                    //将实时时间添加到标签TimeText
                    TimeText.setText(nowTime);
                    //定义异常，程序暂停1000ms
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        //在命令行打印异常信息在程序中出错的位置及原因
                    }
                }
            }
        }.start();
    }
 
    //初始化的方法，定义布局方式
    public void init(){
        this.setTitle("日历");
        this.setBounds(800,400,800,800);
        this.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
        this.add(p1,BorderLayout.NORTH);
        this.add(p2,BorderLayout.CENTER);
        this.add(p3,BorderLayout.SOUTH);
 
        //最上面的面板p1
        p1.setLayout(new FlowLayout());//流布局默认居中
        p1.add(yearStr);
        p1.add(inputYear);
        //在文本框添加获取的实时时间的年份
        inputYear.setText(nowYear+"");
        p1.add(monthStr);
        p1.add(inputMonth);
      //在文本框添加获取的实时时间的月份
        inputMonth.setText(nowMonth+1+"");
        p1.add(confirm);
        //定义按钮“确认”的监听事件
        confirm.addActionListener(new ActionListener() {
            @Override
            //actionPerformed可以执行对定义的监听事件
            public void actionPerformed(ActionEvent e) {
            	//将获取的字符转化为整形并存储在inputyear里面
                int inputyear = Integer.parseInt(inputYear.getText());
                if(inputyear<=1900){
                	//当年份不在范围内的话弹出警告对话框
                    JOptionPane.showMessageDialog(null,"请输入正确的年份(1900-2300)");
                    //然后将文本框定义为空
                    inputYear.setText(null);
                    return;
                }
              //月份
                int inputmonth = Integer.parseInt(inputMonth.getText());
                if (inputmonth<=0||inputmonth>=13){
                    JOptionPane.showMessageDialog(null,"请输入正确的月份(1-12)");
                    inputMonth.setText(null);
                    return;
                }
                for (int i=0;i<42;i++){
                    dayBtn[i].setEnabled(true);
                    //设置字体颜色为黑色
                    dayBtn[i].setForeground(Color.black);
                    //设置按钮背景颜色
                    dayBtn[i].setBackground(new Color(240, 239, 239));
                }
                setDay(inputyear,inputmonth);
                //给标签dayText上面显示实时时间
                dayText.setText(inputYear.getText()+"/"+inputMonth.getText()+"/"+nowDate.get(Calendar.DAY_OF_MONTH)+"  ");
            }
        });
        //创建图片对象
        	//ImageIcon img = new ImageIcon("C:\\Users\\lenovo\\Pictures\\Saved Pictures\\QQ图片20220407174543.jpg"); 
        	//JLabel jl_bg = new JLabel(img); //背景
        	//放置在最底层
        	//this.getLayeredPane().add(jl_bg, new Integer(Integer.MIN_VALUE));  
        	//((JPanel)this.getContentPane()).setOpaque(false); //设置透明 
        	//p1.add(jl_bg);
        p1.add(lastMonth);
        //定义上月的监听事件
        lastMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i=0;i<42;i++){
                    dayBtn[i].setEnabled(true);
                    dayBtn[i].setForeground(Color.black);
                    dayBtn[i].setBackground(new Color(240, 239, 239));
                }
                if ((Integer.parseInt(inputMonth.getText()))==1){
                	//如果是一月的话，则年份减一，月份定义为12
                    setDay(Integer.parseInt(inputYear.getText())-1,12);
                    //将年份和月份放置在文本框中
                    inputYear.setText(Integer.parseInt(inputYear.getText())-1+"");
                    inputMonth.setText(12+"");
                }else{
                	//否则年份不变，月份减一
                    setDay(Integer.parseInt(inputYear.getText()),Integer.parseInt(inputMonth.getText())-1);
                    inputMonth.setText(Integer.parseInt(inputMonth.getText())-1+"");
                }
                //设置标签dayText中的年月信息
                dayText.setText(inputYear.getText()+"/"+inputMonth.getText()+"/"+nowDate.get(Calendar.DAY_OF_MONTH)+"  ");
            }
        });
        p1.add(nextMonth);
        //定义下月的监听
        nextMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i=0;i<42;i++){
                    dayBtn[i].setEnabled(true);
                    dayBtn[i].setForeground(Color.black);
                    dayBtn[i].setBackground(new Color(240, 239, 239));
                }
                //当月份是十二月的时候，年份加一，月份设置为1
                if ((Integer.parseInt(inputMonth.getText()))==12) {
                    setDay(Integer.parseInt(inputYear.getText()) + 1, 1);
                    inputYear.setText(Integer.parseInt(inputYear.getText()) + 1 + "");
                    inputMonth.setText(1 + "");
                }else{
                	//否则设置年份不变，月份加1
                    setDay(Integer.parseInt(inputYear.getText()),Integer.parseInt(inputMonth.getText())+1);
                    //将月份设置在文本框里面
                    inputMonth.setText(Integer.parseInt(inputMonth.getText())+1+"");
                }
                //更新标签dayText里面的月份数据
                dayText.setText(inputYear.getText()+"/"+inputMonth.getText()+"/"+nowDate.get(Calendar.DAY_OF_MONTH)+"  ");
            }
        });
        p1.add(dayText);
        p1.add(TimeText);
     //设置实时时间
        dayText.setText(nowYear+"/"+(nowMonth+1)+"/"+nowDate.get(Calendar.DAY_OF_MONTH)+"  ");
 
        //中间的面板p2
        p2.setLayout(new GridLayout(1,7,2,2)); //1行7列上下间距2
        //面板里控件赋值
        //设置week里面的日期大小
        for (int i=0;i<weekLable.length;i++){
            weekLable[i] = new JLabel(week[i]);
            weekLable[i].setHorizontalAlignment(SwingConstants.CENTER);//文字水平居中
            weekLable[i].setFont(new Font("黑体",Font.BOLD,25));//Font.PLAIN
            p2.add(weekLable[i]);
        }
 
        //下面的面板p3
        p3.setLayout(new GridLayout(6,7,2,2));
        //p3的按钮加进去
        for (int i=0;i<42;i++){
            dayBtn[i]=new JButton(""+i); //暴力创建 强行改字符串
            Font f = new Font(Default,Font.BOLD,23);
            dayBtn[i].setFont(f);
            //JPanel contentPanel = new JPanel();
           	//contentPanel.setBackground(Color.RED);
           	//setContentPane(contentPanel);
           //	ImageIcon icon = new ImageIcon("C:\\Users\\lenovo\\Pictures\\Saved Pictures\\小.jpg");
        	//dayBtn[i] = new JButton("i",icon);
        	//dayBtn[i].setMargin(new Insets(0,0,0,0));
            dayBtn[i].addActionListener(this);//给每个事件监听
            Dimension preferredSize = new Dimension(30,100);//设置尺寸
           dayBtn[i].setPreferredSize(preferredSize );
           
         
            //dayBtn[i].setPreferredSize(5);
            p3.add(dayBtn[i]);
        }
 
        setDay(Integer.parseInt(inputYear.getText()),Integer.parseInt(inputMonth.getText()));
        this.setVisible(true); //显示窗口
        this.setResizable(false);//窗口不可拉伸
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置窗口关闭事件
    }
 
    //确定日历上每天的方法
    public void setDay(int yearSel,int monthSel){
 
        GregorianCalendar dateSel = new GregorianCalendar(yearSel,monthSel-1,1); //构造器传选择那个月的1号
        int weeknum = dateSel.get(Calendar.DAY_OF_WEEK);  //然后通过方法获取选择月1号是星期几  要减去1 外国人从星期天开始
        boolean isLeep = dateSel.isLeapYear(yearSel);  //dateSel里面的方法：传入年，即可判断是否为闰年
 
        int dayNum = getMonthDay(isLeep,monthSel);  //选择该年该月的天数
 
        int lastdayNum = getMonthDay(isLeep,monthSel-1);
        //根据某月的天数 以及某月的1号是星期几，改变按钮上的字
        //当前月1号之前的
        int count1 = lastdayNum;
        for (int i=weeknum-2;i>=0;i--,count1--){
            dayBtn[i].setText(Integer.toString(count1));
            dayBtn[i].setEnabled(false);//按钮不可以按
            dayBtn[i].setForeground(Color.gray); //设置字体颜色为灰色
        }
        //当前月的日历
        int count2 = 1;
        for (int i=weeknum-1;i<weeknum-1+dayNum;i++,count2++){
            dayBtn[i].setText(Integer.toString(count2));
            dayBtn[i].setBackground(Color.WHITE);
        }
        //当前月之后的
        int count3= 1 ;
        for (int i=weeknum-1+dayNum;i<42;i++,count3++){
            dayBtn[i].setText(Integer.toString(count3));
            dayBtn[i].setEnabled(false);//按钮不可以按
            dayBtn[i].setForeground(Color.gray);//设置字体颜色为灰色
        }
    }
 
    //得到该年该月是多少天
    private int getMonthDay(boolean isleepYear,int month){
        int daynum = 0;
        if (isleepYear){ //是闰年
            if (month==2){
                daynum = 29;
            }else if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
                daynum = 31;
            }else{ //小月
                daynum = 30;
            }
        }else{
            if (month==2){
                daynum = 28;
            }else if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
                daynum = 31;
            }else{ //小月
                daynum = 30;
            }
        }
        return daynum;
    }
 //如果点击日期的按钮则会更新标签中的信息
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i=0;i<42;i++){
            if (e.getSource()==dayBtn[i]){ //事件源点了
                dayText.setText(inputYear.getText()+"/"+inputMonth.getText()+"/"+dayBtn[i].getText()+"  ");
            }
        }
    }
}

