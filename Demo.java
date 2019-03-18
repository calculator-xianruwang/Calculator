import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
public class Demo extends JFrame {
	private JFrame jf=new JFrame("Calculator"); //����
	private JPanel centerPanel=new JPanel();    //�м����
	private JPanel bottomPanel=new JPanel();    //�ײ����
	
	//�м�������ť
	private JButton Backbtn=new JButton("Back");
	private JButton CEbtn=new JButton("CE");
	private JButton Cbtn=new JButton("C");
	
	//��ʼ�����ܼ�
	String[] nums={"7","8","9","+","4","5","6","-","1","2","3","*","0",".","=","/"};
	private JButton btn7=new JButton(nums[0]);
	private JButton btn8=new JButton(nums[1]);
	private JButton btn9=new JButton(nums[2]);
	private JButton btnAdd=new JButton(nums[3]);
	private JButton btn4=new JButton(nums[4]);
	private JButton btn5=new JButton(nums[5]);
	private JButton btn6=new JButton(nums[6]);
	private JButton btnMimus=new JButton(nums[7]);
	private JButton btn1=new JButton(nums[8]);
	private JButton btn2=new JButton(nums[9]);
	private JButton btn3=new JButton(nums[10]);
	private JButton btnMultipus=new JButton(nums[11]);
	private JButton btn0=new JButton(nums[12]);
	private JButton btnDot=new JButton(nums[13]);
	private JButton btnResult=new JButton(nums[14]);
	private JButton btnDivide=new JButton(nums[15]);
	
	//���������ı���
	private JTextField txt=new JTextField(15);
	private List<String>lists=new ArrayList<String>(); //������¼�û���������ֺͲ�����
	
	//���������������
	public static void main(String[] args){
		try {
			new Demo().init();
		} catch (Exception e) {
			System.out.println("�����쳣��ֹ");
			System.exit(0);   //�˳������
		}
	}
	//��ʼ��
	public void init() throws Exception{
		//ʹ�����񲼾ַ�ʽ
		bottomPanel.setLayout(new GridLayout(4,4,3,3)); //�������¼����3
		//�����ܼ���ӵ��ײ������
		bottomPanel.add(btn7);
		bottomPanel.add(btn8);
		bottomPanel.add(btn9);
		bottomPanel.add(btnAdd);
		bottomPanel.add(btn4);
		bottomPanel.add(btn5);
		bottomPanel.add(btn6);
		bottomPanel.add(btnMimus);
		bottomPanel.add(btn1);
		bottomPanel.add(btn2);
		bottomPanel.add(btn3);
		bottomPanel.add(btnMultipus);
		bottomPanel.add(btn0);
		bottomPanel.add(btnDot);
		bottomPanel.add(btnResult);
		bottomPanel.add(btnDivide);
		
		//���м��������ť��ӵ��м����
		centerPanel.add(Backbtn);
		centerPanel.add(CEbtn);
		centerPanel.add(Cbtn);
		
		jf.add(txt,BorderLayout.NORTH);   //�������ı�����ӵ����ڵ� ����
		jf.add(centerPanel);              //���м������ӵ������м䣨Ĭ�����м䣩
		jf.add(bottomPanel,BorderLayout.SOUTH);  //���ײ������ӵ����ڵ��ϲ�
		
		//ΪC��ť����¼�����
		Cbtn.addActionListener(e->{
			lists.clear();    //�������е���������
			txt.setText("");  //�� �ı���� ֵ����Ϊ��
		});
		//ΪBack��ť����¼�����         ʹ��Lamdba���ʽ
		Backbtn.addActionListener(e->{
			String text=txt.getText(); //�õ�������ı�
			if("".equals(text) || text.length()==1){ 
				txt.setText(""); //����ǿ��ı������ı�����Ϊ1��ֱ����Ϊ��
				return;
			}
			if(text.length()>1){ //����ı��ĳ��ȴ���1��Ҫ��ǰ��ȡ
				text=text.substring(0,text.length()-1);
				txt.setText(text);
			}
		});
		
		//ΪCE��ť����¼�����
		CEbtn.addActionListener(e->{
			//�õ������ı���
			String text=txt.getText();
			if("".equals(text)){
				return;
			}
			if("+".equals(text) || "-".equals(text) || "*".equals(text) || "/".equals(text)){
				//��ʾҪ��+ - * /���,����Ҫ�Ѽ����еĵ�һ����Ҳ�Ƴ����ϣ���Ϊ�û����ܵ���+��ʱ�򣬾͵�CE������ʱ����ٵ����ּ��ͻ�������⣩
				lists.remove(0);
			}
			txt.setText("");  //���ı�ֱ�����
		});
		
		//Ϊ���ְ�ť��Ӽ����¼�     ʹ�������ڲ���
		ActionListener numBtnListener=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//�õ������ı��������
				String text=txt.getText();
				//�ж���û����仰 ��0��������ĸ������������ԣ�,����� ����������ְ�ť��ʱ��Ҫ����ʧ
				if("��������Ϊ0".equals(text)){
					txt.setText(e.getActionCommand());
					return;
				}
				//�����һ������0���Ͳ����������������ˣ�ֻ������С������
				if("0".equals(text)){
					return;
				}
				if("".equals(text)){  //���ı�����û���ݵ�ʱ�򣬰ѵ�ǰ�����������ʾ��ȥ
					txt.setText(e.getActionCommand());
				}else{
					//�����ǰ����������ݣ������ǲ�����ʱ����¼�¸ò�����
					if(text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/")){
						lists.add(text);  //����������ӵ�������
						txt.setText("");
						text=""; //���õ����ı����գ�Ҳ���ǽ�+��-�� *��/����ֵ
					}
					text+=e.getActionCommand();
					txt.setText(text);
				}
			}
		};
		//Ϊ����ע�� ������
		btn1.addActionListener(numBtnListener);
		btn2.addActionListener(numBtnListener);
		btn3.addActionListener(numBtnListener);
		btn4.addActionListener(numBtnListener);
		btn5.addActionListener(numBtnListener);
		btn6.addActionListener(numBtnListener);
		btn7.addActionListener(numBtnListener);
		btn8.addActionListener(numBtnListener);
		btn9.addActionListener(numBtnListener);
		
		//Ϊ0��ť����¼�����
		btn0.addActionListener(e->{
			//�õ������ı���
			String text=txt.getText();
			if("0".equals(text)){  //�����һ������0�������ٳ���0��
				return;
			}
			//������ֲ�����,�ͼ�¼����
			if("+".equals(text) || "-".equals(text) || "*".equals(text) || "/".equals(text)){
				lists.add(text);
				text="";  //�Ѳ��������
			}
			text+=e.getActionCommand();
			txt.setText(text);
		});
		//Ϊ��  .��ť ����¼�����  ʹ��Lamdba���ʽ
		btnDot.addActionListener(e->{
			//�õ������ı���
			String text=txt.getText();
			if("".equals(text)){
				return;
			}
			//�ж��ı��Ƿ�Ϊ+ - * / .
			if("+".equals(text) || "-".equals(text) || "*".equals(text) || "/".equals(text)){
				return;
			}
			//��������ֺ����Ѿ���С�����ˣ���ô�Ͳ���������������С������
			if(text.contains(".")){
				return;
			}
			text+=e.getActionCommand();
			txt.setText(text); //���ý�ȥ
		});
		
		//Ϊ+-*/����¼�����    ʹ�������ڲ���  
		ActionListener operationBtnListener=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//��ȡ�����ı����ڵ�����
				String text=txt.getText();
				if("".equals(text)){
					return;
				}
				if("+".equals(text) || "-".equals(text) || "*".equals(text) || "/".equals(text)){
					return;
				}
				//��������ӵ�������
				lists.add(text);
				//����������
				txt.setText("");
				//�Ѱ�ť���������ʾ��ȥ
				txt.setText(e.getActionCommand());
			}
		};
		//Ϊ* - + /ע�������
		btnAdd.addActionListener(operationBtnListener);
		btnMimus.addActionListener(operationBtnListener);
		btnMultipus.addActionListener(operationBtnListener);
		btnDivide.addActionListener(operationBtnListener);
		
		//Ϊ =��ť����¼�����
		ActionListener resultBtnListener=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//�жϼ����е�����
				if(lists.isEmpty()){
					return;
				}
				//��ȡ�ı�����������
				String text=txt.getText();
				if("".equals(txt) || "+".equals(text) || "-".equals(text) || "*".equals(text) || "/".equals(text)){
					return;
				}
				//������ӵ�������
				lists.add(text);
				if(lists.size()<3){
					return;
				}
				String one=lists.get(0);  //�õ������еĵ�һ����
				String two=lists.get(1); //�õ������еĵڶ�����
				String three=lists.get(2); //�õ������еĵ�������
				switch(two){
				case "+":
					double i=Double.parseDouble(one);
					double j=Double.parseDouble(three);
					txt.setText((i+j)+""); //��ʾ���
					break;
				case "-":
					double x=Double.parseDouble(one);
					double y=Double.parseDouble(three);
					txt.setText((x-y)+""); //��ʾ���
					break;
					
				case "*":
					double a=Double.parseDouble(one);
					double b=Double.parseDouble(three);
					txt.setText((a*b)+"");
					break;
				case "/":
					double k=Double.parseDouble(one);
					double h=Double.parseDouble(three);
					if(h==0){
						txt.setText("��������Ϊ0");
						lists.clear();
						return;
					}
					txt.setText((k/h)+"");
					break;
				}
				//�������е��������
				lists.clear();
			}
		};
		//Ϊ=�ȺŰ�ťע�������
		btnResult.addActionListener(resultBtnListener);
		//�Զ��崰�ڵ�ͼ��
		ImageIcon image=new ImageIcon("image/girl.jpg"); //ͼƬλ��
		image.setImage(image.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
		jf.setIconImage(image.getImage());
		//����UI�ķ��ΪNimbus
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		//����f���������������UI
		SwingUtilities.updateComponentTreeUI(jf.getContentPane());
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//��X�رմ���
		jf.setLocation(400, 200); //��ʼ��ʱ��λ
		jf.setResizable(false);   //��ֹ��ҷ�ı䴰�ڴ�С
		jf.pack();               //�ô��ڵĴ�С����Ӧ
		jf.setVisible(true);  //��ʾ����
	}
}