package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.helpers;

import ch.uzh.ifi.seal.monolith2microservices.models.SimilarityAlgorithms;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.*;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

import java.util.HashMap;
import java.util.Map;

public class NLPOperations {

    private static final double SIMILARITY_COEFFICIENT_THRESHOLD = 0.2;

    private static Map<String, Double> Cache = new HashMap<String, Double>();
    private static ILexicalDatabase db = new NictWordNet();
    private static RelatednessCalculator calculator;
    private static HashMap<SimilarityAlgorithms,RelatednessCalculator> rcs;

    static {
        //fallback
        calculator = new WuPalmer(db);

        rcs = new HashMap<>();
        rcs.put(SimilarityAlgorithms.HIRST_ST_ONGE, new HirstStOnge(db));
        rcs.put(SimilarityAlgorithms.LEACOCK_CHODOROW, new LeacockChodorow(db));
        rcs.put(SimilarityAlgorithms.RESNIK, new Resnik(db));
        rcs.put(SimilarityAlgorithms.JIANG_CONRATH, new JiangConrath(db));
        rcs.put(SimilarityAlgorithms.LIN, new Lin(db));
        rcs.put(SimilarityAlgorithms.PATH, new Path(db));
        rcs.put(SimilarityAlgorithms.LESK, new Lesk(db));
        rcs.put(SimilarityAlgorithms.WU_PALMER, new WuPalmer(db));
    }

    public static double calculateSemanticSimilarity(String word1, String word2, SimilarityAlgorithms algorithm) {

        if(Cache.containsKey(word1+word2))
        {
            return Cache.get(word1+word2);
        }
        WS4JConfiguration.getInstance().setMFS(true);
        calculator = rcs.get(algorithm);

        double score = calculator.calcRelatednessOfWords(word1, word2);

        Cache.put(word1+word2, score);
        return score;
    }
}
