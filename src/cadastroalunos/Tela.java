package cadastroalunos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Tela extends JFrame{
    
    static JTextField total;
    
    private final JPanel fundo;
    private final Font fonte;
    private final JButton inserirE, adicionarM, alterar, excluir, historico;
    private final Visualizar visualizar;
    private final JLabel txtTotal;
    private final Color botCor;
    
    private ActionListener evtInserirE, evtAdicionarM, evtAlterar, evtVisualizar, evtExcluir;

    public Tela (String titulo, Dimension d){
        super(titulo);
        
        fundo = new JPanel();
        fonte = new Font("Calibri", 1, 23);
        visualizar = new Visualizar("Histórico", new Dimension(400, 400));
        inserirE = new JButton("Cadastrar estudante");
        adicionarM = new JButton("Adicionar matéria");
        alterar = new JButton("Alterar");
        excluir = new JButton(new ImageIcon(Main.localIcons+"\\delete_forever.png"));
        historico = new JButton("Visualizar histórico", new ImageIcon(Main.localIcons+"\\search.png"));
        txtTotal = new JLabel();
        total = new JTextField();
        botCor = new Color(191, 191, 191);
        
        eventos();
        gerarComponentes(d);
    }
    
    private void gerarComponentes(Dimension d){
        //JFrame
        setSize(d);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        fundo.setSize(d);
        fundo.setBackground(new Color(13, 13, 13));
        
        //JButton
        inserirE.setBounds(90, 30, 300, 50);
        inserirE.setFont(fonte);
        inserirE.setBackground(botCor);
        inserirE.addActionListener(evtInserirE);
        
        adicionarM.setBounds(90, 85, 300, 50);
        adicionarM.setFont(fonte);
        adicionarM.setBackground(botCor);
        adicionarM.addActionListener(evtAdicionarM);
        
        alterar.setBounds(90, 140, 300, 50);
        alterar.setFont(fonte);
        alterar.setBackground(botCor);
        alterar.addActionListener(evtAlterar);
        
        historico.setBounds(90, 220, 300, 50);
        historico.setFont(fonte);
        historico.setBackground(botCor);
        historico.setHorizontalTextPosition(SwingConstants.LEFT);
        historico.setIconTextGap(10);
        historico.addActionListener(evtVisualizar);
        
        excluir.setBounds(440, 365, 40, 40);
        excluir.setBackground(botCor);
        excluir.setFont(fonte);
        excluir.addActionListener(evtExcluir);
        
        total.setBounds(170, 370, 100, 30);
        total.setBackground(botCor);
        total.setForeground(new Color(13,13,13));
        total.setText(String.valueOf(Main.arquivo.getNomesArquivo().length));
        total.setFont(fonte);
        total.setHorizontalAlignment(JTextField.CENTER);
        total.setEditable(false);
        
        txtTotal.setText("Total de alunos:");
        txtTotal.setForeground(new Color(140, 140, 140));
        txtTotal.setBounds(10, 370, 200, 30);
        txtTotal.setFont(fonte);
        
        //ADICIONAR COMPONENTES
        add(inserirE);
        add(adicionarM);
        add(alterar);
        add(historico);
        add(excluir);
        add(total);
        add(txtTotal);
        add(fundo);
    }
    
    private void eventos(){
        evtInserirE = (e) ->{
            Cadastrar novo = new Cadastrar("Cadastrar", new Dimension(500, 220));
            novo.setVisible(true);
            Visualizar.atualizarHistorico();
        };
        
        evtAdicionarM = (e) ->{
            AdicionarMateria addMateria = new AdicionarMateria("Inserir Matéria", new Dimension(700, 500));
            addMateria.setVisible(true);
            
            String[] listaNomes = Main.arquivo.getNomesArquivo();
            for (String nome : listaNomes){
                AdicionarMateria.caixaNomes.addItem(nome);
            }
            AdicionarMateria.caixaNomes.setSelectedIndex(-1);
        };
        
        evtAlterar = (e) ->{
            Alterar t = new Alterar("Alterar notas", new Dimension(900, 500));
            
            String[] listaNomes = Main.arquivo.getNomesArquivo();
            for (String nome : listaNomes){
                Alterar.caixaNomes.addItem(nome);
            }
            Alterar.caixaNomes.setSelectedIndex(-1);
            t.setVisible(true);
        };
        
        evtVisualizar = (e) ->{
            Visualizar.atualizarHistorico();
            if (Visualizar.caixaHistorico.getItemCount() == 0){
                JOptionPane.showMessageDialog(rootPane, "Nenhum histórico de aluno foi encontrado", "Histórico vazio", 1);
            }else{
                Visualizar.caixaHistorico.setEnabled(true);
                visualizar.setVisible(true);
            }
        };
        
        evtExcluir = (e) ->{
            if (JOptionPane.showConfirmDialog(rootPane, "DESEJA EXCLUIR TODO O HISTÓRICO DOS ALUNOS PERMANENTEMENTE?", "Atenção", 0, 2) == 0){
                Main.arquivo.excluirTodosArquivos();
                Visualizar.caixaHistorico.removeAllItems();
                Visualizar.caixaHistorico.setEnabled(false);
                Tela.total.setText("0");
            }
        };
    }
}
