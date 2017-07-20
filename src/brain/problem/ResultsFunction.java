package brain.problem;

import java.util.List;

import brain.agent.Action;

/**
 * Inteligência Artificial: Uma Abordagem Moderna (3a Edição), página 174. Para
 * fornecer uma formulação precisa desse problema [não determinístico], é
 * preciso generalizar a noção do modelo de transição do Capítulo 3. Em vez de
 * definir o modelo de transição por uma função RESULTADO que devolve um único
 * estado, usaremos uma função RESULTADO que devolve um conjunto de estados
 * resultantes possíveis.
 * 
 * @author Rafael D.
 *
 * @param <S>
 */
public interface ResultsFunction<S> {

	List<S> getResults(S state, Action action);

}
