package cadastroalunos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VerHistorico extends JFrame{
    
    private final JTextArea areaTexto;
    private final JScrollPane rolagem;
    private final ArrayList lista;
    
    public VerHistorico(String titulo, Dimension d){
        super(titulo);
        
        areaTexto = new JTextArea();
        rolagem = new JScrollPane(areaTexto, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        lista = new ArrayList();
        
        gerarComponentes(d);
    }
    
    private void gerarComponentes(Dimension d){
        //JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(d);
        setLocationRelativeTo(null);
        setResizable(false);
        
        
        //JTextArea
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Calibri", 4, 20));
        areaTexto.setBackground(Color.DARK_GRAY);
        areaTexto.setForeground(Color.WHITE);
        
        String nome = (String)Visualizar.caixaHistorico.getSelectedItem();
        
        Main.arquivo.getConteudoArquivo(nome, lista);
        
        String x = "                      ";
        while (!lista.isEmpty()){
            areaTexto.append("  Matéria: "+lista.get(0)+"\n");
            lista.remove(0);
            
            areaTexto.append("                1ºBIMESTRE        2ºBIMESTRE       3ºBIMESTRE        4ºBIMESTRE\n");
            areaTexto.append("  Nota(1)       "+lista.get(0)+x+lista.get(4)+x+lista.get(8)+x+lista.get(12)+"\n");
            areaTexto.append("  Nota(2)       "+lista.get(1)+x+lista.get(5)+x+lista.get(9)+x+lista.get(13)+"\n");
            areaTexto.append("  Nota(3)       "+lista.get(2)+x+lista.get(6)+x+lista.get(10)+x+lista.get(14)+"\n");
            areaTexto.append("  Média:        "+lista.get(3)+x+lista.get(7)+x+lista.get(11)+x+lista.get(15)+"\n");
            
            for (int i=0; i<16; i++){
                lista.remove(0);
            }
            areaTexto.append("\n");
        }
        
        add(rolagem);
    }
}
