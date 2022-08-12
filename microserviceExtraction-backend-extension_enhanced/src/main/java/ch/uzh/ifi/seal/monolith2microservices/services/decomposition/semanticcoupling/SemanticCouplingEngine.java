package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling;

import ch.uzh.ifi.seal.monolith2microservices.main.Configs;
import ch.uzh.ifi.seal.monolith2microservices.models.ClassContent;
import ch.uzh.ifi.seal.monolith2microservices.models.SimilarityAlgorithms;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.SemanticCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.classprocessing.ClassContentVisitor;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.helpers.MathOperations;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.helpers.NLPOperations;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.tfidf.TfIdfWrapper;
import ch.uzh.ifi.seal.monolith2microservices.utils.ClassContentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class SemanticCouplingEngine {

    @Autowired
    private Configs config;

    public List<SemanticCoupling> computeCouplings(GitRepository repo, SimilarityAlgorithms similarityAlgorithms) throws IOException{

        List<SemanticCoupling> couplings = new ArrayList<>();

        //Read class files (content) from repo
        String localRepoPath = config.localRepositoryDirectory + "/" + repo.getName() + "_" + repo.getId();

        Path repoDirectory = Paths.get(localRepoPath);

        ClassContentVisitor visitor = new ClassContentVisitor(repo,config, new ClassContentFilter());

        Files.walkFileTree(repoDirectory, visitor);

        List<ClassContent> classes = visitor.getClasses();

        for(ClassContent current: classes){
            for(ClassContent other: classes){
                if (!current.getFilePath().equals(other.getFilePath())) {
//                    SemanticCoupling coupling = new SemanticCoupling(current.getFilePath(),other.getFilePath(),TfIdfWrapper.computeSimilarity(current.getTokenizedContent(), other.getTokenizedContent()));
                    ArrayList<Double> similarities = new ArrayList<>();
                    for (String firstToken : current.getTokenizedContent())
                    {
                        for (String secondToken : other.getTokenizedContent())
                        {
                            double score =  NLPOperations.calculateSemanticSimilarity(firstToken, secondToken, similarityAlgorithms);
                            similarities.add(score);
                        }
                    }
                    double meanOfSimilarities = calculateMeanOfSimilarities(similarities);
                    SemanticCoupling coupling = new SemanticCoupling(current.getFilePath(), other.getFilePath(), meanOfSimilarities);
                    couplings.add(coupling);
                }
            }
        }

        return couplings;

    }

    private static double calculateMeanOfSimilarities(List<Double> similarities) {
        double median = MathOperations.median(ListToArray(similarities));

        if (median == 0.0) {
            median = MathOperations.average(ListToArray(similarities));
        }

        return median;
    }

    private static double[] ListToArray(List<Double> t){
        double[] result = new double[t.size()];
        for(int i = 0; i < t.size(); i++){
            result[i] = t.get(i);
        }
        return result;
    }

}
