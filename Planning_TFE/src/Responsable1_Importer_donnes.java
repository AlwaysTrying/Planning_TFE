import java.awt.EventQueue;

import javax.swing.AbstractButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;


public class Responsable1_Importer_donnes {

	 JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Responsable1_Importer_donnes window = new Responsable1_Importer_donnes();
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
	public Responsable1_Importer_donnes() {
		initialize();
	}
	
	private void openActionPerformed(java.awt.event.ActionEvent evt) {                                     

	     //création dun nouveau filechosser
	    JFileChooser chooser = new JFileChooser();

	//intitulé du bouton
	chooser.setApproveButtonText("Choix du fichier"); 
	//affiche la boite de dialogue 
	chooser.showOpenDialog(null); 
	if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
	{
	AbstractButton status = null;
	status.setText(chooser.getSelectedFile().getAbsolutePath());
	}                                    
	}
	
	private String showOpenFileDialog(){
		
		FileFilter xls = new FiltreSimple("Fichiers xls",".xls");
		JFileChooser chooser = new JFileChooser();
		//chooser.setFileSelectionMode(JFileChooser..DIRECTORIES_ONLY);
		chooser.setCurrentDirectory(new File("/"));
		//chooser.changeToParentDirectory();
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.addChoosableFileFilter(xls);
		
		
		
		int retour=chooser.showOpenDialog(null);	
	
		if(retour==JFileChooser.APPROVE_OPTION){
			   // un fichier a été choisi (sortie par OK)
			   // nom du fichier  choisi 
			chooser.getSelectedFile().getName();
			   // chemin absolu du fichier choisi
			chooser.getSelectedFile().getAbsolutePath();
			  
			   //System.out.println(chooser.getSelectedFile().getAbsolutePath());
			   return chooser.getSelectedFile().getAbsolutePath();
			   
			}else
				//System.out.println("ici pas de fichier choisie !");// pas de fichier choisi
				return null;
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 10);
		frame.getContentPane().add(panel);
		
		JButton btnParcourrir = new JButton("Parcourrir");
		btnParcourrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				connexion_base cnx = new connexion_base();
				try {
					cnx.connexion_base();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Statement st = null;
				 try {
					st = cnx.getst();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				insertion_matrice_base i = new insertion_matrice_base();
				
				
				ReadExcel test = new ReadExcel();
				if (showOpenFileDialog() == null){
					System.out.println("fichier non choisie");
					}
				else {
				 test.setInputFile(showOpenFileDialog()); 
				
				try {
					test.read();
					int nbl = test.get_nbreligne_fichier();
					int nbc = test.get_nbrecolonne_fichier();
					String matrice [][]  = new String [nbl][nbc];
					matrice = test.get_matrice();
					
					i.insertion_matrice(matrice,st,nbl);
					///
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				}
		
			}});
		btnParcourrir.setBounds(178, 167, 99, 23);
		btnParcourrir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
			}
		});
		frame.getContentPane().add(btnParcourrir);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setBounds(287, 167, 89, 23);
		frame.getContentPane().add(btnQuitter);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(31, 41, 372, 89);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel labelinfo = new JLabel("Veuillez indiquer le chemin du fichier contenant les donn\u00E9es des \u00E9leves");
		labelinfo.setBounds(10, 11, 352, 67);
		panel_1.add(labelinfo);
	}
}
