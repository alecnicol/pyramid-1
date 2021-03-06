package edu.neu.ccs.pyramid.dataset;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.SequentialAccessSparseVector;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Rainicy on 1/3/16.
 */
public class SequentialSparseMLClfDataSet extends SequentialSparseDataSet implements MultiLabelClfDataSet {
    private int numClasses;
    private MultiLabel[] multiLabels;
    private LabelTranslator labelTranslator;

    public SequentialSparseMLClfDataSet(int numDataPoints, int numFeatures,
                              boolean missingValue, int numClasses){
        super(numDataPoints, numFeatures, missingValue);
        this.numClasses=numClasses;
        this.multiLabels=new MultiLabel[numDataPoints];
        for (int i=0;i<numDataPoints;i++){
            this.multiLabels[i]= new MultiLabel();
        }
        this.labelTranslator = LabelTranslator.newDefaultLabelTranslator(numClasses);
    }

    @Override
    public int getNumClasses() {
        return this.numClasses;
    }

    @Override
    public MultiLabel[] getMultiLabels() {
        return this.multiLabels;
    }

    @Override
    public void addLabel(int dataPointIndex, int classIndex) {
        this.multiLabels[dataPointIndex].addLabel(classIndex);
    }

    @Override
    public void setLabels(int dataPointIndex, MultiLabel multiLabel) {
        multiLabels[dataPointIndex] = multiLabel;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("numClasses=").append(numClasses).append("\n");
        sb.append(super.toString());
        sb.append("labels").append("\n");
        for (int i=0;i<numDataPoints;i++){
            sb.append(i).append(":").append(multiLabels[i]).append(",");
        }
        return sb.toString();
    }

    @Override
    public String getMetaInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getMetaInfo());
        sb.append("type = ").append("sparse sequential multi-label classification").append("\n");
        sb.append("number of classes = ").append(this.numClasses);
        return sb.toString();
    }

    @Override
    public LabelTranslator getLabelTranslator() {
        return labelTranslator;
    }

    @Override
    public void setLabelTranslator(LabelTranslator labelTranslator) {
        this.labelTranslator = labelTranslator;
    }


    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.defaultWriteObject();
        SerializableVector[] serFeatureRows = new SerializableVector[featureRows.length];
        for (int i=0;i<featureRows.length;i++){
            serFeatureRows[i] = new SerializableVector(featureRows[i]);
        }
        SerializableVector[] serFeatureColumns = new SerializableVector[featureColumns.length];
        for (int i=0;i<featureColumns.length;i++){
            serFeatureColumns[i] = new SerializableVector(featureColumns[i]);
        }
        out.writeObject(serFeatureRows);
        out.writeObject(serFeatureColumns);
    }


    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        SerializableVector[] serFeatureRows = (SerializableVector[])in.readObject();
        featureRows = new SequentialAccessSparseVector[serFeatureRows.length];
        for (int i=0;i<featureRows.length;i++){
            featureRows[i] = (SequentialAccessSparseVector) serFeatureRows[i].getVector();
        }

        SerializableVector[] serFeatureColumns = (SerializableVector[])in.readObject();
        featureColumns = new SequentialAccessSparseVector[serFeatureColumns.length];
        for (int i=0;i<featureColumns.length;i++){
            featureColumns[i] = (SequentialAccessSparseVector) serFeatureColumns[i].getVector();
        }
    }
}
