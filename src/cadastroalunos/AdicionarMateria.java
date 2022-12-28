package cadastroalunos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AdicionarMateria extends JFrame{
    
    static JComboBox caixaNomes;
    
    Label tnome, tmateria;
    JTextField[] notaBimestre;
    JTextField campoMateria;
    JButton salvar, limpar, filtrar, redefinir, limpaNome;
    JPanel fundo;
    Font fonte;
    JCheckBox check;
    
    private final DecimalFormat f;
    private final Label[] labelNota, labelBimestre;
    
    private ActionListener evtLimpar, evtSalvar, evtFiltrar, evtRedefinir, evtLimpaNome;
    
    public AdicionarMateria(String titulo, Dimension d){
        super(titulo);
        
        f = new DecimalFormat("0.00");
        fonte = new Font("Calibri", 1, 20);
        fundo = new JPanel();
        salvar = new JButton("Salvar");
        limpar = new JButton("Limpar notas");
        filtrar = new JButton("Filtrar nomes");
        redefinir = new JButton("Restaurar nomes");
        check = new JCheckBox("Filtragem com contém");
        limpaNome = new JButton(new ImageIcon(Main.localIcons+"\\clean.png"));
        tnome = new Label("Nome:");
        tmateria = new Label("Matéria:");
        labelBimestre = new Label[4];
        labelNota = new Label[3];
        campoMateria = new JTextField();
        caixaNomes = new JComboBox();
        notaBimestre = new JTextField[12];
        
        eventos();
        gerarComponentes(d);
    }
    
    private void gerarComponentes(Dimension d){
        //TELA
        setSize(d);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        //FUNDO
        fundo.setSize(d);
        fundo.setBackground(Color.GRAY);
        
        //BOTÕES
        salvar.setBounds(110, 400, 200, 33);
        salvar.setFont(fonte);
        salvar.addActionListener(evtSalvar);
        salvar.setBackground(new Color(0, 180, 0));
        salvar.setForeground(Color.BLACK);
        
        limpar.setBounds(350, 400, 200, 33);
        limpar.setFont(fonte);
        limpar.addActionListener(evtLimpar);
        
        filtrar.setBounds(120, 88, 200, 28);
        filtrar.setFont(fonte);
        filtrar.addActionListener(evtFiltrar);
        
        redefinir.setBounds(350, 88, 200, 28);
        redefinir.setFont(fonte);
        redefinir.addActionListener(evtRedefinir);
        
        limpaNome.setBounds(610, 10, 33, 33);
        limpaNome.addActionListener(evtLimpaNome);
        
        //JLabel
        tnome.setBounds(15, 15, 80, 20);
        tnome.setBackground(Color.GRAY);
        tnome.setForeground(Color.BLACK);
        tnome.setFont(fonte);
        
        tmateria.setBounds(15, 325, 80, 20);
        tmateria.setBackground(Color.GRAY);
        tmateria.setForeground(Color.BLACK);
        tmateria.setFont(fonte);
        
        //JComboBox
        caixaNomes.setBounds(95, 10, 500, 33);
        caixaNomes.setMaximumRowCount(4);
        caixaNomes.setEditable(true);
        caixaNomes.setFont(fonte);
        
        //JTextField
        campoMateria.setBounds(100, 320, 500, 33);
        campoMateria.setFont(fonte);
        
        //JCheckBox
        check.setBounds(230, 55, 200, 25);
        check.setFont(new Font("Calibri",2,16));
        check.setIconTextGap(15);
        
        //Labels do bimestre
        int x=100, y, cont=0;
        
        for (int i=0; i<4; i++){
            labelBimestre[i] = new Label((i+1)+"ºBimestre");
            labelBimestre[i].setBounds(x, 155, 120, 20);
            labelBimestre[i].setBackground(Color.GRAY);
            labelBimestre[i].setForeground(Color.BLACK);
            labelBimestre[i].setFont(fonte);
            add(labelBimestre[i]);
            x+=125;
        }
        
        //Labels das notas
        y = 190;
        for (int i=0; i<3; i++){
            labelNota[i] = new Label("Nota("+(i+1)+")");
            labelNota[i].setBounds(25, y, 80, 20);
            labelNota[i].setBackground(Color.GRAY);
            labelNota[i].setForeground(Color.BLACK);
            labelNota[i].setFont(fonte);
            add(labelNota[i]);
            y+=30;
        }
        
        //Labels dos inputs bimestre
        x = 120;
        for (int i=0; i<4; i++){
            y=80;
            for (int j=0; j<3; j++){
                notaBimestre[cont] = new JTextField();
                notaBimestre[cont].setBounds(x, 110+y, 85, 25);
                notaBimestre[cont].setFont(fonte);
                add(notaBimestre[cont]);
                cont++;
                y+=30;
            }
            x+=120;
        }
        
        //ADICIONAR COMPONENTES
        add(salvar);
        add(limpar);
        add(filtrar);
        add(redefinir);
        add(limpaNome);
        add(check);
        add(tnome);
        add(tmateria);
        add(campoMateria);
        add(caixaNomes);
        add(fundo);
    }
    
    private void eventos(){
        evtLimpar = (e) -> {
            limparTela();
        };
        
        evtSalvar = (e) ->{
            eventoSalvar();
        };
        
        evtFiltrar = (e) ->{
            String procurar = String.valueOf(caixaNomes.getSelectedItem()).toUpperCase().trim();
            
            if (!procurar.equals("NULL") && !procurar.equals("")){
                String teste;
                String[] listaNomes = Main.arquivo.getNomesArquivo();
                
                caixaNomes.removeAllItems();

                if (check.isSelected()){
                    for (String nome : listaNomes){
                        if (nome.contains(procurar)){
                            caixaNomes.addItem(nome);
                        }
                    }
                }else{
                    for (String nome : listaNomes){
                        teste = "";
                        for (int i=0; i<procurar.length() && i<nome.length(); i++){
                            teste += nome.charAt(i);
                        }

                        if (teste.equals(procurar)){
                            caixaNomes.addItem(nome);
                        }
                    }
                }
            }
        };
        
        evtRedefinir = (e) ->{
            caixaNomes.removeAllItems();
            String[] listaNomes = Main.arquivo.getNomesArquivo();
            for (String nome : listaNomes){
                caixaNomes.addItem(nome);
            }
            caixaNomes.setSelectedIndex(-1);
        };
        
        evtLimpaNome = (e) ->{
            caixaNomes.setSelectedIndex(-1);
        };
    }
    
    public void limparTela(){
        campoMateria.setText("");
        for (int i=0; i<12; i++){
            notaBimestre[i].setText("");
        }
    }
    
    public void eventoSalvar(){
        String nome = String.valueOf(caixaNomes.getSelectedItem()).toUpperCase().trim();
        boolean nomeExiste = false;
        
        if (nome.equals("NULL") || nome.equals("") || campoMateria.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Nome/Matéria Não Fornecidos", "Atenção", 2);
        }else{
            String[] listaNomes = Main.arquivo.getNomesArquivo();
            for (String item : listaNomes){
                if (nome.equals(item)){
                    nomeExiste = true;
                    break;
                }
            }
            
            if (nomeExiste){
                inserirNotasBimestre(nome, campoMateria.getText().trim().toUpperCase());  
                JOptionPane.showMessageDialog(rootPane, "Nova Matéria Adicionada", "Salva", 1);
                limparTela();
            }else{
                JOptionPane.showMessageDialog(rootPane, "Nome não encontrado", "Atenção", 2);
            }
        }
        
    }
    
    public void inserirNotasBimestre(String nome, String materia){
        double valor;
        String valorFormatado;
        ArrayList notasMedia = new ArrayList();
        
        Main.arquivo.escreverEspecifico(nome, materia, true);
        
        //Tente converter para double, se não de certo adiciona 0.00
        int cont=0;
        for (int i=0; i<12; i++){
            try{
                valor = Double.valueOf(notaBimestre[i].getText().replace(",", ".").trim());
                valorFormatado = f.format(valor).replace(",", ".");
                if ("10.00".equals(valorFormatado)){
                    valorFormatado = "10.0";
                }
                Main.arquivo.escreverEspecifico(nome, valorFormatado, true);
            }catch(NumberFormatException e){
                Main.arquivo.escreverEspecifico(nome, "0.00", true);
                valorFormatado = "0.00";
            }
            
            notasMedia.add(valorFormatado);
            
            //Adicionar as médias
            cont++;
            if (cont == 3){
                double media = (Double.valueOf((String)notasMedia.get(0))+
                               Double.valueOf((String)notasMedia.get(1))+
                               Double.valueOf((String)notasMedia.get(2)))/3;
                String mediaFormatada = f.format(media).replace(",", ".");
                if ("10.00".equals(mediaFormatada)){
                    mediaFormatada = "10.0";
                }
                Main.arquivo.escreverEspecifico(nome, mediaFormatada, true);
                cont=0;
                notasMedia.clear();
            }
        }
    }
}
