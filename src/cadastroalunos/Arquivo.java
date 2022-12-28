package cadastroalunos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Arquivo {
    
    private File arquivo;
    private Path diretorio;
    private final String localPasta;

    public Arquivo(String local) {
        localPasta = local;
    }
    
    public void escreverEspecifico(String nome, String conteudo, boolean sobrescrever){
        try (OutputStreamWriter bufferOut = new OutputStreamWriter(new FileOutputStream(localArquivo(nome), sobrescrever),"UTF-8")) {
            bufferOut.write(conteudo);
            if (sobrescrever){
                bufferOut.write("\n");
            }
            bufferOut.close();
        } catch (IOException ex) {
            System.out.println("ERRO (escreverEspecifico)");
        }
    }
    
    public String[] getNomesArquivo(){
        File file = new File(localPasta);
        String[] nomes = file.list();
        
        for (int i=0; i<nomes.length; i++){
            nomes[i] = nomes[i].replace(".txt", "");
        }
        
        return nomes;
    }
    
    public ArrayList getMateriaArquivo(String nome){
        ArrayList materias = new ArrayList();
        ArrayList lista = new ArrayList();
        BufferedReader buf;
        
        try {
            buf = new BufferedReader(new InputStreamReader(new FileInputStream(localArquivo(nome)), "UTF-8"));
            String conteudo;
            while(( conteudo = buf.readLine())!=null){
                lista.add(conteudo);
            }
            buf.close();
        } catch (IOException e) {
            System.out.println("ERRO (getMateriaArquivo)");
        }
        
        lista.forEach((item) -> {
            try{
                Double.valueOf((String)item);
            }catch(NumberFormatException e){
                materias.add(item);
            }
        });
        
        return materias;
    }
    
    public void excluirMateria(String nome, String materia){
        ArrayList lista = new ArrayList();
        BufferedReader buf;
        
        try {
            buf = new BufferedReader(new InputStreamReader(new FileInputStream(localArquivo(nome)), "UTF-8"));
            String conteudo;
            while(( conteudo = buf.readLine())!=null){
                lista.add(conteudo);
            }
            buf.close();
        } catch (IOException e) {
            System.out.println("ERRO (excluirMateria)");
        }
        
        int materiaIndex = lista.indexOf((String)materia);
        for (int i=0; i<17; i++){
            lista.remove(materiaIndex);
        }
        
        escreverEspecifico(nome, "", false);
        
        lista.forEach((item) -> {
            escreverEspecifico(nome, (String)item, true);
        });
        
    }
    
    public void excluirArquivo(String nome){
        File file = new File(localArquivo(nome));
        file.delete();
    }
    
    public void excluirTodosArquivos(){
        File file = new File(localPasta);
        File[] arquivos = file.listFiles();
        
        for (File i : arquivos){
            i.delete();
        }
    }
    
    public void getConteudoArquivo(String nome, ArrayList lista){
        BufferedReader buf;
        
        try {
            buf = new BufferedReader(new InputStreamReader(new FileInputStream(localArquivo(nome)), "UTF-8"));
            String conteudo;
            while(( conteudo = buf.readLine())!=null){
                lista.add(conteudo);
            }
            buf.close();
        } catch (IOException e) {
            System.out.println("ERRO (getConteudoArquivo)");
        }
    }
    
    public void criarArquivo(String nome){
        String local = localArquivo(nome);
        
        arquivo = new File(local);
        diretorio = Paths.get(local);
        
        if (!Files.exists(diretorio)){
            try{
               arquivo.createNewFile();
               System.out.println("ARQUIVO CRIADO");
               Tela.total.setText(String.valueOf(Integer.valueOf(Tela.total.getText())+1));
               JOptionPane.showMessageDialog(null, "Estudante cadastrado(a)", "Sucesso", 1);
            }catch(IOException io){
               System.out.println("ERRO CRIAR ARQUIVO");
            }
        }else{
            JOptionPane.showMessageDialog(null, nome+" já está cadastrado(a)", "Atenção", 0);
        }
    }
    
    private String localArquivo(String nome){
        return localPasta+"\\"+nome+".txt";
    }
}
