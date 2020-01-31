package com.oscarhconstantino.computoevolutivo;

/**
 *
 * @author oscahern
 */
public abstract class Individuo {
    
  private double eval;
    
  public double obtenerEval() {
    return eval;
  }
  
  public void setEval(double eval) {
    this.eval = eval;
  }
  
  //public abstract void getCromosoma();
  
  //public abstract 
 
  public abstract String toString();
}
