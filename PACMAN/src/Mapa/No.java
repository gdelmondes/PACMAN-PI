package Mapa;

public class No {
    
    /*
    Algoritmo da AEstrela usado para a IA (Extraido da video-aula da DankiCode do professor Guilherme Grillo)
    Classes extraídas que compõem o algoritmo: AEstrela, No, Vector2i.
    https://cursos.dankicode.com/campus/curso-dev-games/a*-algoritmo
    https://cursos.dankicode.com/campus/curso-dev-games/aplicando-a*
    */

    public Vector2i tile; //objeto que guarda o (x e y) de cada poisção para ter mais controle
    public No parent; //Cada No vai ter um parent(Pai) 
    public double fCost, gCost, hCost; //Custos f,g,h, usados para achar o menor caminho

    public No(Vector2i tile, No parent, double gCost, double hCost) {
        this.tile = tile;
        this.parent = parent;
        this.gCost = gCost; //custo acumulado
        this.hCost = hCost; //distancia do ponto inicial e final(destino)
        this.fCost = gCost + hCost; //f é o custo total
    }
}
