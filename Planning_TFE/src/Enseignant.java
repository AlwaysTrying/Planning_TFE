import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;


public class Enseignant {

	JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Enseignant window = new Enseignant();
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
	public Enseignant() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 629, 497);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Formulaire de disponibilit\u00E9 pour les soutenances TFE");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setForeground(new Color(51, 153, 204));
		lblNewLabel.setBounds(119, 42, 389, 31);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "G\u00E9neralit\u00E9s", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 153, 204)));
		panel.setBounds(70, 102, 470, 143);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(20, 31, 37, 47);
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\WAFA\\git\\Planning_TFE\\Planning_TFE\\src\\Icons\\personne.JPG"));
		panel.add(lblNewLabel_1);
		
		JLabel lblNom = new JLabel("Nom");
		lblNom.setBounds(99, 31, 46, 14);
		panel.add(lblNom);
		
		JLabel lblPrnom = new JLabel("Pr\u00E9nom");
		lblPrnom.setBounds(99, 64, 46, 14);
		panel.add(lblPrnom);
		
		JLabel lblFonction = new JLabel("Fonction");
		lblFonction.setBounds(99, 92, 46, 14);
		panel.add(lblFonction);
		
		JLabel lblNewLabel_2 = new JLabel("Nom de l'enseignant");
		lblNewLabel_2.setBounds(209, 31, 153, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Pr\u00E9nom de l'enseignant");
		lblNewLabel_3.setBounds(209, 64, 134, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblFonctionnalitDeLenseignant = new JLabel("Fonctionnalit\u00E9 de l'enseignant");
		lblFonctionnalitDeLenseignant.setBounds(209, 92, 153, 14);
		panel.add(lblFonctionnalitDeLenseignant);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Disponibilit\u00E9s", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 153, 204)));
		panel_1.setBounds(70, 272, 470, 156);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblLaDateDe = new JLabel("La date de soutenance TFE est le .......");
		lblLaDateDe.setBounds(10, 31, 191, 14);
		panel_1.add(lblLaDateDe);
		
		JLabel lblVeuillezConfirmerLa = new JLabel("Veuillez confirmer la date ou choisir une autre date si vous ne serai pas disponible.");
		lblVeuillezConfirmerLa.setBounds(10, 56, 428, 14);
		panel_1.add(lblVeuillezConfirmerLa);
		
		JRadioButton rdbtnConfirmerLaDate = new JRadioButton("Confirmer la date");
		rdbtnConfirmerLaDate.setBounds(92, 82, 151, 23);
		panel_1.add(rdbtnConfirmerLaDate);
		
		JRadioButton rdbtnChoisirUneAutre = new JRadioButton("Choisir une autre date");
		rdbtnChoisirUneAutre.setBounds(92, 113, 151, 23);
		panel_1.add(rdbtnChoisirUneAutre);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(277, 113, 161, 20);
		panel_1.add(dateChooser);
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
