package cadastroalunos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Cadastrar extends JFrame{
    
    private final JPanel fundo;
    private final JTextField nome;
    private final JButton adicionar, limpar, cancelar;
    
    private ActionListener evtLimpar, evtCancelar, evtAdicionar;

    public Cadastrar(String titulo, Dimension d) {
        super(titulo);
        
        fundo = new JPanel();
        nome = new JTextField();
        adicionar = new JButton("Adicionar",new ImageIcon(Main.localIcons+"\\add_reaction.png"));
        limpar = new JButton(new ImageIcon(Main.localIcons+"\\clean.png"));
        cancelar = new JButton("Cancelar",new ImageIcon(Main.localIcons+"\\close.png"));
        
        eventos();
        carregarComponentes(d);
    }

    private void carregarComponentes(Dimension d) {
        //TELA
        setSize(d);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        fundo.setSize(d);
        fundo.setBackground(Color.GRAY);
        
        //JButton
        adicionar.setBounds(90, 100, 150, 40);
        adicionar.setBackground(new Color(0, 180, 0));
        adicionar.setForeground(Color.BLACK);
        adicionar.setFont(new Font("Calibri",1,18));
        adicionar.addActionListener(evtAdicionar);
        
        limpar.setBounds(435, 15, 40, 40);
        limpar.addActionListener(evtLimpar);
        
        cancelar.setBounds(260, 100, 150, 40);
        cancelar.setBackground(new Color(180, 0, 0));
        cancelar.setForeground(Color.BLACK);
        cancelar.setFont(new Font("Calibri",1,18));
        cancelar.addActionListener(evtCancelar);
        
        //JTextField
        nome.setBounds(10, 15, 420, 40);
        nome.setFont(new Font("Calibri", 1, 25));
        nome.addKeyListener(getKeyListener());
        
        add(adicionar);
        add(limpar);
        add(cancelar);
        add(nome);
        add(fundo);
    }
    
    private void eventos(){
        evtLimpar = (e) ->{
            nome.setText("");
        };
        
        evtCancelar = (e) ->{
            dispose();
        };
        
        evtAdicionar = (e) ->{
            adicionarEstudante();
        };
    }
    
    private KeyListener getKeyListener(){
        return new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    adicionarEstudante();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        };
    }
    
    private void adicionarEstudante(){
        String estudante = nome.getText().trim().toUpperCase();
            
        if (estudante.isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Nome não informado", "Atenção", 2);
        }else{
            Main.arquivo.criarArquivo(estudante);
            nome.setText("");
        }
    }
}
