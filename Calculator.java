import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 一个计算器，与Windows附件自带计算器的标准版功能、界面相仿。 但还不支持键盘操作。
 */
public class Calculator extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//计算器上的键的显示名字
    private final String[] KEYS = {
            "sin","cos","tan","x^2",//3
            "ln","lg","n!","π", //7
    		"7", "8", "9", "÷", //11
            "4", "5", "6", "x",//15
            "1", "2", "3", "-", //19
            "0", ".", "=","+",//23
           "剩余体锻数量","每天推荐里程数","刷锻步数预算"//26
    };

    private final Integer[] COMPLEX = {24,25,26};

    private boolean isSample;
    //计算器上的功能键的显示名字
    private final String[] COMMAND = {"Backspace", "CE", "C"};
    //计算器左边的M的显示名字
    private final String[] M = {"常用公式", "MC", "MR", "MS", "M+"};
    //计算器下方的常用公式名及功能键名
    private final String[] FORMULA = {"BMI","加班工资","鞋码换算","换算1","换算2","确认"};
    //计算器上键的按钮
    private JButton keys[] = new JButton[KEYS.length];
    //计算器上的功能键的按钮
    private JButton commands[] = new JButton[COMMAND.length];
    //计算器左边的M的按钮
    private JButton m[] = new JButton[M.length];
    //计算器下方的公式按钮
    private JButton formula[] = new JButton[FORMULA.length];
    //计算结果文本框
    private JTextField resultText = new JTextField("0");
    // 标志用户按的是否是整个表达式的第一个数字,或者是运算符后的第一个数字
    private boolean firstDigit = true;
    // 计算的中间结果。
    private double resultNum = 0.0;
    // 当前运算的运算符
    private String operator = "=";
    // 操作是否合法
    private boolean operateValidFlag = true;
    // 当前选择的常用公式
    private String formulatype;
    // 使用公式时已传递的参数个数
    private int numofparameters = 0;
    // 选择的换算类型
    private int choosetype = 0;
    // 输入的参数的值
    private double parameter1, parameter2 = 0;
    // 是否需要清除文本框中的提示信息
    private boolean needclear = false;

    /**
     * 构造函数
     */
    public Calculator() {
        super();
        // 初始化计算器
        init();
        // 设置计算器的背景颜色
        this.setBackground(Color.BLACK);
        this.setTitle("计算器");
        // 在屏幕(500, 300)坐标处显示计算器
        this.setLocation(500, 300);
        // 不许修改计算器的大小
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        //用来设置窗口随屏幕大小改变
        sizeWindowOnScreen(this,0.6,0.6);
        this.setVisible(true);
        this.setResizable(true);
        // 使计算器中各组件大小合适
        this.pack();
    }

    /**
    * 
    * @param calculator
    * @param widthRate 宽度比例 
    * @param heightRate 高度比例
    */
    private void sizeWindowOnScreen(Calculator calculator, double widthRate, double heightRate)
    {
       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      
       calculator.setSize(new Dimension((int)(screenSize.width * widthRate),(int)(screenSize.height *heightRate)));
    }
    
    /**
     * 初始化计算器
     */
    private void init() {
        // 文本框中的内容采用右对齐方式
        resultText.setHorizontalAlignment(JTextField.RIGHT);
        // 不允许修改结果文本框
        resultText.setEditable(false);
        // 设置文本框背景颜色为白色
        resultText.setBackground(Color.WHITE);
        // 设置文本框字体和大小
        resultText.setFont(new Font("宋体",Font.BOLD,30));
        // 初始化计算器上键的按钮，将键放在一个画板内
        JPanel calckeysPanel = new JPanel();
        // 用网格布局器，4行，7列的网格，网格之间的水平方向间隔为3个象素，垂直方向间隔为3个象素
        calckeysPanel.setLayout(new GridLayout(7, 4, 3, 3));
        for (int i = 0; i < KEYS.length; i++) {
            keys[i] = new JButton(KEYS[i]);
            calckeysPanel.add(keys[i]);
            keys[i].setFont(new java.awt.Font(KEYS[i], 4, 20));
            keys[i].setForeground(new java.awt.Color(0,139,139));
        }

        // 初始化功能键，都用红色标示。将功能键放在一个画板内
        JPanel commandsPanel = new JPanel();
        // 用网格布局器，1行，3列的网格，网格之间的水平方向间隔为3个象素，垂直方向间隔为3个象素
        commandsPanel.setLayout(new GridLayout(1, 3, 3, 3));
        for (int i = 0; i < COMMAND.length; i++) {
            commands[i] = new JButton(COMMAND[i]);
            commandsPanel.add(commands[i]);
            commands[i].setFont(new java.awt.Font(COMMAND[i], 4, 20));
            commands[i].setForeground(new java.awt.Color(70,130,180));
        }

        // 初始化M键，用红色标示，将M键放在一个画板内
        JPanel calmsPanel = new JPanel();
        // 用网格布局管理器，5行，1列的网格，网格之间的水平方向间隔为3个象素，垂直方向间隔为3个象素
        calmsPanel.setLayout(new GridLayout(5, 1, 3, 3));
        for (int i = 0; i < M.length; i++) {
            m[i] = new JButton(M[i]);
            calmsPanel.add(m[i]);
            m[i].setFont(new java.awt.Font(M[i], 4, 20));
            m[i].setForeground(new java.awt.Color(186,85,211));
        }
        m[0].setForeground(Color.red);
        // 初始化公式键，都用红色标示。将功能键放在一个画板内
        JPanel formulaPanel = new JPanel();
        // 用网格布局器，2行，3列的网格，网格之间的水平方向间隔为3个象素，垂直方向间隔为3个象素
        formulaPanel.setLayout(new GridLayout(2, 3, 3, 3));
        for (int i = 0; i < FORMULA.length; i++) {
            formula[i] = new JButton(FORMULA[i]);
            formulaPanel.add(formula[i]);
            formula[i].setFont(new java.awt.Font(FORMULA[i], 4, 20));
            formula[i].setForeground(Color.red);
        }
        
        // 下面进行计算器的整体布局，将calckeys和command画板放在计算器的中部，
        // 将文本框放在北部，将calms画板放在计算器的西部。

        // 新建一个大的画板，将上面建立的command和calckeys画板放在该画板内
        JPanel panel1 = new JPanel();
        // 画板采用边界布局管理器，画板里组件之间的水平和垂直方向上间隔都为3象素
        panel1.setLayout(new BorderLayout(3, 3));
        panel1.add("North", commandsPanel);
        panel1.add("Center", calckeysPanel);
        panel1.add("South",formulaPanel);
        
        // 建立一个画板放文本框
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        top.add("Center", resultText);

        // 整体布局
        getContentPane().setLayout(new BorderLayout(3, 5));
        getContentPane().add("North", top);
        getContentPane().add("Center", panel1);
        getContentPane().add("West", calmsPanel);
        
        // 为各按钮添加事件侦听器
        // 都使用同一个事件侦听器，即本对象。本类的声明中有implements ActionListener

        final List<Integer> list = new ArrayList<>(Arrays.asList(COMPLEX));

        for (int i = 0; i < KEYS.length; i++) {
            keys[i].addActionListener(this);

            if (list.contains(i)) {
                keys[i].setVisible(isSample);
            }
        }
        for (int i = 0; i < COMMAND.length; i++) {
            commands[i].addActionListener(this);
        }
        for (int i = 0; i < M.length; i++) {
            m[i].addActionListener(this);
        }
        for (int i = 0; i < FORMULA.length; i++) {
            formula[i].addActionListener(this);
        }
        
        m[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m[0].setText(
                        isSample ? "常用公式" : "计算体测"
                );

                for (int i : list) {
                    keys[i].setVisible(!isSample);
                }

                isSample = !isSample;
            }
        });
    }

    /**
     * 处理事件
     */
    public void actionPerformed(ActionEvent e) {
        // 获取事件源的标签
        String label = e.getActionCommand();
        if (label.equals(COMMAND[0])) {
            // 用户按了"Backspace"键
            handleBackspace();
        } else if (label.equals(COMMAND[1])) {
            // 用户按了"CE"键
            resultText.setText("0");
        } else if (label.equals(COMMAND[2])) {
            // 用户按了"C"键
            handleC();
        } else if ("0123456789.".indexOf(label) >= 0) {
            // 用户按了数字键或者小数点键
            handleNumber(label);
            // handlezero(zero);
        } else if (label.equals(FORMULA[0])) {
        	// 用户按了BMI计算键
        	handleBMI();
        	formulatype = "BMI";
        } else if (label.equals(FORMULA[1])) {
        	// 用户按了加班工资计算键
        	handleOverwork();
        	formulatype = "加班工资";
        } else if (label.equals(FORMULA[2])) {
        	// 用户按了鞋码换算计算键
        	handleShoesize();
        	formulatype = "鞋码换算";
        } else if (label.equals(FORMULA[3])) {
        	// 用户按了换算1键
        	handlechoice1();
        } else if (label.equals(FORMULA[4])) {
        	// 用户按了换算2键
        	handlechoice2();
        } else if (label.equals(FORMULA[5])) {
        	// 用户按了确认键
        	handleconfirm();
        }
        else {
            // 用户按了运算符键
            handleOperator(label);
        }
    }

    
     //处理Backspace键被按下的事件
  
    private void handleBackspace() {
        String text = resultText.getText();
        int i = text.length();
        if (i > 0) {
            // 退格，将文本最后一个字符去掉
            text = text.substring(0, i - 1);
            if (text.length() == 0) {
                // 如果文本没有了内容，则初始化计算器的各种值
                resultText.setText("0");
                firstDigit = true;
                operator = "=";
            } else {
                // 显示新的文本
                resultText.setText(text);
            }
        }
    }

    //处理数字键被按下的事件
    private void handleNumber(String key) {
        if (needclear == true && formulatype != "") {
        	resultText.setText("");
        	needclear = false;
        }
    	if (firstDigit) {
            // 输入的第一个数字
            resultText.setText(key);
        } else if ((key.equals(".")) && (resultText.getText().indexOf(".") < 0)) {
            // 输入的是小数点，并且之前没有小数点，则将小数点附在结果文本框的后面
            resultText.setText(resultText.getText() + ".");
        } else if (!key.equals(".")) {
            // 如果输入的不是小数点，则将数字附在结果文本框的后面
            resultText.setText(resultText.getText() + key);
        }
        // 以后输入的肯定不是第一个数字了
        firstDigit = false;
    }

    //处理清除键被按下的事件
    private void handleC() {
        // 初始化计算器的各种值
        resultText.setText("0");
        firstDigit = true;
        operator = "=";
        formulatype = "";
        needclear = false;
        parameter1 = 0;
        parameter2 = 0;
    }

    //处理加减乘除、三角函数等运算键被按下的事件
    private void handleOperator(String key) {
        if (operator.equals("÷")) {
            // 除法运算
            // 如果当前结果文本框中的值等于0
            if (getNumberFromText() == 0.0) {
                // 操作不合法
                operateValidFlag = false;
                resultText.setText("除数不能为零");
            } else {
                resultNum /= getNumberFromText();
            }
        } else if (operator.equals("1/x")) {
            // 倒数运算
            if (resultNum == 0.0) {
                // 操作不合法
                operateValidFlag = false;
                resultText.setText("零没有倒数");
            } else {
                resultNum = 1 / resultNum;
            }
        }else if (operator.equals("刷锻步数预算")) {
            // 倒数运算
     
            if (resultNum<1||resultNum>5) {
                // 操作不合法
                operateValidFlag = false;
                resultText.setText("输入数据无效！每日刷锻数在1到5之间！");
            } else {
                resultNum = 1500 * resultNum;
            }
        }else if (operator.equals("剩余体锻数量")) {
                // 体锻数运算
            	
            	double k = getNumberFromText();
            	double q = 0;
            	q = k - 60;
            	double f = 0;
            	f = 60 - q;
            	if(q >= 0) {
            	resultNum = f;
            	}
            	else {
            		operateValidFlag = false;
            		resultText.setText("无药可救！");}
            	
            }
        else if (operator.equals("每天推荐里程数")) {
                // 每天推荐里程数运算
            	
            	
            	if(resultNum<60) {    //判断体测成绩是否小于60分
            		operateValidFlag = false;
              		resultText.setText("无药可救！");}
            	else {
            		 double l = 0;
            		 
            	     //体测成绩距离及格线的差距
            		resultNum = (120-resultNum)/getNumberFromText();    //第二次获取剩余天数
            	      l = resultNum;
            	      if(l > 5) {
            	    	  operateValidFlag = false;
              		resultText.setText("无药可救！");}
            	      else resultNum = l;
            	
            	}
            }
        else if (operator.equals("+")) {
            // 加法运算
            resultNum += getNumberFromText();
        } else if (operator.equals("-")) {
            // 减法运算
            resultNum -= getNumberFromText();
        } else if (operator.equals("x")) {
            // 乘法运算
            resultNum *= getNumberFromText();
        } else if (operator.equals("sqrt")) {
            // 平方根运算
            resultNum = Math.sqrt(resultNum);
        } else if (operator.equals("%")) {
            // 百分号运算，除以100
            resultNum = resultNum / 100;
        } else if (operator.equals("+/-")) {
            // 正数负数运算
            resultNum = resultNum * (-1);
        } else if (operator.equals("=")) {
            // 赋值运算
            resultNum = getNumberFromText();
        } else if (operator.equals("sin")) {
        	// sin运算
        	resultNum = Math.sin(resultNum);
        } else if (operator.equals("cos")) {
        	// cos运算
        	resultNum = Math.cos(resultNum);
        } else if (operator.equals("tan")) {
        	// tan运算
        	resultNum = Math.tan(resultNum);
        } else if (operator.equals("ln")) {
        	// 对数运算（以e为底）
        	resultNum = Math.log(resultNum);
        } else if (operator.equals("π")) {
        	// 计算π
        	resultNum = Math.PI;
        } else if (operator.equals("n!")) {
        	// 阶乘运算
        	for(double i = resultNum - 1; i >= 1; i--)
        	{
        		resultNum = resultNum * i;
        	}
        } else if (operator.equals("x^2")) {
        	// 平方运算
        	resultNum = resultNum * resultNum;
        } else if (operator.equals("lg")) {
        	// 对数运算（以10为底）
        	resultNum = Math.log10(resultNum);
        }
        if (operateValidFlag) {
            // 双精度浮点数的运算
            long t1;
            double t2;
            t1 = (long) resultNum;
            t2 = resultNum - t1;
            if (t2 == 0) {
                resultText.setText(String.valueOf(t1));
            } else {
                resultText.setText(String.valueOf(resultNum));
            }
        }
        // 运算符等于用户按的按钮
        operator = key;
        firstDigit = true;
        operateValidFlag = true;
    }

    //处理BMI公式键被按下的事件
    private void handleBMI() {
    	resultText.setText("请输入身高值（以米为单位）");
    }
    //处理加班工资计算键被按下的事件
    private void handleOverwork() {
    	resultText.setText("请输入计算类型（1-节假日；2-休息日）");
    }
    //处理鞋码换算键被按下的事件
    private void handleShoesize() {
    	resultText.setText("请输入换算类型（1-旧码→新码；2-新码→旧码）");
    }
    //处理换算键1被按下的事件
    private void handlechoice1() {
    	choosetype = 1;
    	if(formulatype == "加班工资") {
    		resultText.setText("请输入每月工资");
    	}
    	else if(formulatype == "鞋码换算") {
    		resultText.setText("请输入旧鞋码");
    	}
    }
    //处理换算键2被按下的事件
    private void handlechoice2() {
    	choosetype = 2;
    	if(formulatype == "加班工资") {
    		resultText.setText("请输入每月工资");
    	}
    	else if(formulatype == "鞋码换算") {
    		resultText.setText("请输入新鞋码");
    	}
    }
    //处理确认键被按下的事件
    private void handleconfirm() {
    	//如果选择公式为BMI且没有录入参数时，录入第一个参数
    	if(formulatype == "BMI" && numofparameters == 0) {
    		parameter1 = getNumberFromText();
    		numofparameters = 1;
    		needclear = true;
    		if(parameter1 == 0) {
    			resultText.setText("身高不能为0");
    			return;
    		}
    		resultText.setText("请输入体重（以kg为单位）");
    	} //如果选择公式为BMI且已录入一个参数时，录入第二个参数
    	else if(formulatype == "BMI" && numofparameters == 1) {
    		resultText.setText("BMI为" + getNumberFromText()/(parameter1 * parameter1));
    		formulatype = "";
    		parameter1 = 0;
    		numofparameters = 0;
    	} //如果选择公式为加班工资且未录入参数时，录入第一个参数
    	else if(formulatype == "加班工资" && numofparameters == 0) {
    		parameter1 = getNumberFromText() / 20.83;
    		numofparameters = 1;
    		needclear = true;
    		resultText.setText("请输入加班天数");
    	} //如果选择公式为加班工资且已录入一个参数时，录入第二个参数
    	else if(formulatype == "加班工资" && numofparameters == 1) {
    		parameter2 = getNumberFromText();
    		numofparameters = 2;
    		needclear = true;
    		resultText.setText("请输入每天加班时长（以小时计）");
    	} //如果选择公式为加班工资且已录入两个参数时，录入第三个参数
    	else if(formulatype == "加班工资" && numofparameters == 2) {
    		//如果选择节假日
    		if(choosetype == 1) resultText.setText("加班工资总额为" + getNumberFromText() * parameter1 * parameter2 * 3);
    		//如果选择普通休息日
    		else resultText.setText("加班工资总额为" + getNumberFromText() * parameter1 * parameter2 * 2 + "mm");
    		formulatype = "";
    		parameter1 = 0;
    		parameter2 = 0;
    		numofparameters = 0;
    	} //如果选择公式为鞋码换算且未录入参数时，录入唯一一个参数
    	else if(formulatype == "鞋码换算" && numofparameters == 0) {
    		//如果选择换算为旧码→新码
    		if(choosetype == 1) resultText.setText("对应新鞋码是" + (getNumberFromText() + 10) * 5 + "mm");
    		//如果选择换算为新码→旧码
    		else resultText.setText("对应旧鞋码是" + (getNumberFromText() / 5 - 10));
    		formulatype = "";
    	}
    }
    /**
     * 从结果文本框中获取数字
     * @return
     */
    private double getNumberFromText() {
        double result = 0;
        try {
            result = Double.valueOf(resultText.getText()).doubleValue();
        } catch (NumberFormatException e) {
        }
        return result;
    }

    public static void main(String args[]) {
        Calculator calculator1 = new Calculator();
        calculator1.setVisible(true);
        calculator1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}