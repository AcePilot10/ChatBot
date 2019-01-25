package com.codygordon.chatbot;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;

public class ChatbotWindow extends JFrame {

	private static final long serialVersionUID = -8461494319939040202L;
	
	private JPanel contentPane;
	private JTextField txtInput;
	private JTextArea txtResponse;

	private ChatterBot bot;
	private ChatterBotSession session;

	private int xx, xy;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatbotWindow frame = new ChatbotWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initChatBot() {
		try {
			ChatterBotFactory factory = new ChatterBotFactory();
	        bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
	        session = bot.createSession();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ChatbotWindow() {
		setTitle("Chat App");
		setUndecorated(true);
		
		initChatBot();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 476);
		contentPane = new JPanel();

		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				int x = arg0.getXOnScreen();
				int y = arg0.getYOnScreen();
				setLocation(x - xx, y - xy);
			}
		});
		
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtInput = new JTextField();
		txtInput.setBounds(275, 88, 313, 37);
		txtInput.setBackground(Color.WHITE);
		contentPane.add(txtInput);
		txtInput.setColumns(10);
		
		txtResponse = new JTextArea();
		txtResponse.setEditable(false);
		txtResponse.setBounds(275, 178, 313, 100);
		contentPane.add(txtResponse);
		txtResponse.setColumns(10);
		txtResponse.setLineWrap(true);
		txtResponse.setWrapStyleWord(true);
		
		RobotPanel panel = new RobotPanel(250, 350, "robot2.jpg");
		panel.setBounds(10, 44, 250, 350);
		contentPane.add(panel);
		
		JButton btnChat = new JButton("Chat");
		btnChat.setBackground(Color.WHITE);
		btnChat.setBounds(350, 362, 169, 64);
		btnChat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chatButtonClicked();	
			}
		});
		
		getRootPane().setDefaultButton(btnChat);
		contentPane.add(btnChat);
		
		JLabel lblInput = new JLabel("Input");
		lblInput.setHorizontalAlignment(SwingConstants.CENTER);
		lblInput.setBounds(420, 63, 46, 14);
		contentPane.add(lblInput);
		
		JLabel lblOutput = new JLabel("Output");
		lblOutput.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutput.setBounds(420, 153, 46, 14);
		contentPane.add(lblOutput);
		
		JLabel lblX = new JLabel("X");
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		lblX.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblX.setHorizontalAlignment(SwingConstants.TRAILING);
		lblX.setBounds(624, 11, 46, 14);
		contentPane.add(lblX);
	}
	
	private void chatButtonClicked() {
		new Thread(() -> {
			try {
			 String input = txtInput.getText();
			 String response = session.think(input);
			 txtResponse.setText("Bot> " + response);
			 txtInput.setText("");
				} catch(Exception e) { }
		 }).start();
	}
}