package brain.problem;

import java.util.List;

import brain.agent.Action;

/**
 * Intelig�ncia Artificial: Uma Abordagem Moderna (3a Edi��o), p�gina 174. Para
 * fornecer uma formula��o precisa desse problema [n�o determin�stico], �
 * preciso generalizar a no��o do modelo de transi��o do Cap�tulo 3. Em vez de
 * definir o modelo de transi��o por uma fun��o RESULTADO que devolve um �nico
 * estado, usaremos uma fun��o RESULTADO que devolve um conjunto de estados
 * resultantes poss�veis.
 * 
 * @author Rafael D.
 *
 * @param <S>
 */
public interface ResultsFunction<S> {

	List<S> getResults(S state, Action action);

}
