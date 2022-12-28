package cadastroalunos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Visualizar extends JFrame{
    
    static JComboBox caixaHistorico;
    
    private ActionListener evtVer, evtFiltrar, evtLimparFiltro;
    
    private final JButton ver, filtrar, limparFiltro;
    private final JPanel fundo;
    private final JTextField campoFiltro;
    private final Font fonte;
    private final JCheckBox check;

    public Visualizar(String titulo, Dimension d){
        super(titulo);
        
        fonte = new Font("Calibri", 1, 20);
        fundo = new JPanel();
        campoFiltro = new JTextField();
        caixaHistorico = new JComboBox();
        ver = new JButton("Visualizar");
        filtrar = new JButton("Filtrar");
        limparFiltro = new JButton("Limpar Filtro");
        check = new JCheckBox("filtragem com contém");
        
        eventos();
        gerarComponentes(d);
    }
    
    private void gerarComponentes(Dimension d){
        //JFrame
        setSize(d);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        fundo.setSize(d);
        fundo.setBackground(Color.DARK_GRAY);
        
        //JComboBox
        caixaHistorico.setBounds(20, 160, 350, 33);
        caixaHistorico.setMaximumRowCount(5);
        caixaHistorico.setFont(fonte);
        
        //JTextField
        campoFiltro.setBounds(50, 50, 290, 33);
        campoFiltro.setFont(fonte);
        
        //JCheckBox
        check.setBounds(100, 100, 200, 25);
        check.setFont(new Font("Calibri",2,16));
        check.setIconTextGap(15);
        
        //BOTÕES
        ver.setBounds(125, 240, 130, 33);
        ver.setFont(fonte);
        ver.addActionListener(evtVer);
        
        limparFiltro.setBounds(200, 13, 140, 28);
        limparFiltro.setFont(fonte);
        limparFiltro.addActionListener(evtLimparFiltro);
        
        filtrar.setBounds(50, 13, 140, 28);
        filtrar.setFont(fonte);
        filtrar.addActionListener(evtFiltrar);
        
        
        add(caixaHistorico);
        add(ver);
        add(filtrar);
        add(limparFiltro);
        add(campoFiltro);
        add(check);
        add(fundo);
    }
    
    private void eventos(){
        evtVer = (e) ->{
            String nome = String.valueOf(caixaHistorico.getSelectedItem()).toUpperCase().trim();
            
            if (!nome.equals("NULL") && !nome.equals("")){
                VerHistorico verHistorico = new VerHistorico(nome, new Dimension(700, 600));
                verHistorico.setVisible(true);
            }
        };
        
        evtFiltrar = (e) ->{
            String procurar = campoFiltro.getText().toUpperCase().trim();
            
            if (!procurar.equals("NULL") && !procurar.equals("")){
                String teste;
                String[] listaNomes = Main.arquivo.getNomesArquivo();

                caixaHistorico.removeAllItems();

                if (check.isSelected()){
                    for (String nome : listaNomes){
                        if (nome.contains(procurar)){
                            caixaHistorico.addItem(nome);
                        }
                    } 
                }else{
                    for (String nome : listaNomes){
                        teste = "";
                        for (int i=0; i<procurar.length() && i<nome.length(); i++){
                            teste += nome.charAt(i);
                        }

                        if (teste.equals(procurar)){
                            caixaHistorico.addItem(nome);
                        }
                    }
                }
            }
        };
        
        evtLimparFiltro = (e) ->{
            if (!"".equals(campoFiltro.getText().trim())){
                caixaHistorico.removeAllItems();
                String[] listaNomes = Main.arquivo.getNomesArquivo();
                for (String nome : listaNomes){
                    caixaHistorico.addItem(nome);
                }
            }
            caixaHistorico.setSelectedIndex(-1);
            campoFiltro.setText("");
        };
    }
    
    static void atualizarHistorico(){
        caixaHistorico.removeAllItems();
        String[] listaNomes = Main.arquivo.getNomesArquivo();
        for (String nome : listaNomes){
            caixaHistorico.addItem(nome);
        }
        caixaHistorico.setSelectedIndex(-1);
    }
}
