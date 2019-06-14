package Mapa;

public class Vector2i {
    
    /*
    Algoritmo da AEstrela usado para a IA (Extraido da video-aula da DankiCode do professor Guilherme Grillo)
    Classes extraídas que compõem o algoritmo: AEstrela, No, Vector2i.
    https://cursos.dankicode.com/campus/curso-dev-games/a*-algoritmo
    https://cursos.dankicode.com/campus/curso-dev-games/aplicando-a*
    */

    public int x,y;

    public Vector2i(int x,int y) {
        this.x = x;
        this.y = y;
    }

    //Metodo usado para comparar duas posições (o x e y das duas)
    //(Provavelmente a posição de cada inimigo com a do player, ou seja, se chegou ao destino)
    //'object' é padrão do java, pode passar o que quiser
    public boolean equals(Object object) {
        Vector2i vec = (Vector2i) object; //cast para dizer que obrigatorio ser do tipo vector2i
        if(vec.x == this.x && vec.y == this.y) {
            return true;
        }
        return false;
    }
}
