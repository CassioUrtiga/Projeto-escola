package cadastroalunos;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Alterar extends AdicionarMateria{
    
    private final JComboBox caixaVisualizarMateria;
    private final JButton botRestaurarNotas, botRemoverMateria, botRemoverE;
    
    private ActionListener evtRestaurarNotas, evtCaixaNomes, evtInserirDados, evtRemoverM, evtRemoverE;

    public Alterar(String titulo, Dimension d) {
        super(titulo, d);
        
        caixaVisualizarMateria = new JComboBox();
        botRestaurarNotas = new JButton("Restaurar notas");
        botRemoverMateria = new JButton("Remover matéria", new ImageIcon(Main.localIcons+"\\text_decrease.png"));
        botRemoverE = new JButton("Remover estudante", new ImageIcon(Main.localIcons+"\\person_remove.png"));
        
        eventos();
        carregarComponentes();
    }

    private void carregarComponentes() {
        remove(campoMateria);
        
        botRestaurarNotas.setBounds(580, 400, 200, 33);
        botRestaurarNotas.setFont(fonte);
        botRestaurarNotas.addActionListener(evtRestaurarNotas);
        
        tmateria.setBounds(15, 65, 80, 20);
        
        caixaVisualizarMateria.setBounds(95, 60, 500, 33);
        caixaVisualizarMateria.setMaximumRowCount(2);
        caixaVisualizarMateria.setFont(fonte);
        caixaVisualizarMateria.addActionListener(evtInserirDados);
        
        check.setBounds(650, 14, 200, 25);
        filtrar.setBounds(650, 50, 200, 25);
        redefinir.setBounds(650, 100, 200, 25);
        
        caixaNomes.addActionListener(evtCaixaNomes);
        
        salvar.setText("Salvar alterações");
        
        botRemoverMateria.setBounds(620, 260, 240, 40);
        botRemoverMateria.setFont(fonte);
        botRemoverMateria.setIconTextGap(13);
        botRemoverMateria.addActionListener(evtRemoverM);
        
        botRemoverE.setBounds(620, 200, 240, 40);
        botRemoverE.setFont(fonte);
        botRemoverE.setIconTextGap(5);
        botRemoverE.addActionListener(evtRemoverE);
        
        add(caixaVisualizarMateria);
        add(botRemoverMateria);
        add(botRestaurarNotas);
        add(botRemoverE);
        add(fundo);
    }

    private void eventos() {
        evtRestaurarNotas = (e) ->{
            inserirDados();
        };
        
        
        evtCaixaNomes = (e) ->{
            boolean nomeExiste = false;
            String nome = String.valueOf(caixaNomes.getSelectedItem()).toUpperCase().trim();
            
            if (caixaNomes.getSelectedIndex() == -1 || Main.arquivo.getMateriaArquivo(nome).isEmpty()){
                caixaVisualizarMateria.setEnabled(false);
            }else{
                caixaVisualizarMateria.setEnabled(true);
            }
            
            if (caixaNomes.getSelectedIndex() == -1){
                caixaVisualizarMateria.removeAllItems();
                limparTela();
            }else{
                String[] listaNomes = Main.arquivo.getNomesArquivo();
                for (String item : listaNomes){
                    if (nome.equals(item)){
                        nomeExiste = true;
                    }
                }
                
                if (nomeExiste){
                    caixaVisualizarMateria.removeAllItems();
                    ArrayList listaMaterias = Main.arquivo.getMateriaArquivo(nome);
                    listaMaterias.forEach((materia) -> {
                        caixaVisualizarMateria.addItem(materia);
                    });
                }
            }
        };
        
        
        evtInserirDados = (e) ->{
            if (caixaVisualizarMateria.getSelectedIndex() == -1){
                limparTela();
            }else{
                inserirDados();
            }
        };
        
        evtRemoverM = (e) ->{
            if (caixaVisualizarMateria.isEnabled()){
                String nome = String.valueOf(caixaNomes.getSelectedItem()).toUpperCase().trim();
                if (JOptionPane.showConfirmDialog(rootPane, "Tem certeza?", "Remover matéria", 0, 2) == 0){
                    limparTela();
                    String materia = String.valueOf(caixaVisualizarMateria.getSelectedItem()).toUpperCase().trim();
                    Main.arquivo.excluirMateria(nome, materia);
                    caixaVisualizarMateria.removeItem(materia);
                    JOptionPane.showMessageDialog(rootPane, "Matéria excluída com sucesso", "Removido", 1);
                }
                
                if (Main.arquivo.getMateriaArquivo(nome).isEmpty()){
                    caixaVisualizarMateria.setEnabled(false);
                }
            }
        };
        
        evtRemoverE = (e) ->{
            String campoNome = String.valueOf(caixaNomes.getSelectedItem()).toUpperCase().trim();
            if (caixaNomes.getSelectedIndex() != -1 && !campoNome.equals("")){
                boolean existe = false;
                String[] listaNomes = Main.arquivo.getNomesArquivo();
                for (String nome : listaNomes){
                    if (nome.equals(campoNome)){
                        existe = true;
                        break;
                    }
                }
                
                if (existe){
                    if (JOptionPane.showConfirmDialog(rootPane, "Tem certeza?", "Remover estudante", 0, 2) == 0){
                        Main.arquivo.excluirArquivo(campoNome);
                        caixaVisualizarMateria.removeAllItems();
                        caixaNomes.removeItem(campoNome);
                        caixaNomes.setSelectedIndex(-1);
                        limparTela();
                        JOptionPane.showMessageDialog(rootPane, "Estudante ( "+campoNome+" ) foi excluído(a) do sistema", "Removido", 1);
                        Tela.total.setText(String.valueOf(Integer.valueOf(Tela.total.getText())-1));
                    }
                }
            }
        };
    }
    
    private void inserirDados(){
        if (caixaNomes.getSelectedIndex() != -1 && caixaVisualizarMateria.isEnabled()){
            ArrayList lista = new ArrayList();
            ArrayList novaLista = new ArrayList();

            Main.arquivo.getConteudoArquivo((String)caixaNomes.getSelectedItem(), lista);

            int index = lista.indexOf(caixaVisualizarMateria.getSelectedItem());
            for (int i=index; i<index+17; i++){
                novaLista.add(lista.get(i));
            }

            for (int i=1; i<=3; i++){
                notaBimestre[i-1].setText((String)novaLista.get(i));
            }

            for (int i=5; i<=7; i++){
                notaBimestre[i-2].setText((String)novaLista.get(i));
            }

            for (int i=9; i<=11; i++){
                notaBimestre[i-3].setText((String)novaLista.get(i));
            }

            for (int i=13; i<=15; i++){
                notaBimestre[i-4].setText((String)novaLista.get(i));
            }
        }
    }

    @Override
    public void limparTela() {
        for (int i=0; i<12; i++){
            notaBimestre[i].setText("");
        }
    }

    @Override
    public void eventoSalvar() {
        String nome = String.valueOf(caixaNomes.getSelectedItem()).toUpperCase().trim();
        boolean nomeExiste = false;
        
        if (nome.equals("NULL") || nome.equals("") || caixaVisualizarMateria.getSelectedItem() == null){
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
                Main.arquivo.excluirMateria(nome, (String)caixaVisualizarMateria.getSelectedItem());
                inserirNotasBimestre(nome, (String)caixaVisualizarMateria.getSelectedItem());
                JOptionPane.showMessageDialog(rootPane, "Matéria alterda com sucesso", "Alterado", 1);
            }else{
                JOptionPane.showMessageDialog(rootPane, "Nome não encontrado", "Atenção", 2);
            }
        }
    }
}
