package ch.uzh.ifi.seal.monolith2microservices.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by gmazlami on 12/15/16.
 */

@Entity
public class DecompositionParameters {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean logicalCoupling;

    private boolean semanticCoupling;

    private SimilarityAlgorithms similarityAlgorithms = SimilarityAlgorithms.LEACOCK_CHODOROW;

    private boolean contributorCoupling;

    private int numServices;

    private int intervalSeconds;

    private int sizeThreshold;

    public long getId(){
        return id;
    }

    public boolean isLogicalCoupling() {
        return logicalCoupling;
    }

    public void setLogicalCoupling(boolean logicalCoupling) {
        this.logicalCoupling = logicalCoupling;
    }

    public boolean isSemanticCoupling() {
        return semanticCoupling;
    }

    public void setSemanticCoupling(boolean semanticCoupling) {
        this.semanticCoupling = semanticCoupling;
    }

    public boolean isContributorCoupling() {
        return contributorCoupling;
    }

    public void setContributorCoupling(boolean contributorCoupling) {
        this.contributorCoupling = contributorCoupling;
    }

    public int getNumServices() {
        return numServices;
    }

    public void setNumServices(int numServices) {
        this.numServices = numServices;
    }

    public int getIntervalSeconds() {
        return intervalSeconds;
    }

    public void setIntervalSeconds(int intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public int getSizeThreshold() {
        return sizeThreshold;
    }

    public void setSizeThreshold(int sizeThreshold) {
        this.sizeThreshold = sizeThreshold;
    }

    public SimilarityAlgorithms getSimilarityAlgorithms() {
        return similarityAlgorithms;
    }

    public void setSimilarityAlgorithms(SimilarityAlgorithms similarityAlgorithms) {
        this.similarityAlgorithms = similarityAlgorithms;
    }

    @Override
    public String toString() {
        return "DecompositionParameters{" +
                "id=" + id +
                ", logicalCoupling=" + logicalCoupling +
                ", semanticCoupling=" + semanticCoupling +
                ", similarityAlgorithms=" + similarityAlgorithms +
                ", contributorCoupling=" + contributorCoupling +
                ", numServices=" + numServices +
                ", intervalSeconds=" + intervalSeconds +
                ", sizeThreshold=" + sizeThreshold +
                '}';
    }
}
