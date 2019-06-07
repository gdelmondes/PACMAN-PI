package Mapa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AEstrela {

    public static double lastTime = System.currentTimeMillis();
    /*
    Criando um metodo comparator que permite que compare dois objetos 
    e organize em que posição vc quer que o objeto fique na lista;
    
    A classe No é a classe que iremos usar para fazer o comparator,
    ela vai servir para guardar as posições do mapa e outras informações 
    que AEstrela vai precisar;
    */
    private static Comparator<No> nodeSorter = new Comparator<No>() {
        //colocando a propria classe dentro da instancia e reescrevendo para
        //definir como quer que seja o comparador
        //metodo personalizado de comparação
        
        @Override
        public int compare(No n0,No n1) {
            //n0 e n1 sao os dois objetos que irao ser comparados para organizar a lista
            if(n1.fCost < n0.fCost)
                return +1; //sobe uma posição na lista (vai pro começo)
            if(n1.fCost > n0.fCost)
                return -1; //desce uma posição na lista (vai pro final)
            return 0;
        }
    };

    //Metodo de precaução caso o alvo esteja em uma posição muito distante (mapas grandes), 
    //fazendo com que o algoritmo cesse para evitar travamento
    public static boolean clear() {
        //Caso passe uma certa quantidade de segundos e ainda não achou o alvo, ocodigo para!
        if(System.currentTimeMillis() - lastTime >= 1000) {
            return true;
        }
        return false;
    }

    //acha o melhor caminho até o alvo, recebe por parametro o mapa que tera os tiles,
    //o start que é o ponto inicial, e end que é o destino
    public static List<No> findPath(Mapa mapa, Vector2i start, Vector2i end){
        lastTime = System.currentTimeMillis();
        List<No> openList = new ArrayList<No>();//tera posições possiveis que pode percorrer para chegar ao destino
        List<No> closedList = new ArrayList<No>();//possisoes que ja verificou e sabe que nao sao validas

        No current = new No(start,null,0,getDistance(start,end)); //current = posicao atual que ta verificando
        openList.add(current);
        
        while(openList.size() > 0) { //enquanto > 0 quer dizer que ainda pode encontrar o caminho
            Collections.sort(openList,nodeSorter); //organiza a lista com base no comparator
            current = openList.get(0); //O current(tile atual) recebe primeiro item da lista que foi Ordenada
            
            if(current.tile.equals(end)){//se atual é igual ao destino, ele achou o destino e ja pode retornar o caminho
                //Chegamos no ponto final!
                //Basta retornar o valor!
                List<No> path = new ArrayList<No>(); //lista com o caminho
                while(current.parent != null) {
                    path.add(current); //adiciona cada No para a lista do caminho ao destino
                    current = current.parent; //vai para o pai (referencia do proximo No)
                }
                openList.clear();
                closedList.clear();
                return path; // retorna o caminho
            }

            //se ainda nao achou a posição que eu quero:
            openList.remove(current); //ja pode remover que sei que nao é a posição que eu quero
            closedList.add(current); //e adiciona pra lista de verificados

            //pega todas as posições (tiles) vizinhas e verifica se pode ir ou nao pra aquela posição
            //no total serão 9 posições que ira verificar, incluindo a posição que o agente se encontra
            for(int i = 0; i < 9; i++) {
                if(i == 4) continue; //posição que o agente ja esta por isso pula a verificação
                
                //posicao x que o agente esta
                int x = current.tile.x; 
                int y = current.tile.y; 
                
                //posicao que ira verificar
                int xi = (i%3) - 1;
                int yi = (i/3) - 1; 
                Bloco tile = Mapa.getTiles()[x+xi+((y+yi)*Mapa.getWIDTH())];
                if(tile == null) continue;
                if(tile instanceof Parede) continue;
                if(i == 0) {
                    Bloco test = Mapa.getTiles()[x+xi+1+((y+yi) * Mapa.getWIDTH())];
                    Bloco test2 = Mapa.getTiles()[x+xi+((y+yi+1) * Mapa.getWIDTH())];
                    if(test instanceof Parede || test2 instanceof Parede) {
                        continue;
                    }
                }
                else if(i == 2) {
                    Bloco test = Mapa.getTiles()[x+xi-1+((y+yi) * Mapa.getWIDTH())];
                    Bloco test2 = Mapa.getTiles()[x+xi+((y+yi+1) * Mapa.getWIDTH())];
                    if(test instanceof Parede || test2 instanceof Parede) {
                        continue;
                    }
                }
                else if(i == 6) {
                    Bloco test = Mapa.getTiles()[x+xi+((y+yi-1) * Mapa.getWIDTH())];
                    Bloco test2 = Mapa.getTiles()[x+xi+1+((y+yi) * Mapa.getWIDTH())];
                    if(test instanceof Parede || test2 instanceof Parede) {
                        continue;
                    }
                }
                else if(i == 8) {
                    Bloco test = Mapa.getTiles()[x+xi+((y+yi-1) * Mapa.getWIDTH())];
                    Bloco test2 = Mapa.getTiles()[x+xi-1+((y+yi) * Mapa.getWIDTH())];
                    if(test instanceof Parede || test2 instanceof Parede) {
                        continue;
                    }
                }

                Vector2i a = new Vector2i(x+xi,y+yi);
                double gCost = current.gCost + getDistance(current.tile,a);
                double hCost = getDistance(a,end);

                No node = new No(a,current,gCost,hCost);

                if(vecInList(closedList,a) && gCost >= current.gCost) continue;

                if(!vecInList(openList,a)) {
                    openList.add(node);
                }else if(gCost < current.gCost) {
                    openList.remove(current);
                    openList.add(node);
                }
            }
        }
        closedList.clear();
        return null;
    }

    //verifica se a posicao(No) que estamos verificando já está na lista
    private static boolean vecInList(List<No> list, Vector2i vector) {
        for(int i = 0; i < list.size(); i++) {
            //usando o metodo equals de comparao do vector2i
            if(list.get(i).tile.equals(vector)) {
                return true;
            }
        }
        return false;
    }

    //metodo para calcular a distancia do ponto inicial e do final
    //e assim obter o valor de h
    private static double getDistance(Vector2i tile, Vector2i goal) {
        double dx = tile.x - goal.x;
        double dy = tile.y - goal.y;

        return Math.sqrt(dx*dx + dy*dy);
    }
	
}
