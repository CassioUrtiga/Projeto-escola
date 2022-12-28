package cadastroalunos;

import java.awt.Dimension;
import java.io.File;

public class Main {
    
    static Arquivo arquivo;
    static String caminho;
    static String localIcons;
    
    public static void main(String[] args) {
        File localAtual = new File(".");
        
        caminho = localAtual.getAbsolutePath().replace("dist", "alunos");
        //Em tempo de execução, o dist é trocado por alunos
        
        if (!caminho.contains("alunos")){
            caminho += "\\alunos";
        }
        
        //Caso a pasta alunos não exista, é criada uma nova
        File reporPasta = new File(caminho); 

        if (!reporPasta.exists()) {
            reporPasta.mkdirs();
        }
        
        arquivo = new Arquivo(caminho);
        
        localIcons = caminho.replace("alunos", "icons");
        
        Tela tela = new Tela("Cadastro", new Dimension(500, 450));
        
        tela.setVisible(true);
    }
}
