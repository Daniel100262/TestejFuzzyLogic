package main;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class Principal {

	public static void main(String[] args) {
		
        String arqFCL = "arquivo.fcl";
        FIS fis = FIS.load(arqFCL, true);

        if( fis == null ) { 
            System.err.println("Não foi possivel carregar o arquivo: '" + arqFCL + "'");
            return;
        }

        // Mostrar graficos 
        JFuzzyChart.get().chart(fis);
        
        FunctionBlock blocoFuncoes = fis.getFunctionBlock(null);

        // Setar entradas
        fis.setVariable("PAS", 150.0); //PAS fixada
        fis.setVariable("PAD", 100.0); //PAD fixada

        // Fazer Evaluate
        fis.evaluate();

        // Mostrar graficos de saida (do diagnostico)
        Variable diag = blocoFuncoes.getVariable("diagnostico");
        
        JFuzzyChart.get().chart(diag, diag.getDefuzzifier(), true);

        // Dar sysout nas regras
        System.out.println(fis);
        
        // Mostrar resultados
        System.out.println("Diagnostico: " + diag.getValue() + "\nStatus: "+traduzValor(diag.getValue()));
    }
	
	public static String traduzValor(Double valor) {
		String valorTraduzido = "";
		
		if(valor > 4.9 && valor < 10) {
			valorTraduzido = "Pressao baixa!";
		}
		
		if(valor > 5.1 && valor > 9.9 && valor < 15) {
			valorTraduzido = "Pressao media!";
		}
		
		if(valor > 9.9 && valor > 14.9 && valor < 20) {
			valorTraduzido = "Pressao alta!";
		}
		
		if(valor > 20) {
			valorTraduzido = "Pressao indefinida!";
		}
		
		return valorTraduzido;
	}
}
