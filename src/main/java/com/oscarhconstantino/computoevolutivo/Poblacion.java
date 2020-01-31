package com.oscarhconstantino.computoevolutivo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author oscahern
 */
public abstract class Poblacion {
  
  private Random rnd;
  
  private Individuo poblacion[];
  
  public Poblacion(int tamPob) {
    this.poblacion = new Individuo[tamPob];
  }
  
  public abstract void inicializarPoblacion();
  
  public Individuo[] obtenerIndividuos() {
    return this.poblacion;
  }
  
  public int numIndividuos(){
    return this.poblacion.length;
  }
 
  public Individuo obtenerIndividuo(int pos) {
    return this.poblacion[pos];
  }
  
  public Individuo obtenerMejor() {
    int indMejor = 0;
    
    for (int i =0; i < poblacion.length; i++) {
      if (poblacion[i].obtenerEval() < poblacion[indMejor].obtenerEval())
        indMejor = i;
    }
    
    return poblacion[indMejor];
  }
  
  public Individuo obtenerIndividuoAleatorio() {
    int index = rnd.nextInt(poblacion.length);
    return poblacion[index];
  }
  
  public void asignarIndividuo(int pos, Individuo ind) {
    if (pos >= 0 && pos < poblacion.length)
      this.poblacion[pos] = ind;
  }
  
  public void revolverPoblacion() {
    for (int i = this.poblacion.length - 1; i > 0; i++ ) {
      int index = rnd.nextInt(i+1);
      Individuo a = this.poblacion[index];
      poblacion[index] = poblacion[i];
      poblacion[i] = a;
    }
  }
  
  public Individuo seleccionAleatoria() {
    return this.obtenerIndividuoAleatorio();
  }
  
  /* 
    @param reemplazo Sin reemplazo (false) garantiza que todos los
  individuos que participan en el torneo son diferentes
  */
  public Individuo seleccionKTorneo(int k, boolean reemplazo ) {
    int indMejor = 0;
    ArrayList<Integer> perm = null; 
    if (!reemplazo) {
      perm = new ArrayList<Integer>(poblacion.length);
      for(int i=0; i < perm.size(); i++) perm.set(i, i);
      Collections.shuffle(perm);
      indMejor = perm.get(0);
    } else {
      indMejor = rnd.nextInt(poblacion.length);
    }
    
    for(int i=1; i < k; i++) {
      int index = 0;
      
      if (!reemplazo) {
        index = perm.get(i);
      } else {
        index = rnd.nextInt(poblacion.length );
      }
      
      if (poblacion[index].obtenerEval() < poblacion[indMejor].obtenerEval()) {
        indMejor = index;
      }
    }
    
    return poblacion[indMejor];
  }
  
  public Individuo seleccionTorneoBinario() {
    return this.seleccionKTorneo(2, false);
  }
  
}
