/*
 * You need to implement Calendar GUI here!
 * show the calendar of month of today.
 * jump to last/next month's calendar
 * jump to last/next year's calendar
 *
 * jump to one specific day's calendar
 * */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Display extends JFrame implements ActionListener {
    private JLabel yearLabel = new JLabel("年份：");
    private JComboBox yearComboBox = new JComboBox();
    private JLabel monthLabel = new JLabel("月份：");
    private JComboBox monthComBox = new JComboBox();
    private JButton seeButton = new JButton("查看");
    private JButton todayButton = new JButton("今天");
    private JButton memoButton = new JButton("备忘");
    private JButton[] titleName = new JButton[7];
    private JButton buttonDay[] = new JButton[42];
    private JTextField text = new JTextField(8);
    private JButton checkButton = new JButton("查询");
    private String[] name = {"日", "一", "二", "三", "四", "五", "六"};
    private Events events = new Events();

    private JTextField dateAddInDay = new JTextField();
    private JTextField messageAddInDay = new JTextField();
    private JButton addInDay = new JButton("按天添加");
    private JTextField textAddInPeriod1 = new JTextField();
    private JTextField messageAddInPeriod = new JTextField();
    private JTextField textAddInPeriod2 = new JTextField();
    private JButton addInPeriod = new JButton("按时间段添加");

    private JTextField textQueryInDay = new JTextField();
    private JButton queryInDay = new JButton("按天查询");
    private JTextField textQueryInPeriod1 = new JTextField();
    private JTextField textQueryInPeriod2 = new JTextField();
    private JButton queryInPeriod = new JButton("按时间段查询");

    private JFrame frame;
    private JFrame JF;
    private ArrayList<Event> eventArrayList = new ArrayList<>();

    private JButton[] delete;

    private JComboBox addType = new JComboBox();

    public Display() {
        init();
        addInDay.addActionListener(this::actionPerformed);
        addInPeriod.addActionListener(this::actionPerformed);
        queryInDay.addActionListener(this::actionPerformed);
        queryInPeriod.addActionListener(this::actionPerformed);
        addType.addActionListener(this::actionPerformed);
        addType.addItem("normal");
        addType.addItem("meeting");
        addType.addItem("appointment");
    }

    /**
     * Init the UI Windows here. For example, the frame, some panels and buttons.
     */
    private void init() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame jf = new JFrame("日历");
        jf.setResizable(false);
        jf.setSize(600, 500);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //添加上部面板
        JPanel pNorth = new JPanel();
        pNorth.add(yearLabel);
        for (int year = 1800; year < 2101; year++) {
            yearComboBox.addItem(year);
        }
        pNorth.add(yearComboBox);
        pNorth.add(monthLabel);
        for (int month = 1; month < 13; month++) {
            monthComBox.addItem(month);
        }
        pNorth.add(monthComBox);
        pNorth.add(seeButton);
        seeButton.addActionListener(this);
        pNorth.add(todayButton);
        todayButton.addActionListener(this);
        pNorth.add(memoButton);
        memoButton.addActionListener(this);
        //添加中部面板

        JPanel pCenter = new JPanel();
        CalendarDate today = DateUtil.getToday();
        int day = today.getDay();
        java.util.List<CalendarDate> list = DateUtil.getDaysInMonth(today);
        int daysOfMonth = list.size();
        int start = list.get(0).getDayOfWeek() == 7 ? 0 : list.get(0).getDayOfWeek();
        pCenter.setLayout(new GridLayout(7, 7, 7, 7));
        for (int i = 0; i < 7; i++) {
            titleName[i] = new JButton(name[i]);
            pCenter.add(titleName[i]);
        }
        for (int i = 0; i < start; i++) {
            buttonDay[i] = new JButton("");
            buttonDay[i].setOpaque(false);
            buttonDay[i].setBackground(Color.CYAN);
            buttonDay[i].addActionListener(this::actionPerformed);
            pCenter.add(buttonDay[i]);
        }

        for (int i = start; i < daysOfMonth + start; i++) {
            buttonDay[i] = new JButton(Integer.toString(i - start + 1));
            buttonDay[i].setOpaque(false);
            buttonDay[i].setBackground(Color.CYAN);
            buttonDay[i].addActionListener(this::actionPerformed);
            pCenter.add(buttonDay[i]);
        }
        for (int i = daysOfMonth + start; i < 42; i++) {
            buttonDay[i] = new JButton("");
            buttonDay[i].addActionListener(this::actionPerformed);
            buttonDay[i].setOpaque(false);
            buttonDay[i].setBackground(Color.CYAN);
            pCenter.add(buttonDay[i]);
        }

        buttonDay[day + start - 1].setForeground(Color.red);
        //更改下拉列表的值
        yearComboBox.setSelectedItem(today.getYear());
        monthComBox.setSelectedItem(today.getMonth());
        //添加下部面板
        JPanel pSouth = new JPanel();
        checkButton.addActionListener(this);
        pSouth.add(text);
        pSouth.add(checkButton);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(pCenter);
        jf.getContentPane().add(pNorth, BorderLayout.NORTH);
        jf.getContentPane().add(scrollPane, BorderLayout.CENTER);
        jf.getContentPane().add(pSouth, BorderLayout.SOUTH);

        jf.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == seeButton) {
            int year = Integer.parseInt(yearComboBox.getSelectedItem().toString());
            int month = Integer.parseInt(monthComBox.getSelectedItem().toString());
            CalendarDate seeDate = new CalendarDate(year, month, 1);
            paintDays(seeDate);
        }
        if (e.getSource() == todayButton) {
            paintDays(DateUtil.getToday());
        }
        if (e.getSource() == checkButton) {
            String dateText = text.getText();
            try {
                CalendarDate date = new CalendarDate(dateText);
                paintDays(date);
            } catch (FormatException fe) {
                System.out.print(fe.getMessage());
                JFrame frame = new JFrame("错误提示");
                frame.setSize(300, 80);
                JPanel panel = new JPanel();
                JLabel label = new JLabel("您输入的日期格式有问题，请重新输入");
                panel.add(label);
                frame.add(panel);
                frame.setVisible(true);
            }
        }
        if (e.getSource() == memoButton) {

            JFrame.setDefaultLookAndFeelDecorated(true);
            frame = new JFrame("备忘");
            frame.setLayout(null);
            frame.setResizable(false);
            frame.setSize(750, 500);
//            frame.setDefaultCloseOperation();
            JPanel panelNorth = new JPanel();
            panelNorth.setLayout(null);
            panelNorth.setBounds(0, 0, 750, 200);
            JLabel label = new JLabel("添加备忘:");
            label.setBounds(0, 0, 100, 50);
            JLabel type = new JLabel("类型：");
            type.setBounds(80,0,100,50);
            addType.setBounds(130,0,130,50);
            JLabel labelAddInDay = new JLabel("输入日期：");
            labelAddInDay.setBounds(10, 50, 100, 20);
            dateAddInDay.setBounds(100, 50, 150, 20);
            dateAddInDay.addFocusListener(new JTextFieldHintListener(dateAddInDay,"yyyy-mm-dd"));
            JLabel messageLabel = new JLabel("事件：");
            messageLabel.setBounds(300, 50, 50, 20);
            messageAddInDay.setBounds(350, 50, 150, 20);
            addInDay.setBounds(550, 50, 150, 20);

            JLabel labelAddInPeriod1 = new JLabel("起始日期:");
            labelAddInPeriod1.setBounds(10, 100, 100, 20);
            textAddInPeriod1.setBounds(100, 100, 150, 20);
            textAddInPeriod1.addFocusListener(new JTextFieldHintListener(textAddInPeriod1,"yyyy-mm-dd hh:mm"));
            JLabel labelAddInPeriod2 = new JLabel("结束日期：");
            JLabel messageLabelAddInPeriod = new JLabel("事件：");
            messageLabelAddInPeriod.setBounds(300, 100, 50, 20);
            messageAddInPeriod.setBounds(350, 100, 150, 20);
            labelAddInPeriod2.setBounds(10, 130, 150, 20);
            textAddInPeriod2.setBounds(100, 130, 150, 20);
            textAddInPeriod2.addFocusListener(new JTextFieldHintListener(textAddInPeriod2,"yyyy-mm-dd hh:mm"));
            addInPeriod.setBounds(550, 100, 150, 20);


            panelNorth.add(label);
            panelNorth.add(type);
            panelNorth.add(addType);
            panelNorth.add(labelAddInDay);
            panelNorth.add(dateAddInDay);
            panelNorth.add(messageLabel);
            panelNorth.add(messageAddInDay);
            panelNorth.add(addInDay);
            panelNorth.add(labelAddInPeriod1);
            panelNorth.add(textAddInPeriod1);
            panelNorth.add(messageAddInPeriod);
            panelNorth.add(messageLabelAddInPeriod);
            panelNorth.add(labelAddInPeriod2);
            panelNorth.add(textAddInPeriod2);
            panelNorth.add(addInPeriod);

            JPanel panelSouth = new JPanel();
            panelSouth.setLayout(null);
            panelSouth.setBounds(0, 200, 750, 200);
            JLabel labelQuery = new JLabel("查询备忘:");
            labelQuery.setBounds(0, 0, 100, 50);
            JLabel labelQueryInDay = new JLabel("输入日期：");
            labelQueryInDay.setBounds(10, 50, 100, 20);
            textQueryInDay.setBounds(100, 50, 150, 20);
            textQueryInDay.addFocusListener(new JTextFieldHintListener(textQueryInDay,"yyyy-mm-dd"));
            queryInDay.setBounds(550, 50, 150, 20);


            JLabel labelQueryInPeriod1 = new JLabel("起始日期:");
            labelQueryInPeriod1.setBounds(10, 100, 100, 20);
            textQueryInPeriod1.setBounds(100, 100, 150, 20);
            textQueryInPeriod1.addFocusListener(new JTextFieldHintListener(textQueryInPeriod1,"yyyy-mm-dd hh:mm"));
            JLabel labelQueryInPeriod2 = new JLabel("结束日期：");
            labelQueryInPeriod2.setBounds(280, 100, 150, 20);
            textQueryInPeriod2.setBounds(350, 100, 150, 20);
            textQueryInPeriod2.addFocusListener(new JTextFieldHintListener(textQueryInPeriod2,"yyyy-mm-dd hh:mm"));
            queryInPeriod.setBounds(550, 100, 150, 20);


            panelSouth.add(labelQuery);
            panelSouth.add(labelQueryInDay);
            panelSouth.add(textQueryInDay);
            panelSouth.add(queryInDay);
            panelSouth.add(labelQueryInPeriod1);
            panelSouth.add(textQueryInPeriod1);
            panelSouth.add(labelQueryInPeriod2);
            panelSouth.add(textQueryInPeriod2);
            panelSouth.add(queryInPeriod);
            panelSouth.add(labelQuery);

            frame.add(panelNorth);
            frame.add(panelSouth);


            frame.setVisible(true);
        }
        if (e.getSource() == addInDay) {
            String dateString = dateAddInDay.getText();
            String message = messageAddInDay.getText();
            System.out.println(dateString);
            System.out.println(message);
            try {
                CalendarDate date = new CalendarDate(dateString);
                if (!DateUtil.isValid(date)) throw new FormatException("时间格式错误");
                Event event = new Event(date, message);
                events.add(event);
                int year = Integer.parseInt(yearComboBox.getSelectedItem().toString());
                int month = Integer.parseInt(monthComBox.getSelectedItem().toString());
                CalendarDate seeDate = new CalendarDate(year, month, 1);
                paintDays(seeDate);
                dateAddInDay.setText("");
                messageAddInDay.setText("");
                JOptionPane.showMessageDialog(frame, "添加成功", "标题", JOptionPane.INFORMATION_MESSAGE);
            } catch (FormatException fe1) {
                JOptionPane.showMessageDialog(frame, "时间格式有问题", "标题", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (e.getSource() == addInPeriod) {
            String beginDateString = textAddInPeriod1.getText();
            String endDateString = textAddInPeriod2.getText();
            String message = messageAddInPeriod.getText();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date1 = sdf.parse(beginDateString);
                Date date2 = sdf.parse(endDateString);
                if (date1.getTime() > date2.getTime())
                    JOptionPane.showMessageDialog(frame, "时间先后顺序错误", "标题", JOptionPane.WARNING_MESSAGE);
                else {
                    System.out.println(beginDateString);
                    System.out.println(endDateString);
                    System.out.println(message);
                    try {
                        CalendarDate beginDate = new CalendarDate(beginDateString);
                        CalendarDate endDate = new CalendarDate(endDateString);
                        if (!DateUtil.isValid(beginDate) || !DateUtil.isValid(endDate))
                            throw new FormatException("时间格式错误");
                        Event event = new Event(beginDate, endDate, message);
                        events.add(event);
                        int year = Integer.parseInt(yearComboBox.getSelectedItem().toString());
                        int month = Integer.parseInt(monthComBox.getSelectedItem().toString());
                        CalendarDate seeDate = new CalendarDate(year, month, 1);
                        paintDays(seeDate);
                        JOptionPane.showMessageDialog(frame, "添加成功", "标题", JOptionPane.INFORMATION_MESSAGE);
                        textAddInPeriod1.setText("");
                        textAddInPeriod2.setText("");
                        messageAddInPeriod.setText("");
                    } catch (FormatException fe2) {
                        JOptionPane.showMessageDialog(frame, "时间格式有问题", "标题", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "时间格式有问题", "标题", JOptionPane.WARNING_MESSAGE);
            }

        }

        if (e.getSource() == queryInDay) {
            String dateString = textQueryInDay.getText();
            try {
                CalendarDate date = new CalendarDate(dateString);
                if (!DateUtil.isValid(date)) throw new FormatException("时间格式错误");
                eventArrayList = events.queryInDay(date);
                if (eventArrayList.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "当天没有备忘事件", "标题", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    textQueryInDay.setText("");
                    memoDisplay();
                }
            } catch (FormatException fe3) {
                JOptionPane.showMessageDialog(frame, "时间格式有问题", "标题", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (e.getSource() == queryInPeriod) {
            String beginDateString = textQueryInPeriod1.getText();
            String endDateString = textQueryInPeriod2.getText();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date1 = sdf.parse(beginDateString);
                Date date2 = sdf.parse(endDateString);
                if (date1.getTime() > date2.getTime())
                    JOptionPane.showMessageDialog(frame, "时间顺序错误", "标题", JOptionPane.WARNING_MESSAGE);
                else {
                    System.out.println(beginDateString);
                    System.out.println(endDateString);
                    try {
                        CalendarDate beginDate = new CalendarDate(beginDateString);
                        CalendarDate endDate = new CalendarDate(endDateString);
                        if (!DateUtil.isValid(beginDate) || !DateUtil.isValid(endDate))
                            throw new FormatException("时间格式错误");
                        eventArrayList = events.queryInPeriod(beginDate, endDate);
                        if (eventArrayList.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "这个时间段没有备忘事件", "标题", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            textQueryInPeriod1.setText("");
                            textQueryInPeriod2.setText("");
                            memoDisplay();
                        }
                    } catch (FormatException fe4) {
                        JOptionPane.showMessageDialog(frame, "时间格式有问题", "标题", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "时间格式有问题", "标题", JOptionPane.WARNING_MESSAGE);
            }
        }

        if(e.getSource() == addType){
            if(addType.getSelectedItem().toString().equals("meeting")){
                
            }
            if(addType.getSelectedItem().toString().equals("appointment")){
                
            }
        }
        if (null != delete) {
            for (int i = 0; i < delete.length; i++) {
                if (e.getSource() == delete[i]) {
                    events.delete(eventArrayList.get(i));
                    eventArrayList.remove(eventArrayList.get(i));
                    int year = Integer.parseInt(yearComboBox.getSelectedItem().toString());
                    int month = Integer.parseInt(monthComBox.getSelectedItem().toString());
                    CalendarDate seeDate = new CalendarDate(year, month, 1);
                    paintDays(seeDate);
                }
            }
            JF.dispose();
            if (!eventArrayList.isEmpty()) memoDisplay();
            else {
                JOptionPane.showMessageDialog(frame, "已经没有备忘了", "标题", JOptionPane.INFORMATION_MESSAGE);
                delete = null;
            }
        }
        for(int i = 0;i<42;i++){
            if(e.getSource() == buttonDay[i] && !buttonDay[i].getText().equals("")){
                int year = Integer.parseInt(yearComboBox.getSelectedItem().toString());
                int month = Integer.parseInt(monthComBox.getSelectedItem().toString());
                int day = Integer.parseInt(buttonDay[i].getText());
                CalendarDate date = new CalendarDate(year,month,day);
                if(!events.queryInDay(date).isEmpty()) JOptionPane.showMessageDialog(frame, events.queryInDay(date).get(0).getMessage(), "备忘提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * judge whether the date is in one month with today.
     *
     * @param year,month
     */
    private boolean IsToMonth(int year, int month) {
        CalendarDate date = DateUtil.getToday();
        return year == date.getYear() && month == date.getMonth();
    }

    /**
     * paint the days of whole current month on the frame with the given CalendarDate
     *
     * @param date a valid CalendarDate param.
     */
    private void paintDays(CalendarDate date) {
        if (!DateUtil.isValid(date)) {
            JFrame frame = new JFrame("错误提示");
            frame.setSize(300, 80);
            JPanel panel = new JPanel();
            JLabel label = new JLabel("您输入的日期不存在。");
            panel.add(label);
            frame.add(panel);
            frame.setVisible(true);
        } else if (date.getYear() < 1800 || date.getYear() > 2300) {
            JFrame frame = new JFrame("错误提示");
            frame.setSize(300, 80);
            JPanel panel = new JPanel();
            JLabel label = new JLabel("您输入的年份超过了日历的显示范围。");
            panel.add(label);
            frame.add(panel);
            frame.setVisible(true);
        } else {
            for (int i = 0; i < 42; i++) {
                buttonDay[i].setText("");
                buttonDay[i].setForeground(Color.black);
                buttonDay[i].setOpaque(false);
            }
            java.util.List<CalendarDate> list = DateUtil.getDaysInMonth(date);
            int daysOfMonth = list.size();
            int start = list.get(0).getDayOfWeek() == 7 ? 0 : list.get(0).getDayOfWeek();
            for (int i = 0; i < start; i++) {
                buttonDay[i].setText("");
            }
            for (int i = start; i < daysOfMonth + start; i++) {
                buttonDay[i].setText(Integer.toString(i - start + 1));
                int year = date.getYear();
                int month = date.getMonth();
                int day = list.get(i - start).getDay();
                CalendarDate queryDate = new CalendarDate(year, month, day);
                if (!events.queryInDay(queryDate).isEmpty()) {
                    buttonDay[i].setOpaque(true);
                }
            }
            for (int i = daysOfMonth + start; i < 42; i++) {
                buttonDay[i].setText("");
            }
            yearComboBox.setSelectedItem(date.getYear());
            monthComBox.setSelectedItem(date.getMonth());
            if (IsToMonth(date.getYear(), date.getMonth()))
                buttonDay[DateUtil.getToday().getDay() + start - 1].setForeground(Color.red);
        }
    }

    private void memoDisplay() {
        int counts = eventArrayList.size();
        delete = new JButton[counts];
        JFrame.setDefaultLookAndFeelDecorated(true);
        JF = new JFrame("备忘列表");
        JF.setLayout(null);
        JF.setResizable(false);
        JF.setSize(600, (counts + 1) * 50);
        for (int i = 0; i < counts; i++) {
            Event event = eventArrayList.get(i);
            String memoMessage;
            if (event.isPeriod()) {
                memoMessage = event.getBeginDateString() + "~" + event.getEndDateString()+":"+event.getMessage();
            } else memoMessage = event.getDayString()+":"+event.getMessage();
            JLabel label = new JLabel(memoMessage);
            label.setBounds(10, 20 * (i + 1), 450, 20);
            delete[i] = new JButton("删除此备忘");
            delete[i].setBounds(450, 20 * (i + 1), 100, 20);
            delete[i].addActionListener(this::actionPerformed);
            JF.add(delete[i]);
            JF.add(label);
        }
        JF.setVisible(true);
    }
}
