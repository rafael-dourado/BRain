package brain.search.local;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;

import brain.agent.Action;
import brain.problem.Problem;
import brain.search.SearchForActions;
import brain.search.SearchForStates;
import brain.search.SearchUtils;
import brain.search.informed.Informed;
import brain.util.Util;
import brain.util.metrics.Metrics;
import brain.util.node.Node;
import brain.util.node.NodeExpander;

/**
 * 
 * Figura 4.5 O algoritmo de têmpera simulada, uma versão de subida de encosta estocástica, onde
alguns movimentos encosta abaixo são permitidos. Movimentos encosta abaixo são prontamente
aceitos no início do escalonamento da têmpera, e depois com menor frequência no decorrer do
tempo. A entrada escalonamento define o valor da temperatura T como uma função do tempo.
 * 
 * <pre>
 * função TÊMPERA-SIMULADA(problema, escalonamento) retorna um estado solução
 * 	entradas: problema, um problema
 * 	escalonamento, um mapeamento de tempo para “temperatura”
 * 	atual ← CRIAR-NÓ(problema.ESTADO-INICIAL)
 * 	para t = 1 até ∞ faça
 * 		T ← escalonamento[t]
 * 		se T = 0 então retornar corrente
 * 		próximo ← um sucessor de atual selecionado aleatoriamente
 * 		ΔE ← próximo.VALOR – atual.VALOR
 * 		se ΔE > 0 então atual ← próximo
 * 		senão atual ← próximo somente com probabilidade eΔE/T
 * 
 * </pre>

 * @author cce user
 *
 * @param <S>
 */
public class SimulatedAnnealingSearch<S> implements SearchForActions<S>, SearchForStates<S>, Informed<S> {

	private NodeExpander<S> nExpander;
	private final String NODES_EXPANDED = "nodesExpanded";
	private final String NODE_VALUE = "NodeValue";
	private ToDoubleFunction<Node<S>> h;
	private boolean globalMaxFound = false;
	Metrics metrics = new Metrics();
	private final int LIMIT = 100;
	public SimulatedAnnealingSearch(ToDoubleFunction<Node<S>> h, NodeExpander<S> nExpander) {
		this.h = h;
		this.nExpander = nExpander;
		this.nExpander.addNodeListener((node) -> metrics.incrementInt(NODES_EXPANDED));
	}
	
	
	public Node<S> findNode(Problem<S> p){
		//escalonamento, um mapeamento de tempo para “temperatura”
		//atual ← CRIAR-NÓ(problema.ESTADO-INICIAL)
		Node<S> current = nExpander.createRootNode(p.getInicialState());
		
		// para t = 1 até ∞ faça
		int t = 1;
		while (true){
			// T ← escalonamento[t]
			metrics.set(NODE_VALUE, valueOf(current));
			double temperature = scheduler(t);
			
			//se T = 0 então retornar corrente
			if(Util.compareDouble(temperature, 0.0)){
				globalMaxFound = true;
				return current;
			}
			
			t++;
			
			//próximo ← um sucessor de atual selecionado aleatoriamente
			Node<S> next = SearchUtils.selectRandom(nExpander.expand(current, p));
			
			//ΔE ← próximo.VALOR – atual.VALOR
			double deltaE = valueOf(next) - valueOf(current);
			
			// se ΔE > 0 então atual ← próximo
			if( deltaE > 0){
				current = next;
				
			}

			// senão atual ← próximo somente com probabilidade eΔE/T
			else if(acceptNode(next,temperature,deltaE)){
				current = next;
			}
			
		}
		
	}
	
	private boolean acceptNode(Node<S> next, double t, double deltaE) {
		Random r = new Random();
		double prob = Math.exp(deltaE / t);
		return r.nextDouble() <= prob;
	}

	// ta uma bosta
	private double scheduler(int t){
		if( t < LIMIT)
			return 20 * Math.exp(-0.045*t);
		else 
			return 0;
	}
	
	private double valueOf(Node<S> n){
		return -h.applyAsDouble(n);
		
		
	}
	@Override
	public S findState( Problem<S> p ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Action> findActions( Problem<S> p ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Metrics getMetrics() {
		// TODO Auto-generated method stub
		return metrics;
	}

	@Override
	public void addNodeListener( Consumer<Node<S>> listener ) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean removeNodeListener( Consumer<Node<S>> listener ) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setHeuristicFunction( ToDoubleFunction<Node<S>> h ) {
		// TODO Auto-generated method stub
		this.h = h;
	}

}
