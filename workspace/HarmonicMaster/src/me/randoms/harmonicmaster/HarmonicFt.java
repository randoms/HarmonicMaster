package me.randoms.harmonicmaster;

import Jama.Matrix;
import android.util.Log;

//基函数不够，64个基函数只能有64个采样，这样采样太少了
// 关键是64个点的时间太短，对于低频信号连一个周期都采不到
// 现在的问题是怎么合适的采样保证64个点能更好的描述波形

/**
 * 
 * @author randoms
 * a Fourier transition basic on arbitrary bases
 */
public class HarmonicFt {
	private double[][] bases;
	private double[][] coEffs;
	private Matrix baseMatrix;
	private Matrix coMatrix;
	private double[] conArray;
	
	public HarmonicFt(double[][] mbases){
		// uniform the bases
		bases = new double[mbases.length][mbases[0].length];
		for(int i=0;i<mbases.length;i++){
			double[] col = mbases[i];
			double mat = 0;
			for(int j=0;j<col.length;j++){
				mat += col[j]*col[j];
			}
			mat = Math.sqrt(mat);
			for(int j=0;j<col.length;j++){
				bases[i][j] = mbases[i][j]/mat;
			}
		}
		// construct to matrix
		baseMatrix = new Matrix(bases);
		coEffs = new double[bases.length][bases[0].length];
		for(int i=0;i<bases.length;i++){
			for(int j=0;j<bases.length;j++){
				for(int k=0;k<bases[0].length;k++){
					coEffs[i][j] += bases[i][k]*bases[j][k];
				}
			}
		}
		Log.d("coEffs",String.valueOf(bases[0][0]));
		Log.d("coEffs",String.valueOf(coEffs[0][0]));
		coMatrix = new Matrix(coEffs);
		conArray = new double[bases.length];
	}
	
	
	public void beginTrans(double[] input,double[] real, double[] image){
		// step 1 construct linear equations
		// constant array
		if(input.length != bases[0].length){
			throw new RuntimeException("input length should be the same with bases");
		}
		for(int i=0;i<bases.length;i++){
		 	for(int j=0;j<bases[0].length;j++){
				conArray[i] = bases[i][j]*input[j]; 
		 	}
		}
		Matrix conMatrix = new Matrix(conArray,1).transpose();
		Log.d("bases",MatrixToString(baseMatrix));
		Log.d("coMatrix",MatrixToString(coMatrix));
		Log.d("conMatrix",MatrixToString(conMatrix));
		Matrix res = coMatrix.solve(conMatrix);
		Log.d("RandomsMat",MatrixToString(res));
	}
	
	public String MatrixToString(Matrix mat){
		String res = "";
		int row = mat.getRowDimension();
		int col = mat.getColumnDimension();
		res = res +"row:"+String.valueOf(row)+" col:"+String.valueOf(col)+"\n";
		for(int i=0;i<row;i++){
			res = res + ",[";
			for(int j=0;j<col;j++){
				res = res + ","+String.valueOf(mat.get(i, j));
			}
			res = res + "],\n";
		}
		return res;
	}


	public Matrix getBaseMatrix() {
		return baseMatrix;
	}


	public void setBaseMatrix(Matrix baseMatrix) {
		this.baseMatrix = baseMatrix;
	}


	public Matrix getCoMatrix() {
		return coMatrix;
	}


	public void setCoMatrix(Matrix coMatrix) {
		this.coMatrix = coMatrix;
	}


	public double[] getConArray() {
		return conArray;
	}


	public void setConArray(double[] conArray) {
		this.conArray = conArray;
	}
	
}
