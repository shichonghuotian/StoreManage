package com.wy.store.modules.login;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

//登陆页面
public class LoginViewImpl extends JFrame implements LoginView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField nameTextField;
	JPasswordField pwdTextField;
	JButton loginButton;

	LoginPresenter presenter;
	
	public LoginViewImpl() {
		// TODO Auto-generated constructor stub
		
		setPresenter(new LoginPresenter());
		
		init();
	}
	
	public void init() {
		
		//生成frame
		setTitle("登陆");

		setSize(300, 300);
//		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setDefaultLookAndFeelDecorated(true);
		
//		initLayout();
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		add(panel);
		initLayout(panel);
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		menuBar.add(fileMenu);
		
		JMenuItem newMenuItem = new JMenuItem("new file");
		
		fileMenu.add(newMenuItem);
		
		this.setJMenuBar(menuBar);
		pack();
		setVisible(true);

	}
	
	public void initLayout(JPanel panel) {
//		panel.setLayout(null);
		
		nameTextField  = new JTextField();
		
		pwdTextField = new JPasswordField();
		
		loginButton = new JButton("Lgoin");
		
		
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				login();
				
			}
		});
		panel.setLayout(new GridLayout(3, 1,0 ,10) );

		panel.add(nameTextField);
		panel.add(pwdTextField);
		panel.add(loginButton);
		
		pack();
	}

	public void login() {
	
		this.presenter.login(nameTextField.getText(), pwdTextField.getPassword().toString());
		
	}
	
	@Override
	public void setPresenter(LoginPresenter presenter) {
		// TODO Auto-generated method stub
		
		this.presenter = presenter;
		this.presenter.attach(this);
	}

	@Override
	public void login(boolean canLogin) {
		// TODO Auto-generated method stub
		if(canLogin) {
//  			new LoginViewImpl();

//			JOptionPane.showMessageDialog(null,  "登陆成功");
			
			JOptionPane.showMessageDialog(null, "登陆成功", "tip", JOptionPane.PLAIN_MESSAGE);

		}else {
//  			new LoginViewImpl();
			JOptionPane.showMessageDialog(null, "输入正确的用户名密码", "tip", JOptionPane.PLAIN_MESSAGE);

//			JOptionPane.showMessageDialog(null,  "请输入正确的用户名密码");

		}
		System.out.println("login is = " + canLogin);
	}
	
}
