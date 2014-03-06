import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


public class Interface_Enseignant_dispo {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface_Enseignant_dispo window = new Interface_Enseignant_dispo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interface_Enseignant_dispo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Border emptyBorder = BorderFactory.createEmptyBorder();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 642, 686);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("Formulaire de disponibilit\u00E9s pour les soutenances TFE");
		label.setForeground(new Color(51, 153, 204));
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(108, 50, 389, 31);
		frame.getContentPane().add(label);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "G\u00E9neralit\u00E9s", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 153, 204)));
		panel.setBounds(43, 92, 551, 143);
		frame.getContentPane().add(panel);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(20, 31, 37, 47);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Nom");
		label_2.setBounds(99, 31, 46, 14);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("Pr\u00E9nom");
		label_3.setBounds(99, 64, 46, 14);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("Fonction");
		label_4.setBounds(99, 92, 46, 14);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("Nom de l'enseignant");
		label_5.setBounds(209, 31, 153, 14);
		panel.add(label_5);
		
		JLabel label_6 = new JLabel("Pr\u00E9nom de l'enseignant");
		label_6.setBounds(209, 64, 134, 14);
		panel.add(label_6);
		
		JLabel label_7 = new JLabel("Fonctionnalit\u00E9 de l'enseignant");
		label_7.setBounds(209, 92, 153, 14);
		panel.add(label_7);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Disponibilit\u00E9s", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 153, 204)));
		panel_1.setBounds(43, 266, 551, 295);
		frame.getContentPane().add(panel_1);
		
		JLabel lblDatesoutenance = new JLabel("date-soutenance");
		lblDatesoutenance.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDatesoutenance.setBounds(242, 31, 167, 14);
		panel_1.add(lblDatesoutenance);
		
		JLabel label_9 = new JLabel("La date des soutenances est : ");
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_9.setBounds(38, 31, 265, 14);
		panel_1.add(label_9);
		
		JLabel lblVeuillezEntrerVos = new JLabel("Veuillez entrer vos disponibilit\u00E9s :");
		lblVeuillezEntrerVos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVeuillezEntrerVos.setBounds(38, 60, 227, 14);
		panel_1.add(lblVeuillezEntrerVos);
		
		JLabel lblCrnau = new JLabel("Disponibilit\u00E9 1 :        De ");
		lblCrnau.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCrnau.setBounds(38, 99, 169, 14);
		panel_1.add(lblCrnau);
		
		final JLabel lblDisponibilit = new JLabel("Disponibilit\u00E9 2 :        De");
		lblDisponibilit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDisponibilit.setBounds(38, 153, 151, 14);
		
		panel_1.add(lblDisponibilit);
		
		  final Vector comboBoxItems=new Vector();
		 String [] tab1 = {"","8h", "9h", "10h","11h"};
		
		 for (int i = 0 ; i<tab1.length;i++){
			 comboBoxItems.add(tab1[i]); 
			 
		 }

		 final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);

		final JComboBox comboBox = new JComboBox(model);
		
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		comboBox.setToolTipText("");
		comboBox.setBounds(217, 93, 50, 20);
		panel_1.add(comboBox);
		
		JLabel lblA = new JLabel("A:");
		lblA.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblA.setBounds(297, 96, 46, 14);
		panel_1.add(lblA);
		
		final JComboBox comboBox_1 = new JComboBox();
		
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		comboBox_1.setToolTipText("");
		comboBox_1.setBounds(326, 93, 50, 20);
		panel_1.add(comboBox_1);
		
		  Vector comboBoxItems1=new Vector();
		 String [] tab3 = {"", "14h","15h", "16h","17h"};
		
		 for (int i = 0 ; i<tab3.length;i++){
			 comboBoxItems1.add(tab3[i]); 
			 
		 }

		 final DefaultComboBoxModel model1 = new DefaultComboBoxModel(comboBoxItems1);
		
		
		final JComboBox comboBox_2 = new JComboBox(model1);
		
		comboBox_2.setToolTipText("");
		comboBox_2.setBounds(217, 147, 50, 20);
		
		panel_1.add(comboBox_2);
		
		final JLabel label_8 = new JLabel("A:");
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_8.setBounds(297, 150, 46, 14);
		panel_1.add(label_8);
		
		final JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setToolTipText("");
		comboBox_3.setBounds(326, 147, 50, 20);
		panel_1.add(comboBox_3);
		
		JButton btnConfirmer = new JButton("Confirmer");
		btnConfirmer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String [] x = null;
				x=getDisponibilité(comboBox,comboBox_1,comboBox_2,comboBox_3);
				
				for (int i = 0 ; i<x.length;i++){ System.out.println(x[i]);}
				
				connexion_base cnx = new connexion_base();
				try {
					cnx.connexion_base();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				insert_disponibilite (cnx,x);
				
			}
		});
		btnConfirmer.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnConfirmer.setBounds(402, 594, 95, 23);
		frame.getContentPane().add(btnConfirmer);
		
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
			
				int k = comboBox.getSelectedIndex();
				System.out.println(k);
				String [] tab2 = {"9h", "10h","11h","12h"};
				 Vector comboBoxItems=new Vector();
				for (int i = k-1 ; i<tab2.length;i++){
					 comboBoxItems.add(tab2[i]); 
					 
				 }
				  DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
				comboBox_1.setModel(model);
				
			}
		});
		

		comboBox_2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				int k = comboBox_2.getSelectedIndex();
				System.out.println(k);
				String [] tab2 = {"15h ", "16h","17h","18h"};
				 Vector comboBoxItems=new Vector();
				for (int i = k-1 ; i<tab2.length;i++){
					 comboBoxItems.add(tab2[i]); 
					 
				 }
				  DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
				comboBox_3.setModel(model);
	
			}
		});

	}
	public String[] getDisponibilité(JComboBox comboBox, JComboBox comboBox1, JComboBox comboBox2,JComboBox comboBox3){
		
		String[] disponibilité=new String[4];
		String Dispo= comboBox.getSelectedItem().toString();
		String Dispo1= comboBox1.getSelectedItem().toString();
		String Dispo2 = comboBox2.getSelectedItem().toString();
		String Dispo3 =  comboBox3.getSelectedItem().toString();
		
		disponibilité[0] = Dispo.substring(0,Dispo.length()-1);
		disponibilité[1] = Dispo1.substring(0,Dispo1.length()-1);
		disponibilité[2] = Dispo2.substring(0,Dispo2.length()-1);
		disponibilité[3] = Dispo3.substring(0,Dispo3.length()-1);
		return disponibilité; 
	}



public void insert_disponibilite (connexion_base cnx,String[]Disponibilité){
	Statement st = null;
	 try {
		st = cnx.getst();
		String sql ="INSERT INTO `disponibilite`( `disponibilite1`, `disponibilite2`, `disponibilite3`, `disponibilite4`, `id_enseignant`) VALUES ('"+Disponibilité[0]+ "','"+Disponibilité[1]+
				"','"+Disponibilité[2]+
				"','"+Disponibilité[3]+ "',2)";
				
		st.executeUpdate(sql);
		
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}

}
