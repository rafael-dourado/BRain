package mvc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RebaixaDAO;
import dao.ValidadeDAO;
import model.Rebaixa;
import model.Validade;

public class CadastrarRebaixa implements Logica {
	
	private final static List<Rebaixa>  getRebaixas(String path) throws IOException, ParseException{
		Rebaixa rebaixa = new Rebaixa();
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		List<Rebaixa> rList = new ArrayList<Rebaixa>();
		String line;
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		while((line = buffRead.readLine())!=null){
			if(line.contains("^FD") && line.contains("^FS")){
				String s = line.substring(line.indexOf("^FD"), line.indexOf("^FS")).replace("^FD", "");
				// ean
				if(s.matches("[0-9]+")){
						rebaixa.setEan(s.replaceFirst("^0+(?!$)", ""));
				}
				
				// preço
				else if(s.matches("[0-9,]+") && s.contains(",")){
					
					rebaixa.setPreco(Float.parseFloat(s.replace(",", ".")));
				}
				
				// data fim
				else if(s.contains("VALIDA ATE")){
					String sDate = s.substring(s.indexOf("ATE ")).replace("ATE ", "");
					rebaixa.setInicio(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
					Date date = (Date) f.parse(sDate);
					rebaixa.setFim(new java.sql.Date(date.getTime()));
				}
				
					
				
			} 
			else if(line.contains("^XA"))
				rebaixa = new Rebaixa();
			else if(line.contains("^XZ"))
				rList.add(rebaixa);
				
		}
		buffRead.close(); 
		return rList;
	}
	
	private final static List<Validade> getValidadesParaAlterar(Rebaixa r) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, SQLException{
		
		Validade v = new Validade();
		ValidadeDAO vd = new ValidadeDAO();
		List<Validade> vList = vd.listAll(v,
				"mercadoria = '"+ r.getEan()+"'",
				"DATE(validade) >= '" + r.getFim()+"'",
				"status = 'rs'"
			
			);
		vd.closeConnection();
		return vList;
	}
	
	private final static List<Rebaixa> getRebaixasFinalizadas() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, SQLException{
		RebaixaDAO rd = new RebaixaDAO();
		Rebaixa r = new Rebaixa();
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DATE, -1);
		java.sql.Date yesterday = new java.sql.Date(today.getTimeInMillis());
		List<Rebaixa> rebaixas = rd.listAll(r, 
											"DATE(fim) = '" + yesterday+"'");
		rd.closeConnection();
		
		return rebaixas;
	}
	
	private final static List<Validade> getValidadesParaVoltar(Rebaixa r) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, SQLException{
		
		Validade v = new Validade();
		ValidadeDAO vd = new ValidadeDAO();
		List<Validade> vList3 = vd.listAll(v, 
							"mercadoria = " + r.getEan(),
							"status = 'rebaixa'");
		
		vd.closeConnection();
		return vList3;
	
		
	}

	@Override
	public String executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		try {
			List <Rebaixa> rebaixas= getRebaixas("\\\\192.168.17.99\\relatorio\\etiqueta\\30\\promocao.txt");

			for(Rebaixa r : rebaixas){
				Validade vv = new Validade();
				ValidadeDAO vdao = new ValidadeDAO();
				RebaixaDAO rd = new RebaixaDAO();
				if( rd.listAll ( r, 
						"ean = '" + r.getEan() + "'",
						"DATE(inicio) = '"+r.getInicio()+"'"
						 ).isEmpty() &&
						
					!vdao.listAll(vv, 
						"mercadoria = '" + r.getEan()+"'",
						"status = 'rs'"
								).isEmpty()
						){
					
					rd.add( r );
					
				}
				rd.closeConnection();
				vdao.closeConnection();
				
				for(Validade v : getValidadesParaAlterar(r)){
					if(!getValidadesParaAlterar(r).isEmpty()){
						ValidadeDAO vd = new ValidadeDAO();
						v.setStatus("rebaixa");
						//System.out.println(v.getMercadoria() + " " + v.getValidade());
						vd.update(v, "mercadoria = '"+ r.getEan()+"' AND "+
								"DATE(validade) >= '" + r.getFim()+"'", "status = '"+ v.getStatus() + "'");
						vd.closeConnection();
					}
				}
			}
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Rebaixa> rr = getRebaixasFinalizadas();
		for(Rebaixa r : rr){
			List<Validade> vteste = getValidadesParaVoltar(r);
			
			for(Validade v : vteste){
				if(!vteste.isEmpty()){
					ValidadeDAO vd = new ValidadeDAO();
					vd.update(v, "id = " + v.getId(), "status = 'prorrogar'");
					vd.closeConnection();
				}
			}
		}
		System.out.println("Finalizado");
		
		return "mvc?task=ListarRebaixas";
	}
}
