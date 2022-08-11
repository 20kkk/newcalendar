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
	private JPanel p1,p2,p3; //������� p1������ p2�м� p3����
    private JLabel yearStr,monthStr; //��ǩ
    private JTextField inputYear,inputMonth;
    private JButton confirm;  //ȷ��
    private JButton lastMonth;
    private JButton nextMonth;
    private JLabel dayText;//�ı���
    private JLabel TimeText;//�ı���
    //p2�����ؼ�������
    private String[] week = {"��","һ","��","��","��","��","��"};
    private JLabel[] weekLable = new JLabel[week.length];//���������
    //p3����42����ť����
    private JButton[] dayBtn = new JButton[42];
 
    private Calendar nowDate = new GregorianCalendar(); //Calendar�ǳ����� new���� ��ֱ������
    private int nowYear = nowDate.get(Calendar.YEAR);
    private int nowMonth = nowDate.get(Calendar.MONTH);
 
    public MyCalendar() throws HeadlessException {
        p1=new JPanel();
        p2=new JPanel();
        p3=new JPanel();
        yearStr = new JLabel("���:");
        //���������С
        yearStr.setFont(new Font("����", Font.BOLD, 25));
        //�����ı���ĳ���Ϊ6
        inputYear = new JTextField(6);
        //�����ı���Ĵ�С
        inputYear.setPreferredSize(new Dimension (6,35));
        inputYear.setFont(new Font("����", Font.BOLD, 25));
       // JPasswordField mmk = new JPasswordField(6);
        monthStr = new JLabel("�·�:");
        //���������С
        monthStr.setFont(new Font("����", Font.BOLD, 25));
        //�����ı���
        inputMonth = new JTextField(6);
      //�����ı���Ĵ�С
        inputMonth.setPreferredSize(new Dimension (6,35));
        inputMonth.setFont(new Font("����", Font.BOLD, 25));
        confirm = new JButton("ȷ��");
       confirm.setFont(new Font("����", Font.BOLD, 25));
        lastMonth = new JButton("����");
        lastMonth.setFont(new Font("����", Font.BOLD, 25));
        nextMonth = new JButton("����");
        nextMonth.setFont(new Font("����", Font.BOLD, 25));
 
        dayText = new JLabel();
        TimeText = new JLabel();
 
        new Thread(){    //�߳��ڲ�������ʵʱ��ʾʱ��   
            public void run(){
                while(true) {
                	//��ϵͳʱ�ӻ�ȡ��ǰ������ʱ��
                    LocalDateTime dateTime = LocalDateTime.now();
                    //��ʽ����ʾ(�Զ����ʽ)
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); //��д��HH��24Сʱ�Ƶ�
                    //����nowTimeΪ����ĸ�ʽ
                    String nowTime = dateTime.format(formatter);
                    //��ʵʱʱ����ӵ���ǩTimeText
                    TimeText.setText(nowTime);
                    //�����쳣��������ͣ1000ms
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        //�������д�ӡ�쳣��Ϣ�ڳ����г����λ�ü�ԭ��
                    }
                }
            }
        }.start();
    }
 
    //��ʼ���ķ��������岼�ַ�ʽ
    public void init(){
        this.setTitle("����");
        this.setBounds(800,400,800,800);
        this.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
        this.add(p1,BorderLayout.NORTH);
        this.add(p2,BorderLayout.CENTER);
        this.add(p3,BorderLayout.SOUTH);
 
        //����������p1
        p1.setLayout(new FlowLayout());//������Ĭ�Ͼ���
        p1.add(yearStr);
        p1.add(inputYear);
        //���ı�����ӻ�ȡ��ʵʱʱ������
        inputYear.setText(nowYear+"");
        p1.add(monthStr);
        p1.add(inputMonth);
      //���ı�����ӻ�ȡ��ʵʱʱ����·�
        inputMonth.setText(nowMonth+1+"");
        p1.add(confirm);
        //���尴ť��ȷ�ϡ��ļ����¼�
        confirm.addActionListener(new ActionListener() {
            @Override
            //actionPerformed����ִ�жԶ���ļ����¼�
            public void actionPerformed(ActionEvent e) {
            	//����ȡ���ַ�ת��Ϊ���β��洢��inputyear����
                int inputyear = Integer.parseInt(inputYear.getText());
                if(inputyear<=1900){
                	//����ݲ��ڷ�Χ�ڵĻ���������Ի���
                    JOptionPane.showMessageDialog(null,"��������ȷ�����(1900-2300)");
                    //Ȼ���ı�����Ϊ��
                    inputYear.setText(null);
                    return;
                }
              //�·�
                int inputmonth = Integer.parseInt(inputMonth.getText());
                if (inputmonth<=0||inputmonth>=13){
                    JOptionPane.showMessageDialog(null,"��������ȷ���·�(1-12)");
                    inputMonth.setText(null);
                    return;
                }
                for (int i=0;i<42;i++){
                    dayBtn[i].setEnabled(true);
                    //����������ɫΪ��ɫ
                    dayBtn[i].setForeground(Color.black);
                    //���ð�ť������ɫ
                    dayBtn[i].setBackground(new Color(240, 239, 239));
                }
                setDay(inputyear,inputmonth);
                //����ǩdayText������ʾʵʱʱ��
                dayText.setText(inputYear.getText()+"/"+inputMonth.getText()+"/"+nowDate.get(Calendar.DAY_OF_MONTH)+"  ");
            }
        });
        //����ͼƬ����
        	//ImageIcon img = new ImageIcon("C:\\Users\\lenovo\\Pictures\\Saved Pictures\\QQͼƬ20220407174543.jpg"); 
        	//JLabel jl_bg = new JLabel(img); //����
        	//��������ײ�
        	//this.getLayeredPane().add(jl_bg, new Integer(Integer.MIN_VALUE));  
        	//((JPanel)this.getContentPane()).setOpaque(false); //����͸�� 
        	//p1.add(jl_bg);
        p1.add(lastMonth);
        //�������µļ����¼�
        lastMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i=0;i<42;i++){
                    dayBtn[i].setEnabled(true);
                    dayBtn[i].setForeground(Color.black);
                    dayBtn[i].setBackground(new Color(240, 239, 239));
                }
                if ((Integer.parseInt(inputMonth.getText()))==1){
                	//�����һ�µĻ�������ݼ�һ���·ݶ���Ϊ12
                    setDay(Integer.parseInt(inputYear.getText())-1,12);
                    //����ݺ��·ݷ������ı�����
                    inputYear.setText(Integer.parseInt(inputYear.getText())-1+"");
                    inputMonth.setText(12+"");
                }else{
                	//������ݲ��䣬�·ݼ�һ
                    setDay(Integer.parseInt(inputYear.getText()),Integer.parseInt(inputMonth.getText())-1);
                    inputMonth.setText(Integer.parseInt(inputMonth.getText())-1+"");
                }
                //���ñ�ǩdayText�е�������Ϣ
                dayText.setText(inputYear.getText()+"/"+inputMonth.getText()+"/"+nowDate.get(Calendar.DAY_OF_MONTH)+"  ");
            }
        });
        p1.add(nextMonth);
        //�������µļ���
        nextMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i=0;i<42;i++){
                    dayBtn[i].setEnabled(true);
                    dayBtn[i].setForeground(Color.black);
                    dayBtn[i].setBackground(new Color(240, 239, 239));
                }
                //���·���ʮ���µ�ʱ����ݼ�һ���·�����Ϊ1
                if ((Integer.parseInt(inputMonth.getText()))==12) {
                    setDay(Integer.parseInt(inputYear.getText()) + 1, 1);
                    inputYear.setText(Integer.parseInt(inputYear.getText()) + 1 + "");
                    inputMonth.setText(1 + "");
                }else{
                	//����������ݲ��䣬�·ݼ�1
                    setDay(Integer.parseInt(inputYear.getText()),Integer.parseInt(inputMonth.getText())+1);
                    //���·��������ı�������
                    inputMonth.setText(Integer.parseInt(inputMonth.getText())+1+"");
                }
                //���±�ǩdayText������·�����
                dayText.setText(inputYear.getText()+"/"+inputMonth.getText()+"/"+nowDate.get(Calendar.DAY_OF_MONTH)+"  ");
            }
        });
        p1.add(dayText);
        p1.add(TimeText);
     //����ʵʱʱ��
        dayText.setText(nowYear+"/"+(nowMonth+1)+"/"+nowDate.get(Calendar.DAY_OF_MONTH)+"  ");
 
        //�м�����p2
        p2.setLayout(new GridLayout(1,7,2,2)); //1��7�����¼��2
        //�����ؼ���ֵ
        //����week��������ڴ�С
        for (int i=0;i<weekLable.length;i++){
            weekLable[i] = new JLabel(week[i]);
            weekLable[i].setHorizontalAlignment(SwingConstants.CENTER);//����ˮƽ����
            weekLable[i].setFont(new Font("����",Font.BOLD,25));//Font.PLAIN
            p2.add(weekLable[i]);
        }
 
        //��������p3
        p3.setLayout(new GridLayout(6,7,2,2));
        //p3�İ�ť�ӽ�ȥ
        for (int i=0;i<42;i++){
            dayBtn[i]=new JButton(""+i); //�������� ǿ�и��ַ���
            Font f = new Font(Default,Font.BOLD,23);
            dayBtn[i].setFont(f);
            //JPanel contentPanel = new JPanel();
           	//contentPanel.setBackground(Color.RED);
           	//setContentPane(contentPanel);
           //	ImageIcon icon = new ImageIcon("C:\\Users\\lenovo\\Pictures\\Saved Pictures\\С.jpg");
        	//dayBtn[i] = new JButton("i",icon);
        	//dayBtn[i].setMargin(new Insets(0,0,0,0));
            dayBtn[i].addActionListener(this);//��ÿ���¼�����
            Dimension preferredSize = new Dimension(30,100);//���óߴ�
           dayBtn[i].setPreferredSize(preferredSize );
           
         
            //dayBtn[i].setPreferredSize(5);
            p3.add(dayBtn[i]);
        }
 
        setDay(Integer.parseInt(inputYear.getText()),Integer.parseInt(inputMonth.getText()));
        this.setVisible(true); //��ʾ����
        this.setResizable(false);//���ڲ�������
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//���ô��ڹر��¼�
    }
 
    //ȷ��������ÿ��ķ���
    public void setDay(int yearSel,int monthSel){
 
        GregorianCalendar dateSel = new GregorianCalendar(yearSel,monthSel-1,1); //��������ѡ���Ǹ��µ�1��
        int weeknum = dateSel.get(Calendar.DAY_OF_WEEK);  //Ȼ��ͨ��������ȡѡ����1�������ڼ�  Ҫ��ȥ1 ����˴������쿪ʼ
        boolean isLeep = dateSel.isLeapYear(yearSel);  //dateSel����ķ����������꣬�����ж��Ƿ�Ϊ����
 
        int dayNum = getMonthDay(isLeep,monthSel);  //ѡ�������µ�����
 
        int lastdayNum = getMonthDay(isLeep,monthSel-1);
        //����ĳ�µ����� �Լ�ĳ�µ�1�������ڼ����ı䰴ť�ϵ���
        //��ǰ��1��֮ǰ��
        int count1 = lastdayNum;
        for (int i=weeknum-2;i>=0;i--,count1--){
            dayBtn[i].setText(Integer.toString(count1));
            dayBtn[i].setEnabled(false);//��ť�����԰�
            dayBtn[i].setForeground(Color.gray); //����������ɫΪ��ɫ
        }
        //��ǰ�µ�����
        int count2 = 1;
        for (int i=weeknum-1;i<weeknum-1+dayNum;i++,count2++){
            dayBtn[i].setText(Integer.toString(count2));
            dayBtn[i].setBackground(Color.WHITE);
        }
        //��ǰ��֮���
        int count3= 1 ;
        for (int i=weeknum-1+dayNum;i<42;i++,count3++){
            dayBtn[i].setText(Integer.toString(count3));
            dayBtn[i].setEnabled(false);//��ť�����԰�
            dayBtn[i].setForeground(Color.gray);//����������ɫΪ��ɫ
        }
    }
 
    //�õ���������Ƕ�����
    private int getMonthDay(boolean isleepYear,int month){
        int daynum = 0;
        if (isleepYear){ //������
            if (month==2){
                daynum = 29;
            }else if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
                daynum = 31;
            }else{ //С��
                daynum = 30;
            }
        }else{
            if (month==2){
                daynum = 28;
            }else if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
                daynum = 31;
            }else{ //С��
                daynum = 30;
            }
        }
        return daynum;
    }
 //���������ڵİ�ť�����±�ǩ�е���Ϣ
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i=0;i<42;i++){
            if (e.getSource()==dayBtn[i]){ //�¼�Դ����
                dayText.setText(inputYear.getText()+"/"+inputMonth.getText()+"/"+dayBtn[i].getText()+"  ");
            }
        }
    }
}

