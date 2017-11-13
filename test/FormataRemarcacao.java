package mvc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.leitorarquivo.LeitorArquivo;

public class FormataRemarcacao implements Logica{
	
	private final static String PATH_PROMOCAO = "\\\\192.168.17.99\\relatorio\\etiqueta\\30\\promocao.txt";
	private final static String PATH_REMARCACAO = "\\\\192.168.17.99\\relatorio\\etiqueta\\30\\remarcacao.txt";
	
	
	@Override
	public String executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		
		return null;
	}
	
	private static List<Remarcacao> getRemarcacoes() throws IOException{
		List<Remarcacao> remarcacoes = new ArrayList<Remarcacao>();
		BufferedReader promocoes = new BufferedReader(new FileReader(PATH_PROMOCAO));
		BufferedReader nRemarcacoes = new BufferedReader(new FileReader(PATH_REMARCACAO));
		String line;
		while( (line = promocoes.readLine())!= null){
			if(line.contains("^FD") && line.contains("^FS")){
				String substring = line.substring(line.indexOf("^FD"), line.indexOf("^FS")).replace("^FD", "");
				
				if( notEan(substring) && notPrice(substring));
				System.out.println(substring);
			}
		}
		return null;
	}
	
	private static boolean notEan(String string){
		return !string.matches("[0-9]+");
		
	}
	
	private static boolean notPrice(String string){
		return !(string.matches("[0-9,]+") && string.contains(","));
	}
	
	private class Remarcacao{
		private String ean;
		private String descricao;
		private String preco;
		
		public Remarcacao(String ean, String descricao, String preco){
			this.ean = ean;
			this.descricao = descricao;
			this.preco = preco;
			
		}

		public String getEan() {
			return ean;
		}

		public void setEan(String ean) {
			this.ean = ean;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public String getPreco() {
			return preco;
		}

		public void setPreco(String preco) {
			this.preco = preco;
		}
		@Override
		public String toString(){
			return "EAN: " + ean + " DESCRICAO: "+ descricao +  " PRECO: " + preco;
		}
	}
	
	public static void main(String[]args){
		
		try {
			FormataRemarcacao.getRemarcacoes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
