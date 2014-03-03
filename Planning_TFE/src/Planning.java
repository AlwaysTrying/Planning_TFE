

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.SystemColor;

public class Planning extends JFrame {

    static JTable table;
    static DefaultTableModel model;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Planning frame = new Planning();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }); 
    }

    public Planning(){
    getContentPane().setBackground(SystemColor.control);
    setTitle("Portable test file viewing");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(50, 50, 1024, 768);
    getContentPane().setLayout(null);

    final String[] columnNames = {"8                 9", "9               10", "10             11", "11             12", "12             13", "13             14", "14             15", " 15           16 ", "16            17", "17             18"};
    final Object[][] data = {
                {"+","+","","","","~","","","",""},
                {"-","+","","","","","","","",""},
                {"+","+","","","~","","","","",""},
                {"+","+","","","","","~","","",""},
                {"+","-","","~","","","","","",""},
                {"+","-","","","","","","","",""},
                {"+","-","","","","","","","",""},
                {"-","+","~","","","","","","","+"}
    };
    model = new DefaultTableModel(data, columnNames);
    table = new JTable(data, columnNames);
    table.setModel(model);
    for(int i = 0 ; i < 8 ; i++){
            cellBG(table, i, 9);
    }
    JTableHeader header = table.getTableHeader();
    header.setFont(new Font("Times New Roman", Font.BOLD, 13));
    header.setBackground(Color.black);
    header.setForeground(Color.white);

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setSize(988, 618);
    scrollPane.setFont(new Font("Times New Roman", Font.BOLD, 13));
    scrollPane.setLocation(10, 60);
    getContentPane().add(scrollPane);
    }
    public Component cellBG(JTable table, int row, int column)
    {
        TableCellRenderer renderer = table.getCellRenderer(row, column);
        Component c = table.prepareRenderer(renderer, row, column);

        Object bøf = table.getValueAt(row,column);

        if(((String) bøf).startsWith("+"))
        {
           c.setBackground(Color.GREEN);
        }
        else if(((String) bøf).startsWith("-"))
        {
            c.setBackground(Color.RED);
        }
        else if(((String) bøf).startsWith("~"))
        {
            c.setBackground(Color.YELLOW);
        }
        else if(((String) bøf).equals(""))
        {
            c.setBackground(Color.WHITE);
        }
        return c;
    }
    }