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
 * һ������������Windows�����Դ��������ı�׼�湦�ܡ�������¡� ������֧�ּ��̲�����
 */
public class Calculator extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//�������ϵļ�����ʾ����
    private final String[] KEYS = {
            "sin","cos","tan","x^2",//3
            "ln","lg","n!","��", //7
    		"7", "8", "9", "��", //11
            "4", "5", "6", "x",//15
            "1", "2", "3", "-", //19
            "0", ".", "=","+",//23
           "ʣ���������","ÿ���Ƽ������","ˢ�Ͳ���Ԥ��"//26
    };

    private final Integer[] COMPLEX = {24,25,26};

    private boolean isSample;
    //�������ϵĹ��ܼ�����ʾ����
    private final String[] COMMAND = {"Backspace", "CE", "C"};
    //��������ߵ�M����ʾ����
    private final String[] M = {"���ù�ʽ", "MC", "MR", "MS", "M+"};
    //�������·��ĳ��ù�ʽ�������ܼ���
    private final String[] FORMULA = {"BMI","�Ӱ๤��","Ь�뻻��","����1","����2","ȷ��"};
    //�������ϼ��İ�ť
    private JButton keys[] = new JButton[KEYS.length];
    //�������ϵĹ��ܼ��İ�ť
    private JButton commands[] = new JButton[COMMAND.length];
    //��������ߵ�M�İ�ť
    private JButton m[] = new JButton[M.length];
    //�������·��Ĺ�ʽ��ť
    private JButton formula[] = new JButton[FORMULA.length];
    //�������ı���
    private JTextField resultText = new JTextField("0");
    // ��־�û������Ƿ����������ʽ�ĵ�һ������,�������������ĵ�һ������
    private boolean firstDigit = true;
    // ������м�����
    private double resultNum = 0.0;
    // ��ǰ����������
    private String operator = "=";
    // �����Ƿ�Ϸ�
    private boolean operateValidFlag = true;
    // ��ǰѡ��ĳ��ù�ʽ
    private String formulatype;
    // ʹ�ù�ʽʱ�Ѵ��ݵĲ�������
    private int numofparameters = 0;
    // ѡ��Ļ�������
    private int choosetype = 0;
    // ����Ĳ�����ֵ
    private double parameter1, parameter2 = 0;
    // �Ƿ���Ҫ����ı����е���ʾ��Ϣ
    private boolean needclear = false;

    /**
     * ���캯��
     */
    public Calculator() {
        super();
        // ��ʼ��������
        init();
        // ���ü������ı�����ɫ
        this.setBackground(Color.BLACK);
        this.setTitle("������");
        // ����Ļ(500, 300)���괦��ʾ������
        this.setLocation(500, 300);
        // �����޸ļ������Ĵ�С
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        //�������ô�������Ļ��С�ı�
        sizeWindowOnScreen(this,0.6,0.6);
        this.setVisible(true);
        this.setResizable(true);
        // ʹ�������и������С����
        this.pack();
    }

    /**
    * 
    * @param calculator
    * @param widthRate ��ȱ��� 
    * @param heightRate �߶ȱ���
    */
    private void sizeWindowOnScreen(Calculator calculator, double widthRate, double heightRate)
    {
       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      
       calculator.setSize(new Dimension((int)(screenSize.width * widthRate),(int)(screenSize.height *heightRate)));
    }
    
    /**
     * ��ʼ��������
     */
    private void init() {
        // �ı����е����ݲ����Ҷ��뷽ʽ
        resultText.setHorizontalAlignment(JTextField.RIGHT);
        // �������޸Ľ���ı���
        resultText.setEditable(false);
        // �����ı��򱳾���ɫΪ��ɫ
        resultText.setBackground(Color.WHITE);
        // �����ı�������ʹ�С
        resultText.setFont(new Font("����",Font.BOLD,30));
        // ��ʼ���������ϼ��İ�ť����������һ��������
        JPanel calckeysPanel = new JPanel();
        // �����񲼾�����4�У�7�е���������֮���ˮƽ������Ϊ3�����أ���ֱ������Ϊ3������
        calckeysPanel.setLayout(new GridLayout(7, 4, 3, 3));
        for (int i = 0; i < KEYS.length; i++) {
            keys[i] = new JButton(KEYS[i]);
            calckeysPanel.add(keys[i]);
            keys[i].setFont(new java.awt.Font(KEYS[i], 4, 20));
            keys[i].setForeground(new java.awt.Color(0,139,139));
        }

        // ��ʼ�����ܼ������ú�ɫ��ʾ�������ܼ�����һ��������
        JPanel commandsPanel = new JPanel();
        // �����񲼾�����1�У�3�е���������֮���ˮƽ������Ϊ3�����أ���ֱ������Ϊ3������
        commandsPanel.setLayout(new GridLayout(1, 3, 3, 3));
        for (int i = 0; i < COMMAND.length; i++) {
            commands[i] = new JButton(COMMAND[i]);
            commandsPanel.add(commands[i]);
            commands[i].setFont(new java.awt.Font(COMMAND[i], 4, 20));
            commands[i].setForeground(new java.awt.Color(70,130,180));
        }

        // ��ʼ��M�����ú�ɫ��ʾ����M������һ��������
        JPanel calmsPanel = new JPanel();
        // �����񲼾ֹ�������5�У�1�е���������֮���ˮƽ������Ϊ3�����أ���ֱ������Ϊ3������
        calmsPanel.setLayout(new GridLayout(5, 1, 3, 3));
        for (int i = 0; i < M.length; i++) {
            m[i] = new JButton(M[i]);
            calmsPanel.add(m[i]);
            m[i].setFont(new java.awt.Font(M[i], 4, 20));
            m[i].setForeground(new java.awt.Color(186,85,211));
        }
        m[0].setForeground(Color.red);
        // ��ʼ����ʽ�������ú�ɫ��ʾ�������ܼ�����һ��������
        JPanel formulaPanel = new JPanel();
        // �����񲼾�����2�У�3�е���������֮���ˮƽ������Ϊ3�����أ���ֱ������Ϊ3������
        formulaPanel.setLayout(new GridLayout(2, 3, 3, 3));
        for (int i = 0; i < FORMULA.length; i++) {
            formula[i] = new JButton(FORMULA[i]);
            formulaPanel.add(formula[i]);
            formula[i].setFont(new java.awt.Font(FORMULA[i], 4, 20));
            formula[i].setForeground(Color.red);
        }
        
        // ������м����������岼�֣���calckeys��command������ڼ��������в���
        // ���ı�����ڱ�������calms������ڼ�������������

        // �½�һ����Ļ��壬�����潨����command��calckeys������ڸû�����
        JPanel panel1 = new JPanel();
        // ������ñ߽粼�ֹ����������������֮���ˮƽ�ʹ�ֱ�����ϼ����Ϊ3����
        panel1.setLayout(new BorderLayout(3, 3));
        panel1.add("North", commandsPanel);
        panel1.add("Center", calckeysPanel);
        panel1.add("South",formulaPanel);
        
        // ����һ��������ı���
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        top.add("Center", resultText);

        // ���岼��
        getContentPane().setLayout(new BorderLayout(3, 5));
        getContentPane().add("North", top);
        getContentPane().add("Center", panel1);
        getContentPane().add("West", calmsPanel);
        
        // Ϊ����ť����¼�������
        // ��ʹ��ͬһ���¼����������������󡣱������������implements ActionListener

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
                        isSample ? "���ù�ʽ" : "�������"
                );

                for (int i : list) {
                    keys[i].setVisible(!isSample);
                }

                isSample = !isSample;
            }
        });
    }

    /**
     * �����¼�
     */
    public void actionPerformed(ActionEvent e) {
        // ��ȡ�¼�Դ�ı�ǩ
        String label = e.getActionCommand();
        if (label.equals(COMMAND[0])) {
            // �û�����"Backspace"��
            handleBackspace();
        } else if (label.equals(COMMAND[1])) {
            // �û�����"CE"��
            resultText.setText("0");
        } else if (label.equals(COMMAND[2])) {
            // �û�����"C"��
            handleC();
        } else if ("0123456789.".indexOf(label) >= 0) {
            // �û��������ּ�����С�����
            handleNumber(label);
            // handlezero(zero);
        } else if (label.equals(FORMULA[0])) {
        	// �û�����BMI�����
        	handleBMI();
        	formulatype = "BMI";
        } else if (label.equals(FORMULA[1])) {
        	// �û����˼Ӱ๤�ʼ����
        	handleOverwork();
        	formulatype = "�Ӱ๤��";
        } else if (label.equals(FORMULA[2])) {
        	// �û�����Ь�뻻������
        	handleShoesize();
        	formulatype = "Ь�뻻��";
        } else if (label.equals(FORMULA[3])) {
        	// �û����˻���1��
        	handlechoice1();
        } else if (label.equals(FORMULA[4])) {
        	// �û����˻���2��
        	handlechoice2();
        } else if (label.equals(FORMULA[5])) {
        	// �û�����ȷ�ϼ�
        	handleconfirm();
        }
        else {
            // �û������������
            handleOperator(label);
        }
    }

    
     //����Backspace�������µ��¼�
  
    private void handleBackspace() {
        String text = resultText.getText();
        int i = text.length();
        if (i > 0) {
            // �˸񣬽��ı����һ���ַ�ȥ��
            text = text.substring(0, i - 1);
            if (text.length() == 0) {
                // ����ı�û�������ݣ����ʼ���������ĸ���ֵ
                resultText.setText("0");
                firstDigit = true;
                operator = "=";
            } else {
                // ��ʾ�µ��ı�
                resultText.setText(text);
            }
        }
    }

    //�������ּ������µ��¼�
    private void handleNumber(String key) {
        if (needclear == true && formulatype != "") {
        	resultText.setText("");
        	needclear = false;
        }
    	if (firstDigit) {
            // ����ĵ�һ������
            resultText.setText(key);
        } else if ((key.equals(".")) && (resultText.getText().indexOf(".") < 0)) {
            // �������С���㣬����֮ǰû��С���㣬��С���㸽�ڽ���ı���ĺ���
            resultText.setText(resultText.getText() + ".");
        } else if (!key.equals(".")) {
            // �������Ĳ���С���㣬�����ָ��ڽ���ı���ĺ���
            resultText.setText(resultText.getText() + key);
        }
        // �Ժ�����Ŀ϶����ǵ�һ��������
        firstDigit = false;
    }

    //��������������µ��¼�
    private void handleC() {
        // ��ʼ���������ĸ���ֵ
        resultText.setText("0");
        firstDigit = true;
        operator = "=";
        formulatype = "";
        needclear = false;
        parameter1 = 0;
        parameter2 = 0;
    }

    //����Ӽ��˳������Ǻ���������������µ��¼�
    private void handleOperator(String key) {
        if (operator.equals("��")) {
            // ��������
            // �����ǰ����ı����е�ֵ����0
            if (getNumberFromText() == 0.0) {
                // �������Ϸ�
                operateValidFlag = false;
                resultText.setText("��������Ϊ��");
            } else {
                resultNum /= getNumberFromText();
            }
        } else if (operator.equals("1/x")) {
            // ��������
            if (resultNum == 0.0) {
                // �������Ϸ�
                operateValidFlag = false;
                resultText.setText("��û�е���");
            } else {
                resultNum = 1 / resultNum;
            }
        }else if (operator.equals("ˢ�Ͳ���Ԥ��")) {
            // ��������
     
            if (resultNum<1||resultNum>5) {
                // �������Ϸ�
                operateValidFlag = false;
                resultText.setText("����������Ч��ÿ��ˢ������1��5֮�䣡");
            } else {
                resultNum = 1500 * resultNum;
            }
        }else if (operator.equals("ʣ���������")) {
                // ���������
            	
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
            		resultText.setText("��ҩ�ɾȣ�");}
            	
            }
        else if (operator.equals("ÿ���Ƽ������")) {
                // ÿ���Ƽ����������
            	
            	
            	if(resultNum<60) {    //�ж����ɼ��Ƿ�С��60��
            		operateValidFlag = false;
              		resultText.setText("��ҩ�ɾȣ�");}
            	else {
            		 double l = 0;
            		 
            	     //���ɼ����뼰���ߵĲ��
            		resultNum = (120-resultNum)/getNumberFromText();    //�ڶ��λ�ȡʣ������
            	      l = resultNum;
            	      if(l > 5) {
            	    	  operateValidFlag = false;
              		resultText.setText("��ҩ�ɾȣ�");}
            	      else resultNum = l;
            	
            	}
            }
        else if (operator.equals("+")) {
            // �ӷ�����
            resultNum += getNumberFromText();
        } else if (operator.equals("-")) {
            // ��������
            resultNum -= getNumberFromText();
        } else if (operator.equals("x")) {
            // �˷�����
            resultNum *= getNumberFromText();
        } else if (operator.equals("sqrt")) {
            // ƽ��������
            resultNum = Math.sqrt(resultNum);
        } else if (operator.equals("%")) {
            // �ٷֺ����㣬����100
            resultNum = resultNum / 100;
        } else if (operator.equals("+/-")) {
            // ������������
            resultNum = resultNum * (-1);
        } else if (operator.equals("=")) {
            // ��ֵ����
            resultNum = getNumberFromText();
        } else if (operator.equals("sin")) {
        	// sin����
        	resultNum = Math.sin(resultNum);
        } else if (operator.equals("cos")) {
        	// cos����
        	resultNum = Math.cos(resultNum);
        } else if (operator.equals("tan")) {
        	// tan����
        	resultNum = Math.tan(resultNum);
        } else if (operator.equals("ln")) {
        	// �������㣨��eΪ�ף�
        	resultNum = Math.log(resultNum);
        } else if (operator.equals("��")) {
        	// �����
        	resultNum = Math.PI;
        } else if (operator.equals("n!")) {
        	// �׳�����
        	for(double i = resultNum - 1; i >= 1; i--)
        	{
        		resultNum = resultNum * i;
        	}
        } else if (operator.equals("x^2")) {
        	// ƽ������
        	resultNum = resultNum * resultNum;
        } else if (operator.equals("lg")) {
        	// �������㣨��10Ϊ�ף�
        	resultNum = Math.log10(resultNum);
        }
        if (operateValidFlag) {
            // ˫���ȸ�����������
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
        // ����������û����İ�ť
        operator = key;
        firstDigit = true;
        operateValidFlag = true;
    }

    //����BMI��ʽ�������µ��¼�
    private void handleBMI() {
    	resultText.setText("���������ֵ������Ϊ��λ��");
    }
    //����Ӱ๤�ʼ���������µ��¼�
    private void handleOverwork() {
    	resultText.setText("������������ͣ�1-�ڼ��գ�2-��Ϣ�գ�");
    }
    //����Ь�뻻��������µ��¼�
    private void handleShoesize() {
    	resultText.setText("�����뻻�����ͣ�1-��������룻2-��������룩");
    }
    //�������1�����µ��¼�
    private void handlechoice1() {
    	choosetype = 1;
    	if(formulatype == "�Ӱ๤��") {
    		resultText.setText("������ÿ�¹���");
    	}
    	else if(formulatype == "Ь�뻻��") {
    		resultText.setText("�������Ь��");
    	}
    }
    //�������2�����µ��¼�
    private void handlechoice2() {
    	choosetype = 2;
    	if(formulatype == "�Ӱ๤��") {
    		resultText.setText("������ÿ�¹���");
    	}
    	else if(formulatype == "Ь�뻻��") {
    		resultText.setText("��������Ь��");
    	}
    }
    //����ȷ�ϼ������µ��¼�
    private void handleconfirm() {
    	//���ѡ��ʽΪBMI��û��¼�����ʱ��¼���һ������
    	if(formulatype == "BMI" && numofparameters == 0) {
    		parameter1 = getNumberFromText();
    		numofparameters = 1;
    		needclear = true;
    		if(parameter1 == 0) {
    			resultText.setText("��߲���Ϊ0");
    			return;
    		}
    		resultText.setText("���������أ���kgΪ��λ��");
    	} //���ѡ��ʽΪBMI����¼��һ������ʱ��¼��ڶ�������
    	else if(formulatype == "BMI" && numofparameters == 1) {
    		resultText.setText("BMIΪ" + getNumberFromText()/(parameter1 * parameter1));
    		formulatype = "";
    		parameter1 = 0;
    		numofparameters = 0;
    	} //���ѡ��ʽΪ�Ӱ๤����δ¼�����ʱ��¼���һ������
    	else if(formulatype == "�Ӱ๤��" && numofparameters == 0) {
    		parameter1 = getNumberFromText() / 20.83;
    		numofparameters = 1;
    		needclear = true;
    		resultText.setText("������Ӱ�����");
    	} //���ѡ��ʽΪ�Ӱ๤������¼��һ������ʱ��¼��ڶ�������
    	else if(formulatype == "�Ӱ๤��" && numofparameters == 1) {
    		parameter2 = getNumberFromText();
    		numofparameters = 2;
    		needclear = true;
    		resultText.setText("������ÿ��Ӱ�ʱ������Сʱ�ƣ�");
    	} //���ѡ��ʽΪ�Ӱ๤������¼����������ʱ��¼�����������
    	else if(formulatype == "�Ӱ๤��" && numofparameters == 2) {
    		//���ѡ��ڼ���
    		if(choosetype == 1) resultText.setText("�Ӱ๤���ܶ�Ϊ" + getNumberFromText() * parameter1 * parameter2 * 3);
    		//���ѡ����ͨ��Ϣ��
    		else resultText.setText("�Ӱ๤���ܶ�Ϊ" + getNumberFromText() * parameter1 * parameter2 * 2 + "mm");
    		formulatype = "";
    		parameter1 = 0;
    		parameter2 = 0;
    		numofparameters = 0;
    	} //���ѡ��ʽΪЬ�뻻����δ¼�����ʱ��¼��Ψһһ������
    	else if(formulatype == "Ь�뻻��" && numofparameters == 0) {
    		//���ѡ����Ϊ���������
    		if(choosetype == 1) resultText.setText("��Ӧ��Ь����" + (getNumberFromText() + 10) * 5 + "mm");
    		//���ѡ����Ϊ���������
    		else resultText.setText("��Ӧ��Ь����" + (getNumberFromText() / 5 - 10));
    		formulatype = "";
    	}
    }
    /**
     * �ӽ���ı����л�ȡ����
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