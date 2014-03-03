

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Responsable {

	JFrame frame;
	/*
	 * initialisation de ces deux variables selon  la taille de la base et le nombre des lignes remplie
	 * pour savoir si la table des disponibilités et bien remplie ou non
	 */
	int taille_de_la_table=20;
    int nombre_de_ligne_saisie=20;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
			
			    Responsable window = new Responsable();
			  
				try {
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
	public Responsable() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 778, 455);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(199, 151, 486, 55);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tous les enseignants ont bien  saisie  leurs disponibilites");
		lblNewLabel.setBounds(30, 11, 412, 33);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		if(taille_de_la_table==nombre_de_ligne_saisie)
		{
		panel.setVisible(true);
		}

		else panel.setVisible(false);
		
		
		JButton affiche = new JButton("Afficher planning");
		affiche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		affiche.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				 Planning planning =new Planning();
		            System.out.println("passage à la fenetre planning");
		       //     planning.frame.setVisible(true);	
			}
		});
		affiche.setBounds(393, 261, 148, 23);
		frame.getContentPane().add(affiche);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setBounds(578, 261, 89, 23);
		frame.getContentPane().add(btnQuitter);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(199, 151, 486, 55);
		frame.getContentPane().add(panel_1_1);
		panel_1_1.setLayout(null);
		panel_1_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		JLabel label = new JLabel("Certains enseignants n'ont pas encore saisie leurs disponibilites");
		label.setBounds(30, 11, 446, 33);
		panel_1_1.add(label);
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Responsable.class.getResource("/Icons/icon_ECN.JPG")));
		label_1.setBounds(10, 11, 109, 128);
		frame.getContentPane().add(label_1);
		if(taille_de_la_table>nombre_de_ligne_saisie)
		{
		panel_1_1.setVisible(true);
		}

		else panel_1_1.setVisible(false);
	}

	
}
