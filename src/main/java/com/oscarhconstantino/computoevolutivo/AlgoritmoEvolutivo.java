package com.oscarhconstantino.computoevolutivo;

/**
 *
 * @author oscahern
 */
public abstract class AlgoritmoEvolutivo {
    
  /* Numero de individuos */
  private int tamPob;
  
  /* Numero de hijos a generar en cada iteracion (generacion) */
  private int numHijos;
  
//  private int numPadres;
  
  /* Numero de Iteraciones para el criterio de paro */
  private int maxNumIters;
  
  private Individuo[] padres;
  
  public AlgoritmoEvolutivo() {
    this.tamPob = 100;
    this.numHijos = 2; //
    this.maxNumIters = 1000;
    
//    this.numPadres = 2;
    
//    this.padres = new Individuo[this.numPadres];
  }
  
//  private Poblacion pob;
    
  public abstract double evaluarIndividuo(Individuo ind);
  
  public void evaluarPoblacion(Poblacion pob) {
    for(int i=0; i < pob.numIndividuos(); i++) {
      Individuo indI = pob.obtenerIndividuo(i);
      double evalI = this.evaluarIndividuo(indI);
      indI.setEval(evalI);
    }
  }
  
  public boolean evalCriterioParo(int k) {
    return k < this.maxNumIters;
  }
  
  public Individuo[] obtenerPadres(Poblacion pob) {
    Individuo[] padres = new Individuo[2];
    
    /* Seleccion de padres por torneo binario con reemplazo */
    padres[0] = pob.seleccionTorneoBinario();
    padres[1] = pob.seleccionTorneoBinario();
    
    return padres;
  }
  
  public abstract Individuo[] cruzarIndividuos(Individuo[] padres);
  
  public abstract Poblacion crearPoblacion(int tamPob, boolean inicializar);
  
  public Poblacion generarHijos(Poblacion pob) {
    Poblacion hijos = this.crearPoblacion(numHijos, false);
    
    int numHijosGenerados= 0;
    while ( numHijosGenerados < numHijos ) {
      Individuo[] padres = obtenerPadres(pob);
      
      Individuo[] cruza = cruzarIndividuos(padres);
      
      for (int h = 0; h < cruza.length ; h++) {
        if (numHijosGenerados < numHijos) 
          hijos.asignarIndividuo(numHijosGenerados++ , cruza[h]);
        else break;
      }      
    }
    
    return hijos;
  }
  
  public abstract void reemplazarIndividuos(Poblacion pob, Poblacion hijos);
  
  public void realizarIteracionAE(Poblacion pob) {
    Poblacion hijos = this.generarHijos(pob);
    
    this.evaluarPoblacion(hijos);
      
    this.reemplazarIndividuos(pob, hijos);
  }
  
  public Individuo resolver(int maxNumIters) {
    Poblacion pob = crearPoblacion(tamPob, true);
    this.evaluarPoblacion(pob);
    Individuo mejor = pob.obtenerMejor();
    this.maxNumIters = maxNumIters;
    
    int k = 0;
    do {
      realizarIteracionAE(pob);
      
      mejor = pob.obtenerMejor();
      
    } while( ! this.evalCriterioParo(++k) );
    
    return mejor;
  }
  
  
}
